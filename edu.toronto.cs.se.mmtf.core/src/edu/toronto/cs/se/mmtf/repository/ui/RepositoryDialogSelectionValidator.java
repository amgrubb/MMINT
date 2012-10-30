/**
 * Copyright (c) 2012 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay, Vivien Suen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmtf.repository.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import edu.toronto.cs.se.mmtf.mid.Model;

/**
 * The selection validator for the repository tree dialog.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class RepositoryDialogSelectionValidator implements ISelectionStatusValidator {

	/**
	 * {@inheritDoc}
	 * The selection of a model type is not allowed, only its editors.
	 */
	@Override
	public IStatus validate(Object[] selection) {

		for (Object object : selection) {
			if (object instanceof Model) {
				return new Status(IStatus.ERROR, "edu.toronto.cs.se.mmtf", "Please select an editor");
			}
		}

		return new Status(IStatus.OK, "edu.toronto.cs.se.mmtf", "");
	}

}
