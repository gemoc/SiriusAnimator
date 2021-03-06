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
package org.gemoc.xdsmlframework.api.core;

import org.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public interface IExecutionPlatform extends IDisposable
{

	

	/**
	 * @return The model loader used to load the model to be executed.
	 */
	IModelLoader getModelLoader();

	void addEngineAddon(IEngineAddon addon);
	void removeEngineAddon(IEngineAddon addon);
	Iterable<IEngineAddon> getEngineAddons();
	

}
