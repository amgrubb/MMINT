/*
 * Copyright (C) 2012 Marsha Chechik, Alessio Di Sandro, Rick Salay
 * 
 * This file is part of MMTF ver. 0.9.0.
 * 
 * MMTF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MMTF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MMTF.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.toronto.cs.se.mmtf.mid.mapping.diagram.part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * The outline page drag listener.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class MappingDiagramOutlineDragListener extends DragSourceAdapter {

	/** The outline tree viewer. */
	private Viewer viewer;

	/**
	 * Constructor: initialises the viewer.
	 * 
	 * @param viewer
	 *            The outline tree viewer.
	 */
	public MappingDiagramOutlineDragListener(Viewer viewer) {

		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}<br />
	 * Starts the drag by copying the selected objects into the transfer data.
	 * 
	 * @param event
	 *            The drag event.
	 */
	@Override
	public void dragStart(DragSourceEvent event) {

		List<Object> transferData = new ArrayList<Object>();
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

		// filter current selection to only EObjects
		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object sel = it.next();
			if (sel instanceof EObject) {
				transferData.add(sel);
			}
		}

		// create drag transfer selection
		selection = new StructuredSelection(transferData);
		if (selection.isEmpty()) {
			event.doit = false;
		}
		else {
			LocalSelectionTransfer.getTransfer().setSelection(selection);
			event.doit = true;
		}
	}

	/**
	 * {@inheritDoc}<br />
	 * Moves the transfer data into the event.
	 * 
	 * @param event
	 *            The drag event.
	 */
	@Override
	public void dragSetData(DragSourceEvent event) {

		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();

		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.dataType)) {
			event.data = selection;
		}
		else {
			event.doit = false;
		}
	}

	/**
	 * {@inheritDoc}<br />
	 * Clears the transfer data.
	 * 
	 * @param event
	 *            The drag event.
	 */
	@Override
	public void dragFinished(DragSourceEvent event) {

		LocalSelectionTransfer.getTransfer().setSelection(null);
	}

}
