package fr.inria.diverse.trace.metamodel.generator

import fr.inria.diverse.trace.commons.EMFUtil
import java.io.IOException
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl
import org.eclipse.xtend.lib.annotations.Accessors
import ecorext.Ecorext
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.util.Diagnostician
import org.eclipse.emf.common.util.Diagnostic

class TraceMMGenerator {

	// Inputs
	private val Ecorext mmext
	private val EPackage mm
	private val ResourceSet rs
	private val String languageName
	private val boolean gemoc

	// Outputs
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) val EPackage tracemmresult
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER) val TraceMMGenerationTraceability traceability

	// Base classes
	private val TraceMMExplorer traceMMExplorer

	private var boolean done = false

	new(Ecorext mmext, EPackage mm, boolean gemoc) {

		// Storing inputs
		this.mmext = mmext
		this.mm = mm
		this.gemoc = gemoc

		// Create name of the trace metamodel 
		languageName = mm.name.replaceAll(" ", "") + "Trace"

		// Creating resource set to work with
		this.rs = new ResourceSetImpl()
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

		// Create the root package by loading the base ecore and changing its name and stuff
		val Resource base = EMFUtil.loadModelURI(
			URI.createPlatformPluginURI("fr.inria.diverse.trace.metamodel.generator/model/base.ecore", true), rs)
		tracemmresult = base.contents.get(0) as EPackage
		base.contents.remove(tracemmresult)
		tracemmresult.name = languageName
		tracemmresult.nsURI = languageName // TODO
		tracemmresult.nsPrefix = languageName

		// Create an explorer to generically manipulate the generated trace metamodel
		// (mostly base classes)
		this.traceMMExplorer = new TraceMMExplorer(tracemmresult)

		// Changing packages names 
		// TODO use strings classes to name the languages
		traceMMExplorer.stepsPackage.nsURI = languageName + "_Steps"
		traceMMExplorer.statesPackage.nsURI = languageName + "_States"

		// Finally, initializing traceability class 
		this.traceability = new TraceMMGenerationTraceability(traceMMExplorer, mmext)

	}

	public def void computeAllMaterial() throws IOException {
		if (!done) {

			val statesGen = new TraceMMGeneratorStates(mmext, mm, traceability, traceMMExplorer, languageName,
				tracemmresult, gemoc)
			statesGen.process

			val stepsGen = new TraceMMGeneratorSteps(mmext, tracemmresult, traceability, traceMMExplorer, gemoc)
			stepsGen.process

			// Validation
			val results = Diagnostician.INSTANCE.validate(this.mmext);
			val error = results.children.findFirst[r|r.severity == Diagnostic.ERROR]
			if (error != null)
				throw new IllegalStateException(
					"The generated trace metamodel is invalid for at least one reason: " + error)

			done = true
		} else {
			println("ERROR: already computed.")
		}
	}

	public def void sortResult() {
		sortEPackage(tracemmresult)
	}

	private def void sortEPackage(EPackage ePack) {
		for (EPackage subPackage : ePack.ESubpackages) {
			sortEPackage(subPackage);
		}
		// sort EClass in EPackage
		val sortedSteps = ePack.EClassifiers.sortBy[name]
		ePack.EClassifiers.clear;
		ePack.EClassifiers.addAll(sortedSteps);

		for (EClass eClass : ePack.EClassifiers.filter(EClass)) {
			sortEClassFeatures(eClass);
			sortEClassInheritance(eClass);
		}
	}

	private def void sortEClassFeatures(EClass eClass) {
		// sort class attributes and references
		val sortedClassFeatures = eClass.EStructuralFeatures.sortBy[name]
		eClass.EStructuralFeatures.clear
		eClass.EStructuralFeatures.addAll(sortedClassFeatures)

	}

	private def void sortEClassInheritance(EClass eClass) {
		// sort class inheritance
		val sortedClassInheritance = eClass.ESuperTypes.sortBy[name]
		eClass.ESuperTypes.clear
		eClass.ESuperTypes.addAll(sortedClassInheritance)
	}

}
