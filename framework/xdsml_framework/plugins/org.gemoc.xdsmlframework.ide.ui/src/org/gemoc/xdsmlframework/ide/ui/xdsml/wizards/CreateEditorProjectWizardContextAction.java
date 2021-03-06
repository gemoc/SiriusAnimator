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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
//import org.eclipse.sirius.editor.tools.internal.wizards.ViewpointSpecificationProjectWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.gemoc.commons.eclipse.core.resources.FileFinderVisitor;
import org.gemoc.commons.eclipse.core.resources.NewProjectWorkspaceListener;
import org.gemoc.commons.eclipse.ui.WizardFinder;
import org.gemoc.commons.eclipse.ui.dialogs.SelectAnyIProjectDialog;
import org.gemoc.executionframework.xdsml_base.EditorProject;
import org.gemoc.executionframework.xdsml_base.LanguageDefinition;
import org.gemoc.executionframework.xdsml_base.SiriusEditorProject;
import org.gemoc.executionframework.xdsml_base.TreeEditorProject;
import org.gemoc.executionframework.xdsml_base.XTextEditorProject;
import org.gemoc.executionframework.xdsml_base.impl.Xdsml_baseFactoryImpl;
import org.gemoc.xdsmlframework.extensions.sirius.wizards.NewGemocSiriusProjectWizard;
import org.gemoc.xdsmlframework.ide.ui.Activator;
import org.gemoc.xdsmlframework.ui.utils.dialogs.SelectODesignIProjectDialog;
import org.gemoc.xdsmlframework.ui.utils.dialogs.SelectXtextIProjectDialog;

//import org.eclipse.emf.ecoretools.design.wizard.EcoreModelerWizard;

/**
 * This class is both a context for the wizard and a Command that will be
 * executed
 * 
 * @author dvojtise
 *
 */
public class CreateEditorProjectWizardContextAction {

	public enum CreateEditorProjectAction {
		CREATE_NEW_EMFTREE_PROJECT, CREATE_NEW_XTEXT_PROJECT, CREATE_NEW_SIRIUS_PROJECT, SELECT_EXISTING_EMFTREE_PROJECT, SELECT_EXISTING_XTEXT_PROJECT, SELECT_EXISTING_OD_PROJECT
	};

	public CreateEditorProjectAction actionToExecute = CreateEditorProjectAction.CREATE_NEW_EMFTREE_PROJECT;

	// one of these must be set, depending on it it will work on the file or
	// directly in the model
	protected IProject gemocLanguageIProject = null;
	protected LanguageDefinition gemocLanguageModel = null;
	protected IProject createdProject = null;

	public CreateEditorProjectWizardContextAction(
			IProject updatedGemocLanguageProject) {
		gemocLanguageIProject = updatedGemocLanguageProject;
	}

	public CreateEditorProjectWizardContextAction(
			IProject updatedGemocLanguageProject,
			LanguageDefinition rootModelElement) {
		gemocLanguageIProject = updatedGemocLanguageProject;
		gemocLanguageModel = rootModelElement;
	}

	public void execute() {
		switch (actionToExecute) {
		case CREATE_NEW_EMFTREE_PROJECT:
			createNewEMFTreeProject();
			break;
		case CREATE_NEW_XTEXT_PROJECT:
			createNewXTextProject();
			break;

		case CREATE_NEW_SIRIUS_PROJECT:
			createNewODProject();
			break;
		case SELECT_EXISTING_EMFTREE_PROJECT:
			selectExistingEMFTreeProject();
			break;
		case SELECT_EXISTING_XTEXT_PROJECT:
			selectExistingXTextProject();
			break;
		case SELECT_EXISTING_OD_PROJECT:
			selectExistingSiriusProject();
			break;
		default:
			break;
		}

	}

	protected void createNewEMFTreeProject() {
		// launch the appropriate wizard

		// "org.eclipse.emf.importer.ui.EMFProjectWizard" = create EMFProject
		// from existing Ecore file
		/*
		 * IWizardDescriptor descriptor = PlatformUI .getWorkbench()
		 * .getNewWizardRegistry() .findWizard(
		 * "fr.obeo.mda.pim.ecore.design.wizard");
		 * 
		 * // Then if we have a wizard, open it. if (descriptor != null) { //
		 * add a listener to capture the creation of the resulting project
		 * NewProjectWorkspaceListener workspaceListener = new
		 * NewProjectWorkspaceListener();
		 * ResourcesPlugin.getWorkspace().addResourceChangeListener
		 * (workspaceListener); try { IWizard wizard; wizard =
		 * descriptor.createWizard(); // this wizard need some dedicated
		 * initialization ((EcoreModelerWizard
		 * )wizard).init(PlatformUI.getWorkbench(), (IStructuredSelection)
		 * PlatformUI
		 * .getWorkbench().getActiveWorkbenchWindow().getSelectionService
		 * ().getSelection());
		 * //((EcoreModelWizard)wizard).init(PlatformUI.getWorkbench(),
		 * (IStructuredSelection) selection); WizardDialog wd = new
		 * WizardDialog(
		 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
		 * wizard); wd.create(); wd.setTitle(wizard.getWindowTitle());
		 * 
		 * int res = wd.open(); if(res == WizardDialog.OK){
		 * ResourcesPlugin.getWorkspace
		 * ().removeResourceChangeListener(workspaceListener); IProject
		 * createdProject = workspaceListener.getLastCreatedProject(); // update
		 * the project configuration model if(createdProject != null){
		 * addEMFProjectToConf(createdProject.getName()); } else{
		 * addEMFProjectToConf(""); } } } catch (CoreException e) {
		 * Activator.error(e.getMessage(), e); } finally{ // make sure to remove
		 * listener in all situations
		 * ResourcesPlugin.getWorkspace().removeResourceChangeListener
		 * (workspaceListener); } }
		 */
		MessageDialog.openWarning(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(),
				"Gemoc Language Workbench UI", "Action not implemented yet");
	}

	protected void createNewXTextProject() {
		/*
		 * MessageDialog.openWarning(
		 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
		 * "Gemoc Language Workbench UI",
		 * "Action not completly implemented yet");
		 */
		// create xtext project from existing ecore model
		// wizard id =
		// org.eclipse.xtext.xtext.ui.wizard.ecore2xtext.NewXtextProjectFromEcoreWizard
		// launch the appropriate wizard

		IWizardDescriptor descriptor = WizardFinder
				.findNewWizardDescriptor("org.eclipse.xtext.xtext.ui.wizard.ecore2xtext.NewXtextProjectFromEcoreWizard");
		// Then if we have a wizard, open it.
		if (descriptor != null) {
			// add a listener to capture the creation of the resulting project
			NewProjectWorkspaceListener workspaceListener = new NewProjectWorkspaceListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(
					workspaceListener);
			try {
				IWizard wizard;
				wizard = descriptor.createWizard();
				// this wizard need some dedicated initialization
				// ((EcoreModelerWizard )wizard).init(PlatformUI.getWorkbench(),
				// (IStructuredSelection)
				// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection());
				// ((EcoreModelWizard)wizard).init(PlatformUI.getWorkbench(),
				// (IStructuredSelection) selection);
				WizardDialog wd = new WizardDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), wizard);
				wd.create();
				wd.setTitle(wizard.getWindowTitle());

				int res = wd.open();
				if (res == WizardDialog.OK) {
					ResourcesPlugin.getWorkspace()
							.removeResourceChangeListener(workspaceListener);
					ArrayList<IProject> newlyCreatedProjects = workspaceListener
							.getNewlyCreatedProjects();
					// find the created project with xtext files in it
					FileFinderVisitor fileFinder = new FileFinderVisitor(
							"xtext");
					for (Iterator<IProject> iterator = newlyCreatedProjects
							.iterator(); iterator.hasNext();) {
						IProject iProject = (IProject) iterator.next();
						iProject.accept(fileFinder);
						if (fileFinder.getFile() != null) {
							createdProject = iProject;
							break;
						}
					}
					// update the project configuration model
					if (createdProject != null) {
						XTextEditorProject editorProject = Xdsml_baseFactoryImpl.eINSTANCE
								.createXTextEditorProject();
						editorProject.setProjectName(createdProject.getName());
						addOrUpdateProjectToConf(editorProject);
					} else {
						Activator
								.error("not able to detect which project was created by wizard",
										null);
					}
				}
			} catch (CoreException e) {
				Activator.error(e.getMessage(), e);
			} finally {
				// make sure to remove listener in all situations
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(
						workspaceListener);
			}
		} else {
			Activator
					.error("wizard with id=org.eclipse.xtext.xtext.ui.wizard.ecore2xtext.NewXtextProjectFromEcoreWizard not found",
							null);
		}
	}

	protected void createNewODProject() {
		final IWizardDescriptor descriptor = WizardFinder
				.findNewWizardDescriptor("org.gemoc.xdsmlframework.extensions.sirius.wizards.NewGemocSiriusProjectWizard");
		// Then if we have a wizard, open it.
		if (descriptor != null) {
			NewProjectWorkspaceListener workspaceListener = new NewProjectWorkspaceListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(
					workspaceListener);
			try {
				IWorkbenchWizard wizard;
				wizard = descriptor.createWizard();
				 ((NewGemocSiriusProjectWizard)wizard).setInitialProjectName(XDSMLProjectHelper
						                                                .baseProjectName(gemocLanguageIProject));

				IWorkbench workbench = PlatformUI.getWorkbench();
				wizard.init(workbench, null);
				WizardDialog wd = new WizardDialog(workbench
						.getActiveWorkbenchWindow().getShell(), wizard);
				wd.create();
				wd.setTitle(wizard.getWindowTitle());

				int res = wd.open();
				if (res == WizardDialog.OK) {
					ResourcesPlugin.getWorkspace()
							.removeResourceChangeListener(workspaceListener);
					createdProject = workspaceListener
							.getLastCreatedProject();
					// update the project configuration model
					if (createdProject != null) {
						SiriusEditorProject editorProject = Xdsml_baseFactoryImpl.eINSTANCE
								.createSiriusEditorProject();
						editorProject.setProjectName(createdProject.getName());
						addOrUpdateProjectToConf(editorProject);
					} else {
						Activator
								.error("not able to detect which project was created by wizard",
										null);
					}
				}
			} catch (CoreException e) {
				Activator.error(e.getMessage(), e);
			} finally {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(
						workspaceListener);
			}
		} else {
			Activator
					.error("wizard with id=org.eclipse.sirius.ui.specificationproject.wizard not found",
							null);
		}
	}

	protected void selectExistingEMFTreeProject() {
		// launch the appropriate wizard
		// TODO filter only projects related to the current domain model
		SelectAnyIProjectDialog dialog = new SelectAnyIProjectDialog(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getShell());
		int res = dialog.open();
		if (res == WizardDialog.OK) {
			// update the project model
			String projectName = ((IResource) dialog.getResult()[0]).getName();
			TreeEditorProject editorProject = Xdsml_baseFactoryImpl.eINSTANCE
					.createTreeEditorProject();
			editorProject.setProjectName(projectName);
			// TODO detection of the current extension
			addOrUpdateProjectToConf(editorProject);
		}
	}

	protected void selectExistingXTextProject() {
		// launch the appropriate wizard
		// TODO filter only projects related to the current domain model
		SelectXtextIProjectDialog dialog = new SelectXtextIProjectDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		int res = dialog.open();
		if (res == WizardDialog.OK) {
			// update the project model
			String projectName = ((IResource) dialog.getResult()[0]).getName();
			EditorProject editorProject = Xdsml_baseFactoryImpl.eINSTANCE
					.createXTextEditorProject();
			editorProject.setProjectName(projectName);
			// TODO detection of the current extension
			addOrUpdateProjectToConf(editorProject);
		}
	}

	protected void selectExistingSiriusProject() {
		// launch the appropriate wizard
		// TODO filter only projects related to the current domain model
		SelectAnyIProjectDialog dialog = new SelectODesignIProjectDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		int res = dialog.open();
		if (res == WizardDialog.OK) {
			// update the project model
			String projectName = ((IResource) dialog.getResult()[0]).getName();
			EditorProject editorProject = Xdsml_baseFactoryImpl.eINSTANCE
					.createSiriusEditorProject();
			editorProject.setProjectName(projectName);
			// TODO detection of the current extension
			addOrUpdateProjectToConf(editorProject);

		}
	}

	/**
	 * if an editor of the same concrete kind exist, then the new one will
	 * replace it Ie. only one Sirius editor, one XtextEditor, etc
	 * 
	 * @param editorProject
	 */
	protected void addOrUpdateProjectToConf(EditorProject editorProject) {
		if (gemocLanguageIProject != null) {
			addOrUpdateProjectToConf(editorProject, gemocLanguageIProject);
		}
		if (gemocLanguageModel != null) {
			addOrUpdateProjectToConf(editorProject, gemocLanguageModel);
		}
	}

	protected void addOrUpdateProjectToConf(EditorProject editorProject,
			IProject gemocProject) {
		IFile configFile = gemocProject.getFile(new Path(
				Activator.GEMOC_PROJECT_CONFIGURATION_FILE));
		if (configFile.exists()) {
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put(Activator.GEMOC_PROJECT_CONFIGURATION_FILE_EXTENSION,
					new XMIResourceFactoryImpl());

			// Obtain a new resource set
			ResourceSet resSet = new ResourceSetImpl();

			// get the resource
			Resource resource = resSet
					.getResource(URI.createURI(configFile.getLocationURI()
							.toString()), true);

			LanguageDefinition gemocLanguageWorkbenchConfiguration = (LanguageDefinition) resource
					.getContents().get(0);
			addOrUpdateProjectToConf(editorProject,
					gemocLanguageWorkbenchConfiguration);

			try {
				resource.save(null);
			} catch (IOException e) {
				Activator.error(e.getMessage(), e);
			}
		}
		try {
			configFile.refreshLocal(IResource.DEPTH_ZERO,
					new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.error(e.getMessage(), e);
		}
	}

	/**
	 * if an editor of the same concrete kind exist, then the new one will
	 * replace it Ie. only one Sirius editor, one XtextEditor, etc
	 * 
	 * @param editorProject
	 */
	protected void addOrUpdateProjectToConf(EditorProject editorProject,
			LanguageDefinition language) {

		// add missing data to conf
		EditorProject existingEditor = null;
		String searchedClass = editorProject.getClass().getName();
		// search first existing editor
		for (EditorProject possibleExistingEditor : language
				.getEditorProjects()) {
			if (possibleExistingEditor.getClass().getName()
					.equals(searchedClass)) {
				existingEditor = possibleExistingEditor;
				break;
			}
		}
		if (existingEditor == null) {
			// simply add the new editor
			language.getEditorProjects().add(editorProject);
		} else {
			// replace the existing editor
			int index = language.getEditorProjects().indexOf(existingEditor);
			language.getEditorProjects().set(index, editorProject);
		}
	}
	
	public String getSiriusPath(){
		if(createdProject != null){
			FileFinderVisitor odesignProjectVisitor = new FileFinderVisitor(
					"odesign");
			try {
				createdProject.accept(odesignProjectVisitor);
				IFile odesignIFile = odesignProjectVisitor.getFile();
				if (odesignIFile != null) {
					return "/"+createdProject.getName()+"/"+odesignIFile.getProjectRelativePath().toString();
				}
			} catch (CoreException e) {
				Activator.error(e.getMessage(), e);
			}
		}
		return "";
	}

	public String getXtextPath(){
		if(createdProject != null){
			FileFinderVisitor odesignProjectVisitor = new FileFinderVisitor(
					"xtext");
			try {
				createdProject.accept(odesignProjectVisitor);
				IFile odesignIFile = odesignProjectVisitor.getFile();
				if (odesignIFile != null) {
					return "/"+createdProject.getName()+"/"+odesignIFile.getProjectRelativePath().toString();
				}
			} catch (CoreException e) {
				Activator.error(e.getMessage(), e);
			}
		}
		return "";
	}
}
