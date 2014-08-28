package org.gemoc.gemoc_language_workbench.ui.dialogs;

import org.gemoc.gemoc_language_workbench.utils.resourcevisitors.FileFinderVisitor;

public class SelectAIRDIFileDialog extends SelectSpecificFileDialog {

	@Override
	protected FileFinderVisitor instanciateFinder() {
		return new FileFinderVisitor("aird");
	}
	
}
