/**
 * Copyright (c) 2012-2014 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.modelepedia.kleisli.contextmenu;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

import edu.toronto.cs.se.mmint.mid.EMFInfo;
import edu.toronto.cs.se.mmint.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmint.mid.diagram.library.AddModifyConstraintListener;
import edu.toronto.cs.se.mmint.mid.relationship.ModelElementReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelEndpointReference;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementReference2EditPart;
import edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.parts.ModelElementReferenceEditPart;
import edu.toronto.cs.se.modelepedia.kleisli.KleisliModelEndpoint;

public class KleisliRelationshipDiagramActions extends ContributionItem {

	private static final int INVALID_MENU_ITEM_LIMIT = 20;

	@Override
	public boolean isDynamic() {

		return true;
	}

	@Override
	public void fill(Menu menu, int index) {

		// this is a hack to prevent this menu from being created twice
		if (menu.getItemCount() > INVALID_MENU_ITEM_LIMIT) {
			return;
		}

		// check selection
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();
		if (selection == null || selection.isEmpty() || !(selection instanceof StructuredSelection)) {
			return;
		}
		Object[] objects = ((StructuredSelection) selection).toArray();
		boolean doDerivation = true;
		if (objects.length > 1) {
			doDerivation = false;
		}

		// get selection
		ModelElementReference modelElemRef = null;
		for (Object object : objects) {
			if (
				object instanceof ModelElementReferenceEditPart ||
				object instanceof ModelElementReference2EditPart
			) {
				GraphicalEditPart editPart = (GraphicalEditPart) object;
				modelElemRef = (ModelElementReference) ((View) editPart.getModel()).getElement();
				//TODO MMINT[KLEISLI] replace with KleisliModelElement
				EMFInfo eInfo = modelElemRef.getObject().getEInfo();
				if (
					!(((ModelEndpointReference) modelElemRef.eContainer()).getObject() instanceof KleisliModelEndpoint) ||
					MultiModelConstraintChecker.isInstancesLevel(modelElemRef) || ( // only types
						!eInfo.getClassName().startsWith("_") && ( // only derived classes
							eInfo.getFeatureName() == null ||
							!eInfo.getFeatureName().startsWith("_")
						)
					) || (
						eInfo.getClassName().startsWith("_") && // only derived features
						eInfo.getFeatureName() != null &&
						!eInfo.getFeatureName().startsWith("_")
					)
				) {
					doDerivation = false;
				}
			}
			if (!doDerivation) {
				return;
			}
		}
		if (modelElemRef == null) { // no relevant edit parts selected
			return;
		}

		// create dynamic menus
		MenuItem mmintItem = new MenuItem(menu, SWT.CASCADE, index);
		mmintItem.setText("MMINT");
		Menu mmintMenu = new Menu(menu);
		mmintItem.setMenu(mmintMenu);
		// derivation
		if (doDerivation) {
			MenuItem derivationItem = new MenuItem(mmintMenu, SWT.NONE);
			derivationItem.setText("Add/Modify Kleisli Query");
			derivationItem.addSelectionListener(
				new AddModifyConstraintListener(modelElemRef)
			);
		}
	}

}
