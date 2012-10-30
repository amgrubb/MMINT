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
package edu.toronto.cs.se.mmtf.mid.relationship.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;

public class ExtendibleElementReferenceSupertypeRefSemanticEditPolicy extends
		ExtendibleElementReferenceSupertypeRefItemSemanticEditPolicy {

	@Override
	protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {

		return null;
	}

}
