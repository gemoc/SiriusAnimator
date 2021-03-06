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
package org.gemoc.executionframework.engine.ui.debug;

import org.gemoc.xdsmlframework.api.core.IBasicExecutionEngine;

import fr.obeo.dsl.debug.ide.event.IDSLDebugEventProcessor;

public abstract class AbstractGemocDebuggerFactory {

	public AbstractGemocDebuggerFactory(){
		
	}
	
	public abstract AbstractGemocDebugger createDebugger(IDSLDebugEventProcessor target, IBasicExecutionEngine engine); 
}
