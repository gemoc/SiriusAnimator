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
package org.gemoc.executionframework.ui.views.engine.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.gemoc.commons.eclipse.ui.ViewHelper;
import org.gemoc.executionframework.ui.Activator;
import org.gemoc.executionframework.ui.views.engine.EnginesStatusView;
import org.gemoc.xdsmlframework.api.core.IBasicExecutionEngine;
import org.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;

public class DisposeStoppedEngineAction extends AbstractEngineAction 
{

	public DisposeStoppedEngineAction()
	{
		setText("Dispose stopped engine");
		setToolTipText("Dispose stopped engine");
		ImageDescriptor id = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ISharedImages.IMG_ELCL_REMOVE);
		setImageDescriptor(id);
	}
	
	@Override
	public void run()
	{
		EnginesStatusView view = ViewHelper.retrieveView(EnginesStatusView.ID);
		view.removeStoppedEngines();
		
		//EnginesStatusView view = ViewHelper.retrieveView(EnginesStatusView.ID);
		if(view.getSelectedEngine() != null &&  view.getSelectedEngine().getRunningStatus().equals(RunStatus.Stopped))
		{
			view.getSelectedEngine().dispose();
		}
		else
		{
			showMessage(view.getSite(), "please stop the Engine before trying to dispose it");		
		}
	}

	
	@Override
	public void engineSelectionChanged(IBasicExecutionEngine engine) 
	{
		super.engineSelectionChanged(engine);
		if (getCurrentSelectedEngine() != null)
		{
			setEnabled(	getCurrentSelectedEngine().getRunningStatus().equals(RunStatus.Stopped));
						
		}
	}
}
