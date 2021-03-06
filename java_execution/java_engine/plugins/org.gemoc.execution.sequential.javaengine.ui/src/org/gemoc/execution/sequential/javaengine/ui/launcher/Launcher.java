/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.gemoc.execution.sequential.javaengine.ui.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.ecore.EObject;
import org.gemoc.commons.eclipse.ui.ViewHelper;
import org.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.gemoc.execution.sequential.javaengine.SequentialModelExecutionContext;
import org.gemoc.execution.sequential.javaengine.ui.Activator;
import org.gemoc.execution.sequential.javaengine.ui.debug.GenericSequentialModelDebugger;
import org.gemoc.execution.sequential.javaengine.ui.debug.OmniscientGenericSequentialModelDebugger;
import org.gemoc.executionframework.engine.commons.EngineContextException;
import org.gemoc.executionframework.engine.commons.ModelExecutionContext;
import org.gemoc.executionframework.engine.mse.MSEOccurrence;
import org.gemoc.executionframework.engine.ui.commons.RunConfiguration;
import org.gemoc.executionframework.engine.ui.debug.AbstractGemocDebugger;
import org.gemoc.executionframework.engine.ui.debug.AnnotationMutableFieldExtractor;
import org.gemoc.executionframework.engine.ui.debug.IMutableFieldExtractor;
import org.gemoc.executionframework.engine.ui.debug.IntrospectiveMutableFieldExtractor;
import org.gemoc.executionframework.engine.ui.launcher.AbstractSequentialGemocLauncher;
import org.gemoc.executionframework.ui.views.engine.EnginesStatusView;
import org.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.gemoc.xdsmlframework.api.core.IBasicExecutionEngine;
import org.gemoc.xdsmlframework.api.core.ISequentialExecutionEngine;

import fr.inria.diverse.commons.messagingsystem.api.MessagingSystem;
import fr.inria.diverse.trace.gemoc.api.IMultiDimensionalTraceAddon;
import fr.obeo.dsl.debug.ide.IDSLDebugger;
import fr.obeo.dsl.debug.ide.event.DSLDebugEventDispatcher;

public class Launcher extends AbstractSequentialGemocLauncher {

	public final static String TYPE_ID = Activator.PLUGIN_ID + ".launcher";

	@Override
	protected IBasicExecutionEngine createExecutionEngine(RunConfiguration runConfiguration, ExecutionMode executionMode)
			throws CoreException, EngineContextException {
		// create and initialize engine
		IBasicExecutionEngine executionEngine = new PlainK3ExecutionEngine();
		ModelExecutionContext executioncontext = new SequentialModelExecutionContext(runConfiguration, executionMode);
		executioncontext.initializeResourceModel();
		executionEngine.initialize(executioncontext);
		return executionEngine;
	}

	@Override
	protected IDSLDebugger getDebugger(ILaunchConfiguration configuration, DSLDebugEventDispatcher dispatcher,
			EObject firstInstruction, IProgressMonitor monitor) {

		ISequentialExecutionEngine engine = (ISequentialExecutionEngine) _executionEngine;
		AbstractGemocDebugger res;
		Set<IMultiDimensionalTraceAddon> traceAddons = _executionEngine
				.getAddonsTypedBy(IMultiDimensionalTraceAddon.class);

		// We don't want to use trace managers that only work with a subset of
		// the execution state
		traceAddons.removeIf(traceAddon -> {
			return traceAddon.getTraceConstructor() != null && traceAddon.getTraceConstructor().isPartialTraceConstructor();
		});

		if (traceAddons.isEmpty()) {
			res = new GenericSequentialModelDebugger(dispatcher, engine);
		} else {
			res = new OmniscientGenericSequentialModelDebugger(dispatcher, engine);
		}
		// We create a list of all mutable data extractors we want to try
		List<IMutableFieldExtractor> extractors = new ArrayList<IMutableFieldExtractor>();
		// We put annotation first
		extractors.add(new AnnotationMutableFieldExtractor());
		// Then introspection
		extractors.add(new IntrospectiveMutableFieldExtractor(_executionEngine.getExecutionContext()
				.getRunConfiguration().getLanguageName()));
		res.setMutableFieldExtractors(extractors);

		// If in the launch configuration it is asked to pause at the start,
		// we add this dummy break
		try {
			if (configuration.getAttribute(RunConfiguration.LAUNCH_BREAK_START, false)) {
				res.addPredicateBreak(new BiPredicate<IBasicExecutionEngine, MSEOccurrence>() {
					@Override
					public boolean test(IBasicExecutionEngine t, MSEOccurrence u) {
						return true;
					}
				});
			}
		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}

		_executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(res);
		return res;
	}

	@Override
	protected String getLaunchConfigurationTypeID() {
		return TYPE_ID;
	}

	@Override
	protected String getDebugJobName(ILaunchConfiguration configuration, EObject firstInstruction) {
		return "Gemoc debug job";
	}

	@Override
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}

	@Override
	protected String getModelIdentifier() {
		if (_executionEngine instanceof PlainK3ExecutionEngine)
			return Activator.PLUGIN_ID + ".debugModel";
		else
			return MODEL_ID;
	}

	@Override
	protected void prepareViews() {
		ViewHelper.retrieveView(EnginesStatusView.ID);
	}

	@Override
	protected RunConfiguration parseLaunchConfiguration(ILaunchConfiguration configuration) throws CoreException {
		return new RunConfiguration(configuration);
	}

	@Override
	protected void error(String message, Exception e) {
		Activator.error(message, e);
	}

	@Override
	protected MessagingSystem getMessagingSystem() {
		return Activator.getDefault().getMessaggingSystem();
	}

	@Override
	protected void setDefaultsLaunchConfiguration(ILaunchConfigurationWorkingCopy configuration) {

	}
}
