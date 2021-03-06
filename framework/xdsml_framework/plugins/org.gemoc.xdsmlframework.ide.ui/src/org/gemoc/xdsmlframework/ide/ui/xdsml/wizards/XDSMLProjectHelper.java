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
package org.gemoc.xdsmlframework.ide.ui.xdsml.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.gemoc.executionframework.xdsml_base.LanguageDefinition;
import org.gemoc.xdsmlframework.ide.ui.Activator;

public class XDSMLProjectHelper {

	/**
	 * Computer the base name for a project base on xdsml project
	 * ie. if it ends with .xdsml this suffix is removed
	 * @param xdsmlProject
	 * @return
	 */
	public static String baseProjectName(IProject xdsmlProject){
		int index = xdsmlProject.getName().indexOf(".xdsml");
		if(index != -1){
			return xdsmlProject.getName().substring(0, index);		
		}
		return xdsmlProject.getName();
	}
	
	public static LanguageDefinition getLanguageDefinition(IProject searchedProject){

		if(searchedProject != null){
			IFile configFile = searchedProject.getFile(new Path(
					Activator.GEMOC_PROJECT_CONFIGURATION_FILE));
			if (configFile.exists()) {
				// Obtain a new resource set
				ResourceSet resSet = new ResourceSetImpl();
				// get the resource
				Resource resource = resSet
						.getResource(URI.createURI(configFile.getLocationURI()
								.toString()), true);
				
				
				return (LanguageDefinition) resource
						.getContents().get(0);
			}
		}
		return null;
	}
}
