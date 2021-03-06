package fr.inria.diverse.trace.plaink3.tracematerialextractor

import ecorext.Ecorext
import ecorext.EcorextFactory
import ecorext.Rule
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EOperation
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.jdt.core.IAnnotation
import org.eclipse.jdt.core.IMethod
import org.eclipse.jdt.core.IType
import org.eclipse.jdt.internal.corext.callhierarchy.CallLocation
import org.eclipse.jdt.core.IMember
import fr.inria.diverse.commons.eclipse.callgraph.CallHierarchyHelper

class K3StepExtractor {

	// Input
	private val Set<IType> allClasses
	private val EPackage extendedMetamodel

	// Input / Output
	private val Ecorext ecoreExtension

	// Transient
	private val Map<IType, EClass> stepAspectsClassToAspectedClasses = new HashMap
	private val Set<IMethod> allFunctions = new HashSet
	private val Set<IMethod> stepFunctions = new HashSet
	private val Map<IMethod, Rule> functionToRule = new HashMap
	private val Set<IType> inspectedClasses = new HashSet
	private val Set<CallLocation> allCallSites = new HashSet

	// bind called methods to their call sites
	private val Map<CallLocation, IMethod> callToFunction = new HashMap

	new(Set<IType> aspects, String languageName, EPackage extendedMetamodel, Ecorext inConstructionEcorext) {
		this.allClasses = aspects
		this.extendedMetamodel = extendedMetamodel
		this.ecoreExtension = inConstructionEcorext

	}

	public def void generate() {
		generateStepFromXtend(allClasses)
	}

	private def Rule getRuleOfFunction(IMethod function) {
		if (functionToRule.containsKey(function))
			return functionToRule.get(function)
		else {
			val Rule rule = EcorextFactory.eINSTANCE.createRule;
			this.ecoreExtension.rules.add(rule)

			// We find the ecore class matching the aspected java class 
			val containingClass = function.declaringType
			rule.containingClass = stepAspectsClassToAspectedClasses.get(containingClass)

			var EOperation candidate = null
			if (rule.containingClass != null) {
				candidate = rule.containingClass.EAllOperations.findFirst [ o |
					o.name.equals(function.elementName)
				]

			}
			if (candidate != null) {
				rule.operation = candidate
			} else {
				rule.operation = xtendFunctionToEOperation(function)
			}

			rule.stepRule = stepFunctions.contains(function)
			rule.main = isMain(function)
			functionToRule.put(function, rule)
			return rule
		}
	}

	private def void inspectForBigStep(IMethod function) {

		// We consider that each Kermeta function is a transformation rule (even through we cannot know if it modifies anything)		
		val Rule rule = getRuleOfFunction(function)

		// We retrieve which functions are called by the function
		val calledFunctions = getCalledFunctions(function)
		for (calledFunction : calledFunctions) {
			if (calledFunction !== null) {
				val Rule calledRule = getRuleOfFunction(calledFunction)
				rule.calledRules.add(calledRule)
			}
		}

		// Finally we look if this function was overriden/implemented by subtypes
		// TODO use annotation?
		val xclass = function.declaringType
		val subtypes = getSubClasses(xclass)
		for (t : subtypes) {
			for (f : t.methods) {
				if (f.elementName.equals(function.elementName)) {
					val Rule overridingRule = getRuleOfFunction(f)
					rule.overridenBy.add(overridingRule)
				}
			}
		}
	}

	private def EOperation xtendFunctionToEOperation(IMethod function) {
		val result = EcoreFactory.eINSTANCE.createEOperation
		result.name = function.elementName
		// TODO finish the translation and/or ask Thomas
		// TODO or consider it is already in the ecore?
		return result
	}

	private def void inspectClass(IType type) {

		if (!inspectedClasses.contains(type)) {

			// Collect all functions not generated by Kermeta 3 (ie prefixed "_privk3_")
			allFunctions.addAll(type.methods.filter[!elementName.startsWith("_privk3_")])

			// For each aspect annotation of the class 
			for (a : getAspectAnnotations(type)) {

				// We find the JVM aspected class
				val aspectedEClass = getAspectized(a)

				// We store the aspect class and the aspected class
				stepAspectsClassToAspectedClasses.put(type, aspectedEClass)

				// And we store all the functions with @Step
				stepFunctions.addAll(type.methods.filter[isStep])
			}
			inspectedClasses.add(type)
		}
	}

	private def void inspectFunction(IMethod function) {
		val Set<CallLocation> callingSites = CallHierarchyHelper.getCallLocationsOf(function)
		callingSites.forEach [ call |
			callToFunction.put(call, function);
		]
		allCallSites.addAll(callingSites)
	}

	private def generateStepFromXtend(Set<IType> files) {

		// First we look for functions, step aspects and step functions
		// Will fill the variables stepAspectsClassToAspectedClasses, allFunctions and stepFunctions		
		for (c : allClasses) {
			inspectClass(c)
		}

		// Collect call sites
		allFunctions.forEach[inspectFunction]

		// Next we create the Rule objects with all that
		for (function : allFunctions) {
			inspectForBigStep(function)
		}

	}

	/**
	 * Find annotations "@Aspect"
	 */
	private def List<IAnnotation> getAspectAnnotations(IType type) {
		// TODO compare with: fr.inria.diverse.k3.al.annotationprocessor.Aspect
		if (type.isClass) {
			return type.annotations.filter [ annot |
				val name = annot.elementName // may be qualified
				val lastDotIndex = name.lastIndexOf('.')
				var simpleName = name
				if (lastDotIndex !== -1) {
					simpleName = name.substring(lastDotIndex + 1)
				}
				simpleName.equals("Aspect")
			].toList
		}

		return new ArrayList<IAnnotation>()
	}

	private def boolean testAnnotation(IMethod method, String annotationSimpleName) {
		// TODO: compare with: fr.inria.diverse.k3.al.annotationprocessor.XXX
		return method.annotations.exists [ annot |
			val name = annot.elementName // may be qualified
			val lastDotIndex = name.lastIndexOf('.')
			var simpleName = name
			if (lastDotIndex !== -1) {
				simpleName = name.substring(lastDotIndex + 1)
			}
			return simpleName.equals(annotationSimpleName)
		]
	}

	/**
	 * Return true if 'method' is tagged with "@Step"
	 */
	private def boolean isStep(IMethod method) {
		testAnnotation(method, "Step")
	}
	
	/**
	 * Return true if 'method' is tagged with "@Main"
	 */
	private def boolean isMain(IMethod method) {
		testAnnotation(method, "Main")
	}

	/**
	 * Return direct sub types
	 */
	private def List<IType> getSubClasses(IType type) {
		val hierarchy = type.newTypeHierarchy(new NullProgressMonitor)
		return hierarchy.getSubclasses(type)
	}

	private def EClass getAspectized(IAnnotation annot) {
		val aspectedClassName = annot.memberValuePairs.findFirst[p|p.memberName == "className"].value as String
		return extendedMetamodel.eAllContents.filter(EClass).findFirst[c1|aspectedClassName.equals(c1.name)]
	}

	private def List<IMethod> getCalledFunctions(IMethod function) {
		return allCallSites.filter[getRealMethod(getContainingAspectMethod(it.member as IMethod)) == function].sortBy [
			start
		].map[callToFunction.get(it)].toList
	}

	/**
	 * If 'function' is a body generated by K3, return the corresponding method
	 */
	private def IMethod getRealMethod(IMember function) {
		// function may be the 'body' generated by Kermeta 3,
		// so we need the real function
		if (function !== null) {
			var name = function.elementName
			if (name.startsWith("_privk3_")) {
				name = name.substring(8);
			}
			val dumbFinalName = name
			val realFunction = allFunctions.findFirst [ // TODO: check arguments
				elementName == dumbFinalName && declaringType == function.declaringType
			]
			return realFunction
		}
		return null
	}

	/**
	 * Return the top level method in a type tagged @aspect
	 * that contains 'function'<br>
	 * <br>
	 * Return 'function' if it is already a top level method.<br>
	 * <br>
	 * Return null if not inside a type with @aspect
	 */
	private def IMethod getContainingAspectMethod(IMethod function) {
		val container = function.declaringType
		if (allClasses.contains(container)) {
			return function
		}

		// function can be in annonymous/inner classes (e.g in lamba)
		var parent = function.parent
		while (parent !== null) {

			if (parent instanceof IMethod) {
				return getContainingAspectMethod(parent)
			}

			parent = parent.parent
		}

		return null
	}
}
