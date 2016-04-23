/**
 * Copyright (c) 2012-2016 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmint.mid.relationship.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import edu.toronto.cs.se.mmint.MMINT;
import edu.toronto.cs.se.mmint.MMINTException;
import edu.toronto.cs.se.mmint.MIDTypeHierarchy;
import edu.toronto.cs.se.mmint.mid.MID;
import edu.toronto.cs.se.mmint.mid.constraint.MIDConstraintChecker;
import edu.toronto.cs.se.mmint.mid.library.MIDRegistry;
import edu.toronto.cs.se.mmint.mid.relationship.Mapping;
import edu.toronto.cs.se.mmint.mid.relationship.MappingReference;
import edu.toronto.cs.se.mmint.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmint.mid.ui.MIDDialogUtils;
import edu.toronto.cs.se.mmint.mid.ui.MIDDialogCancellation;

/**
 * The command to create a link.
 * 
 * @author Alessio Di Sandro
 * 
 */
public class MappingReferenceNewNaryMappingCommand extends MappingReferenceCreateCommand {

	/**
	 * Constructor: initialises the superclass.
	 * 
	 * @param req
	 *            The request.
	 */
	public MappingReferenceNewNaryMappingCommand(CreateElementRequest req) {

		super(req);
	}

    /**
     * Overrides superclass to re-initialize diagram type hierarchy.
     */
	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		IStatus status = super.doUndo(monitor, info);
		MID mid = (MID) getElementToEdit().eContainer();
		if (!MIDConstraintChecker.isInstancesLevel(mid)) {
			MMINT.createTypeHierarchy(mid);
		}

		return status;
    }

    /**
     * Overrides superclass to re-initialize diagram type hierarchy.
     */
	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		IStatus status = super.doRedo(monitor, info);
		MID mid = (MID) getElementToEdit().eContainer();
		if (!MIDConstraintChecker.isInstancesLevel(mid)) {
			MMINT.createTypeHierarchy(mid);
		}

		return status;
    }

	/**
	 * Checks if a link can be created.
	 * 
	 * @return True if a link can be created, false otherwise.
	 */
	@Override
	public boolean canExecute() {

		return
			super.canExecute() && (
				MIDConstraintChecker.isInstancesLevel((ModelRel) getElementToEdit()) ||
				!MIDTypeHierarchy.isRootType((ModelRel) getElementToEdit())
			);
	}

	protected MappingReference doExecuteInstancesLevel() throws MMINTException, MIDDialogCancellation {

		ModelRel modelRel = (ModelRel) getElementToEdit();
		MappingReference mappingTypeRef = MIDDialogUtils.selectMappingTypeReferenceToCreate(modelRel, null, null);
		MappingReference newMappingRef = mappingTypeRef.getObject().createInstanceAndReference(false, modelRel);

		return newMappingRef;
	}

	protected MappingReference doExecuteTypesLevel() throws MMINTException, MIDDialogCancellation {

		ModelRel modelRelType = (ModelRel) getElementToEdit();
		MappingReference mappingTypeRef = MIDDialogUtils.selectMappingTypeReferenceToExtend(modelRelType, null, null);
		Mapping mappingType = mappingTypeRef.getObject();
		if (MIDTypeHierarchy.getRootTypeUri(mappingType).equals(mappingType.getUri())) {
			mappingTypeRef = null; // the link reference to the root is never shown
		}
		String newMappingTypeName = MIDDialogUtils.getStringInput("Create new light mapping type", "Insert new mapping type name", null);
		MappingReference newMappingTypeRef = mappingType.createSubtypeAndReference(mappingTypeRef, newMappingTypeName, false, modelRelType);
		MMINT.createTypeHierarchy(MIDRegistry.getMultiModel(modelRelType));

		return newMappingTypeRef;
	}

	/**
	 * Creates a new link.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            Additional parameter, not used.
	 * @return The ok result.
	 * @throws ExecutionException
	 *             If configuration command execution goes wrong.
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		try {
			MappingReference newElement = (MIDConstraintChecker.isInstancesLevel((ModelRel) getElementToEdit())) ?
				doExecuteInstancesLevel() :
				doExecuteTypesLevel();
			doConfigure(newElement, monitor, info);
			((CreateElementRequest) getRequest()).setNewElement(newElement);
	
			return CommandResult.newOKCommandResult(newElement);
		}
		catch (ExecutionException ee) {
			throw ee;
		}
		catch (MIDDialogCancellation e) {
			return CommandResult.newCancelledCommandResult();
		}
		catch (MMINTException e) {
			MMINTException.print(IStatus.ERROR, "No link created", e);
			return CommandResult.newErrorCommandResult("No link created");
		}
	}

}
