package fr.inria.diverse.trace.metamodel.generator

import ecorext.ClassExtension
import ecorext.Ecorext
import ecorext.Rule
import fr.inria.diverse.trace.commons.EMFUtil
import fr.inria.diverse.trace.commons.EcoreCraftingUtil
import fr.inria.diverse.trace.commons.ExecutionMetamodelTraceability
import fr.inria.diverse.trace.commons.tracemetamodel.StepStrings
import java.io.IOException
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil.Copier
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.inria.diverse.trace.commons.EcoreCraftingUtil.*

class TraceMMGenerator {

	// Outputs
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) var EPackage tracemmresult
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) val TraceMMGenerationTraceability traceability

	// Other 
	private boolean done = false
	private Copier runtimeClassescopier

	private ResourceSet rs
	private Set<EClass> referencedStaticClasses
	private Set<EClass> allRuntimeClasses
	private Set<EClass> allStaticClasses
	private Set<EClass> allNewEClasses
	private Set<EPackage> allNewEPackages
	private String languageName

	// Inputs
	private Ecorext mmext
	private EPackage mm

	// Base classes
	private TraceMMExplorer traceMMExplorer

	new(Ecorext mmext, EPackage mm) {
		this.mm = mm
		this.mmext = mmext
		this.rs = new ResourceSetImpl()
		this.referencedStaticClasses = new HashSet<EClass>
		this.allRuntimeClasses = new HashSet<EClass>
		this.allStaticClasses = new HashSet<EClass>
		this.allNewEClasses = mmext.eAllContents.toSet.filter(EClass).toSet
		this.allNewEPackages = mmext.eAllContents.toSet.filter(EPackage).toSet
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		this.traceability = new TraceMMGenerationTraceability(traceMMExplorer)
	}

	public def void computeAllMaterial() throws IOException {
		if (!done) {
			runtimeClassescopier = new Copier
			loadBase()
			handleTraceClasses()
			handleEvents()
			runtimeClassescopier.copyReferences
			cleanup()
			done = true
		} else {
			println("ERROR: already computed.")
		}
	}

	private def void cleanup() {
		for (c : this.tracemmresult.eAllContents.filter(EClass).toSet) {
			cleanupAnnotations(c);
			c.EOperations.clear
		}

		for (r : runtimeClassescopier.values.filter(EReference)) {
			r.EOpposite = null
		}
	}

	private def void cleanupAnnotations(EClass eClass) {
		val traceabilityAnnotation = ExecutionMetamodelTraceability.getTraceabilityAnnotation(eClass);
		eClass.EAnnotations.clear
		if (traceabilityAnnotation != null) {
			eClass.EAnnotations.add(traceabilityAnnotation);
		}
	}

	private def void loadBase() throws IOException {

		// Create the root package by loading the base ecore and changing its name and stuff
		val Resource base = EMFUtil.loadModelURI(
			URI.createPlatformPluginURI("fr.inria.diverse.trace.metamodel.generator/model/base.ecore", true), rs)
		languageName = mm.name.replaceAll(" ", "") + "Trace"
		tracemmresult = base.contents.get(0) as EPackage
		base.contents.remove(tracemmresult)
		tracemmresult.name = languageName
		tracemmresult.nsURI = languageName // TODO
		tracemmresult.nsPrefix = languageName

		this.traceMMExplorer = new TraceMMExplorer(tracemmresult)

		// Changing packages names TODO use strings classes to name the languages
		traceMMExplorer.tracedPackage.nsURI = languageName + "_Traced"
		traceMMExplorer.eventsPackage.nsURI = languageName + "_Steps"
		traceMMExplorer.statesPackage.nsURI = languageName + "_Values"

		traceability.traceMMExplorer = this.traceMMExplorer
	}

	private def EPackage obtainTracedPackage(EPackage runtimePackage) {
		var EPackage result = traceMMExplorer.tracedPackage

		if (runtimePackage != null) {
			val tracedSuperPackage = obtainTracedPackage(runtimePackage.ESuperPackage)
			val String tracedPackageName = TraceMMStrings.package_createTracedPackage(runtimePackage)
			result = tracedSuperPackage.ESubpackages.findFirst[p|p.name.equals(tracedPackageName)]
			if (result == null) {
				result = EcoreFactory.eINSTANCE.createEPackage
				result.name = tracedPackageName
				result.nsURI = languageName + "_" + result.name // TODO
				result.nsPrefix = "" // TODO
				tracedSuperPackage.ESubpackages.add(result)
			}
		}
		return result
	}

	private def handleTraceClasses() {

		// First we find ALL classes linked to runtime properties
		val runtimeClass2ClassExtension = new HashMap<EClass, ClassExtension>;
		for (c : mmext.classesExtensions) {
			allRuntimeClasses.add(c.extendedExistingClass)
			runtimeClass2ClassExtension.put(c.extendedExistingClass, c)

			// super-classes of extended class
			allRuntimeClasses.addAll(c.extendedExistingClass.EAllSuperTypes)

			// sub-classes of extended class
			for (someEClass : mm.eAllContents.toSet.filter(EClass)) {
				if (someEClass.EAllSuperTypes.contains(c.extendedExistingClass)) {
					allRuntimeClasses.add(someEClass)
				}
			}
		}
		allRuntimeClasses.addAll(allNewEClasses)

		// We also store the dual set of classes not linked to anything dynamic
		allStaticClasses.addAll(mm.eAllContents.toSet.filter(EClass).filter[c|!allRuntimeClasses.contains(c)])

		// Here we find classes that inherit from multiple concrete classes
		// This allows later to handle multiple non-conflicting "originalObject" references in such cases
		val Set<EClass> multipleOrig = new HashSet
		for (rc : allRuntimeClasses) {
			val concreteSuperTypes = rc.EAllSuperTypes.filter[c|!c.abstract].toSet
			multipleOrig.addAll(concreteSuperTypes)

		}

		// We go through all dynamic classes and we create traced versions of them
		for (runtimeClass : allRuntimeClasses) {

			// Creating the traced version of the class by copying the runtime class
			// TODO if we remove static objects creation, could we remove such properties as well? we should always have an "originalObject" ref
			val traceClass = runtimeClassescopier.copy(runtimeClass) as EClass
			traceClass.name = TraceMMStrings.class_createTraceClassName(runtimeClass)

			// We recreate the same package organization
			val tracedPackage = obtainTracedPackage(runtimeClass.EPackage)
			tracedPackage.EClassifiers.add(traceClass)

			// Removing all containments in the references obtained
			for (prop : traceClass.EReferences) {
				prop.containment = false
				prop.EOpposite = null
			}

			// We remove all attributes from the class (since accessible from "originalObject"): 
			traceClass.EStructuralFeatures.removeAll(traceClass.EStructuralFeatures.filter(EAttribute))

			// If this is a class extension, then we add a reference, to be able to refer to the element of the original model (if originally static element of the model)
			val boolean notNewClass = !allNewEClasses.contains(runtimeClass)
			val boolean notAbstract = !traceClass.abstract

			if (notNewClass && runtimeClass2ClassExtension.containsKey(runtimeClass)) {
				val traceabilityAnnotationValue = computeTraceabilityAnnotationValue(
					runtimeClass2ClassExtension.get(runtimeClass));
				if (traceabilityAnnotationValue != null)
					ExecutionMetamodelTraceability.createTraceabilityAnnotation(traceClass,
						traceabilityAnnotationValue);
			}

			// Also we must check that there isn't already a concrete class in the super classes, which would have its own origObj ref
			// TODO this is not enough ! it is possible to have a concrete class with no originalObject link! (eg new class in the extension)
			val boolean onlyAbstractSuperTypes = runtimeClass.EAllSuperTypes.forall[c|c.abstract]
			if (notNewClass && notAbstract && onlyAbstractSuperTypes) {
				var refName = ""
				if (multipleOrig.contains(runtimeClass)) {
					refName = TraceMMStrings.ref_OriginalObject_MultipleInheritance(runtimeClass)
				} else {
					refName = TraceMMStrings.ref_OriginalObject
				}
				val EReference ref = addReferenceToClass(traceClass, refName, runtimeClass)
				traceability.addRefs_originalObject(traceClass, ref)
			}

			// Link TracedObjects -> Trace class
			if (!traceClass.abstract) {
				val refTraceSystem2Trace = addReferenceToClass(traceMMExplorer.tracedObjectsClass,
					TraceMMStrings.ref_createTracedObjectsToTrace(traceClass), traceClass)
				refTraceSystem2Trace.containment = true
				refTraceSystem2Trace.ordered = false
				refTraceSystem2Trace.unique = true
				refTraceSystem2Trace.upperBound = -1
				refTraceSystem2Trace.lowerBound = 0
			}

			// Then going through all properties for the remaining generation
			var Set<EStructuralFeature> runtimeProperties = new HashSet<EStructuralFeature>
			if (allNewEClasses.contains(runtimeClass))
				runtimeProperties.addAll(runtimeClass.EStructuralFeatures)
			else {
				val classExtension = runtimeClass2ClassExtension.get(runtimeClass)
				if (classExtension != null) {
					runtimeProperties.addAll(classExtension.newProperties);
				}
//				for (c2 : mmext.classesExtensions) {
//					if(c2.extendedExistingClass == runtimeClass) {
//						runtimeProperties.addAll(c2.newProperties)
//					}
//				}
			}

			// We remove the copied properties that will become traces
			for (prop : runtimeProperties)
				traceClass.EStructuralFeatures.remove(runtimeClassescopier.get(prop))

			// Storing traceability stuff
			traceability.putTracedClasses(runtimeClass, traceClass)
			if (!runtimeProperties.isEmpty)
				traceability.addRuntimeClass(runtimeClass)

			// We go through the runtime properties of this class
			for (runtimeProperty : runtimeProperties) {

				// Storing traceability stuff
				traceability.addMutableProperty(runtimeClass, runtimeProperty)

				// State class
				val stateClass = EcoreFactory.eINSTANCE.createEClass
				stateClass.name = TraceMMStrings.class_createStateClassName(runtimeClass, runtimeProperty)

				// We copy the property inside the state class
				val copiedProperty = runtimeClassescopier.copy(runtimeProperty) as EStructuralFeature
				if (copiedProperty instanceof EReference) {
					copiedProperty.containment = false
					copiedProperty.EOpposite = null // useful ? copy references later...
				}
				stateClass.EStructuralFeatures.add(copiedProperty)
				traceMMExplorer.statesPackage.EClassifiers.add(stateClass)

				ExecutionMetamodelTraceability.createTraceabilityAnnotation(stateClass,
					ExecutionMetamodelTraceability.getTraceabilityAnnotationValue(runtimeProperty));

				// Link Trace class -> State class
				val refTrace2State = addReferenceToClass(traceClass,
					TraceMMStrings.ref_createTraceToState(runtimeProperty), stateClass);
				refTrace2State.containment = true
				refTrace2State.ordered = true
				refTrace2State.unique = true
				refTrace2State.lowerBound = 0
				refTrace2State.upperBound = -1

				traceability.putTraceOf(runtimeProperty, refTrace2State)

				// Link State class -> Trace class (bidirectional)
				val refState2Trace = addReferenceToClass(stateClass, TraceMMStrings.ref_StateToTrace, traceClass);
				refState2Trace.upperBound = 1
				refState2Trace.lowerBound = 1
				refState2Trace.EOpposite = refTrace2State
				refTrace2State.EOpposite = refState2Trace

				// Link GlobalState -> State class
				val refGlobal2State = addReferenceToClass(traceMMExplorer.globalStateClass,
					TraceMMStrings.ref_createGlobalToState(stateClass), stateClass);
				refGlobal2State.ordered = false
				refGlobal2State.unique = true
				refGlobal2State.upperBound = -1
				refGlobal2State.lowerBound = 0

				traceability.putGlobalToState(runtimeProperty, refGlobal2State)

				// Link State class -> GlobalState (bidirectional)
				val refState2Global = addReferenceToClass(stateClass, TraceMMStrings.ref_StateToGlobal,
					traceMMExplorer.globalStateClass);
				refState2Global.upperBound = -1
				refState2Global.lowerBound = 1
				refState2Global.EOpposite = refGlobal2State
				refGlobal2State.EOpposite = refState2Global
			}
		}

	}

	def String computeTraceabilityAnnotationValue(ClassExtension classExtension) {
		var String traceabilityAnnotationValue = null;
		if (!classExtension.newProperties.empty) {
			val mutableProperty = classExtension.newProperties.get(0);
			val String mutablePropertyTraceabilityValue = ExecutionMetamodelTraceability.
				getTraceabilityAnnotationValue(mutableProperty)
			if (mutablePropertyTraceabilityValue != null) {
				val classSubstringStartIndex = mutablePropertyTraceabilityValue.lastIndexOf("/");
				traceabilityAnnotationValue = mutablePropertyTraceabilityValue.substring(0, classSubstringStartIndex);
			}
		}
		return traceabilityAnnotationValue;
	}

	private def boolean isInPackage(EPackage c, EPackage p) {
		if (c != null && p != null && c == p) {
			return true
		} else if (c.ESuperPackage != null) {
			return isInPackage(c.ESuperPackage, p)
		} else {
			return false
		}
	}

	def Set<Rule> gatherOverrides(Rule rule) {
		val Set<Rule> result = new HashSet
		result.add(rule)
		result.addAll(rule.overridenBy)
		for (ov : rule.overridenBy) {
			result.addAll(gatherOverrides(ov))
		}
		return result
	}

	private def Set<Rule> gatherStepCalls(Rule rule, Set<Rule> inProgress) {
		val Set<Rule> result = new HashSet
		// To avoid cycles
		if (!inProgress.contains(rule)) {
			inProgress.add(rule)
			// Case step rule: stop
			if (rule.isStepRule) {
				result.add(rule)
			} // Case non step, recursive
			else {
				for (called : rule.calledRules) {
					val gathered = gatherStepCalls(called, inProgress)
					result.addAll(gathered)
				}
			}
		} 
		
		return result
	}

	private Map<Rule, EClass> stepRuleToClass = new HashMap

	private def getStepClass(Rule stepRule) {
		if (stepRuleToClass.containsKey(stepRule)) {
			return stepRuleToClass.get(stepRule)
		} else {
			val stepClass = EcoreFactory.eINSTANCE.createEClass
			traceMMExplorer.eventsPackage.EClassifiers.add(stepClass)
			stepRuleToClass.put(stepRule, stepClass)
			return stepClass
		}
	}

	private def handleEvents() {

		val Set<Rule> allRules = new HashSet
		allRules.addAll(mmext.rules)
		val stepRules = allRules.filter[r|r.isStepRule].toSet

		// Flatten the rule graph regarding function overrides
		for (rule : allRules) {
			rule.calledRules.addAll(rule.calledRules.map[cr|gatherOverrides(cr)].flatten.toSet)
		}

		// "Merge" normal rules into step rules (ie. inlining)
		for (rule : stepRules) {
			val calledNonStepRules = rule.calledRules.filter[r|!r.isStepRule].toSet
			// For each called non step rule
			for (called : calledNonStepRules) {
				val Set<Rule> inProgress = new HashSet
				// We gather all step rules transitively called
				val gathered = gatherStepCalls(called, inProgress)
				rule.calledRules.addAll(gathered)
			}
			// And we remove the calls to the non step rules
			rule.calledRules.removeAll(calledNonStepRules)
		}
		// Now "stepRules" contains a set of step rules that only call other step rules
		// We directly have the information for the big/small steps creation
		// -----------------------------------------
		for (stepRule : stepRules) {

			// Creation of the step class (or reuse)
			val stepClass = getStepClass(stepRule)

			// Default basic name
			stepClass.name = stepRule.operation.name

			// We put a single "this" parameter
			EcoreCraftingUtil.addReferenceToClass(stepClass, "this", stepRule.containingClass)

			// And a FQN name
			stepClass.name = StepStrings.stepClassName(stepRule.containingClass, stepRule.operation)

			// Link EventsTraces -> Event class
			val ref = addReferenceToClass(traceMMExplorer.eventsClass,
				TraceMMStrings.ref_createEventsTracesToEvent(stepClass), stepClass)

			ref.lowerBound = 0
			ref.upperBound = -1
			ref.containment = true
			traceability.addEventTrace(stepClass, ref)
			traceability.addEventClass(stepClass)

			// Case Small Step
			if (stepRule.calledRules.isEmpty) {

				// Adding inheritance to Event abstract class
				stepClass.ESuperTypes.add(traceMMExplorer.eventOccClass)

			} // Case Big Step
			else {

				traceability.addMacroEventClass(stepClass)

				// Adding inheritance to MacroEvent abstract class
				stepClass.ESuperTypes.add(traceMMExplorer.macroEventClass)

				// SubStepSuperClass
				val EClass subStepSuperClass = EcoreFactory.eINSTANCE.createEClass
				traceMMExplorer.eventsPackage.EClassifiers.add(subStepSuperClass)
				subStepSuperClass.name = StepStrings.abstractSubStepClassName(stepRule.containingClass,
					stepRule.operation)
				subStepSuperClass.abstract = true

				// Link StepClass -> SubStepSuperClass
				val ref2 = EcoreCraftingUtil.addReferenceToClass(stepClass, StepStrings.ref_BigStepToSub,
					subStepSuperClass)
				ref2.ordered = true
				ref2.containment = false
				ref2.lowerBound = 0
				ref2.upperBound = -1

				// Fill step class
				val EClass fillStepClass = EcoreFactory.eINSTANCE.createEClass
				traceMMExplorer.eventsPackage.EClassifiers.add(fillStepClass)
				fillStepClass.name = StepStrings.fillStepClassName(stepRule.containingClass, stepRule.operation)

				// Inheritance Fill > SubStepSuper
				fillStepClass.ESuperTypes.add(subStepSuperClass)

				for (calledStepRule : stepRule.calledRules) {

					// For each called step rule, we create an step class (if not created already)
					val EClass subStepClass = getStepClass(calledStepRule)

					// Inheritance SubStep -> SubStepSuper
					subStepClass.ESuperTypes.add(subStepSuperClass)

				}

			}

		}

	}

}
