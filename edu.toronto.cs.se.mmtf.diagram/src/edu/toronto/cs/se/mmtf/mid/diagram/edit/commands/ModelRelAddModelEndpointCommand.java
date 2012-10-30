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
package edu.toronto.cs.se.mmtf.mid.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import edu.toronto.cs.se.mmtf.MMTFException;
import edu.toronto.cs.se.mmtf.MultiModelLightTypeFactory;
import edu.toronto.cs.se.mmtf.mid.Model;
import edu.toronto.cs.se.mmtf.mid.ModelEndpoint;
import edu.toronto.cs.se.mmtf.mid.diagram.trait.MidDiagramTrait;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelEndpointReference;
import edu.toronto.cs.se.mmtf.mid.trait.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmtf.mid.trait.MultiModelInstanceFactory;

/**
 * The command to add a model to a model relationship.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class ModelRelAddModelEndpointCommand extends ModelEndpointCreateCommand {

	private EList<String> modelTypeEndpointUris;

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 * @param source
	 *            The source model relationship.
	 * @param target
	 *            The target model.
	 */
	public ModelRelAddModelEndpointCommand(CreateRelationshipRequest request, EObject source, EObject target) {

		super(request, source, target);
		modelTypeEndpointUris = null;
	}

	/**
	 * Checks if a model can be added.
	 * 
	 * @return True if a model can be added, false otherwise.
	 */
	@Override
	public boolean canExecute() {

		boolean instance = MultiModelConstraintChecker.isInstancesLevel(getSource());

		return
			super.canExecute() && ((
				instance &&
				(modelTypeEndpointUris = MultiModelConstraintChecker.getAllowedModelEndpoints(getSource(), (Model) getTarget())) != null
			) || (
				!instance &&
				!MultiModelConstraintChecker.isRootType(getSource()) &&
				!MultiModelConstraintChecker.isRootType(getTarget())
			));
	}

	protected ModelEndpoint doExecuteInstancesLevel() throws Exception {

		ModelEndpointReference modelTypeEndpointRef = MidDiagramTrait.selectModelTypeEndpointToCreate(getSource(), modelTypeEndpointUris);
		ModelEndpointReference newModelEndpointRef = MultiModelInstanceFactory.createModelEndpointAndModelEndpointReference(
			modelTypeEndpointRef.getObject(),
			getSource(),
			(Model) getTarget(),
			false
		);

		return newModelEndpointRef.getObject();
	}

	protected ModelEndpoint doExecuteTypesLevel() throws MMTFException {

		String newModelTypeEndpointName = MidDiagramTrait.getStringInput("Create new light model type endpoint", "Insert new model type endpoint role");
		//TODO MMTF: search for override (only if we're not inheriting from a root type)
		ModelEndpointReference modelTypeEndpointRef = null;
		ModelEndpoint modelTypeEndpoint = (modelTypeEndpointRef == null) ? null : modelTypeEndpointRef.getObject();
		ModelEndpointReference newModelTypeEndpointRef = MultiModelLightTypeFactory.createLightModelTypeEndpointAndModelTypeEndpointReference(
			getSource(),
			modelTypeEndpoint,
			modelTypeEndpointRef,
			(Model) getTarget(),
			newModelTypeEndpointName,
			false
		);
		// no need to init type hierarchy, no need for undo/redo

		return newModelTypeEndpointRef.getObject();
	}

	/**
	 * Adds a model to a model relationship.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            Additional parameter, not used.
	 * @return The ok result.
	 * @throws ExecutionException
	 *             If this command can't be executed.
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		try {
			if (!canExecute()) {
				throw new ExecutionException("Invalid arguments in create link command");
			}

			ModelEndpoint newElement = (MultiModelConstraintChecker.isInstancesLevel(getSource())) ?
				doExecuteInstancesLevel() :
				doExecuteTypesLevel();
			doConfigure(newElement, monitor, info);
			((CreateElementRequest) getRequest()).setNewElement(newElement);

			return CommandResult.newOKCommandResult(newElement);
		}
		catch (ExecutionException ee) {
			throw ee;
		}
		catch (Exception e) {
			MMTFException.print(MMTFException.Type.WARNING, "No model endpoint created", e);
			return CommandResult.newErrorCommandResult("No model endpoint created");
		}
	}

}
