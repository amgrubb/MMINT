/*
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
package edu.toronto.cs.se.mmtf.mid.relationship.diagram.sheet;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.sheet.DefaultPropertySection;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import edu.toronto.cs.se.mmtf.mid.relationship.BinaryLinkReference;
import edu.toronto.cs.se.mmtf.mid.relationship.ExtendibleElementReference;
import edu.toronto.cs.se.mmtf.mid.relationship.diagram.edit.parts.BinaryLinkReferenceEditPart.BinaryLinkReferenceFigure;

/**
 * @generated
 */
public class MidPropertySection extends DefaultPropertySection implements
		IPropertySourceProvider {

	/**
	 * Modify/unwrap selection.
	 * @generated
	 */
	protected Object transformSelectionGen(Object selected) {
		selected = /*super.*/transformSelectionToDomain(selected);
		return selected;
	}

	/**
	 * @generated NOT
	 */
	protected Object transformSelection(Object selected) {

		if (selected instanceof EditPart) {
			Object model = ((EditPart) selected).getModel();
			if (model instanceof View) {
				Object element = ((View) model).getElement();
				if (element instanceof BinaryLinkReference
						&& selected instanceof GraphicalEditPart) {
					IFigure figure = ((GraphicalEditPart) selected).getFigure();
					if (figure == ((BinaryLinkReferenceFigure) figure
							.getParent())
							.getFigureBinaryLinkReferenceSourceModelElementEndpointReferenceLabelFigure()) {
						element = ((BinaryLinkReference) element)
								.getModelElemEndpointRefs().get(0);
					} else if (figure == ((BinaryLinkReferenceFigure) figure
							.getParent())
							.getFigureBinaryLinkReferenceTargetModelElementEndpointReferenceLabelFigure()) {
						element = ((BinaryLinkReference) element)
								.getModelElemEndpointRefs().get(1);
					}
				}
				return element instanceof ExtendibleElementReference ? ((ExtendibleElementReference) element)
						.getReferencedObject() : element;
			}
			return null;
		}

		return transformSelectionGen(selected);
	}

}
