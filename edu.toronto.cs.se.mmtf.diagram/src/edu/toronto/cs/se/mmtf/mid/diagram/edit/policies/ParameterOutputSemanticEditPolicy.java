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
package edu.toronto.cs.se.mmtf.mid.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

/**
 * The semantic edit policy for output parameters.
 * 
 * @author Vivien Suen
 * 
 */
public class ParameterOutputSemanticEditPolicy extends Parameter2ItemSemanticEditPolicy {

	/** Gets the command to destroy an output parameter.
	 * 
	 * @param req
	 *            The request.
	 * @return The executable command.
	 */
	@Override
	protected Command getDestroyElementCommand(DestroyElementRequest req) {

		return null;
	}

}