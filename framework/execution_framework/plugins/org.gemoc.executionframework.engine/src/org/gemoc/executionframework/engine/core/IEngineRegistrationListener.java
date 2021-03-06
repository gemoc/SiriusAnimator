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
package org.gemoc.executionframework.engine.core;

import org.gemoc.xdsmlframework.api.core.IBasicExecutionEngine;

public interface IEngineRegistrationListener {

	void engineRegistered(IBasicExecutionEngine engine);

	void engineUnregistered(IBasicExecutionEngine engine);
	
}
