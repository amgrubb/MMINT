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
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;

import edu.toronto.cs.se.mmtf.mid.MultiModel;
import edu.toronto.cs.se.mmtf.mid.trait.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmtf.mid.trait.MultiModelInstanceFactory;
import edu.toronto.cs.se.mmtf.mid.trait.MultiModelTypeFactory;

/**
 * The command to change a model of a mapping reference.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class ModelRelChangeModelCommand extends ModelRelModelsReorientCommand {

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param request
	 *            The request.
	 */
	public ModelRelChangeModelCommand(ReorientReferenceRelationshipRequest request) {

		super(request);
	}

	/**
	 * Checks if a model can be changed.
	 * 
	 * @return True if a model can be changed, false otherwise.
	 */
	@Override
	public boolean canExecute() {

		return
			super.canExecute() && (
				MultiModelConstraintChecker.isInstancesLevel(getOldSource()) ||
				MultiModelConstraintChecker.isAllowedModelType(getOldSource())
			);
	}

	/**
	 * Checks if the source model relationship can be changed.
	 * 
	 * @return True if the source model relationship can be changed, false
	 *         otherwise.
	 */
	@Override
	protected boolean canReorientSource() {

		return
			super.canReorientSource() && (
				!MultiModelConstraintChecker.isInstancesLevel(getOldSource()) ||
				MultiModelConstraintChecker.isAllowedModel(getNewSource(), getOldTarget())
			);
	}

	/**
	 * Checks if the target model can be changed.
	 * 
	 * @return True if the target model can be changed, false otherwise.
	 */
	@Override
	protected boolean canReorientTarget() {

		return
			super.canReorientTarget() && (
				!MultiModelConstraintChecker.isInstancesLevel(getOldSource()) ||
				MultiModelConstraintChecker.isAllowedModel(getOldSource(), getNewTarget())
			);
	}

	/**
	 * Changes the source model of a model relationship.
	 * 
	 * @return The ok result.
	 * @throws ExecutionException
	 *             Never thrown.
	 */
	@Override
	protected CommandResult reorientSource() throws ExecutionException {

		CommandResult result;
		if (MultiModelConstraintChecker.isInstancesLevel(getOldSource())) {
			MultiModelInstanceFactory.removeModelReference(getOldSource(), getOldTarget());
			MultiModelInstanceFactory.createModelReference(getNewSource(), getOldTarget());
			result = super.reorientSource();
		}
		else {
			MultiModel multiModel = (MultiModel) getOldSource().eContainer();
			MultiModelTypeFactory.removeLightModelTypeRef(getOldSource(), getOldTarget());
			MultiModelTypeFactory.createLightModelTypeRef(getNewSource(), getOldTarget());
			result = super.reorientSource();
			MultiModelTypeFactory.syncRepository(multiModel);
		}

		return result;
	}

	/**
	 * Changes the target model of a model relationship.
	 * 
	 * @return The ok result.
	 * @throws ExecutionException
	 *             Never thrown.
	 */
	@Override
	protected CommandResult reorientTarget() throws ExecutionException {

		CommandResult result;
		if (MultiModelConstraintChecker.isInstancesLevel(getOldSource())) {
			MultiModelInstanceFactory.removeModelReference(getOldSource(), getOldTarget());
			MultiModelInstanceFactory.createModelReference(getOldSource(), getNewTarget());
			result = super.reorientTarget();
		}
		else {
			MultiModel multiModel = (MultiModel) getOldSource().eContainer();
			MultiModelTypeFactory.removeLightModelTypeRef(getOldSource(), getOldTarget());
			MultiModelTypeFactory.createLightModelTypeRef(getOldSource(), getNewTarget());
			result = super.reorientTarget();
			MultiModelTypeFactory.syncRepository(multiModel);
		}

		return result;
	}

}
