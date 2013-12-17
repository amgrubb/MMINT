/**
 * Copyright (c) 2013 Marsha Chechik, Alessio Di Sandro, Michalis Famelis,
 * Rick Salay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Alessio Di Sandro - Implementation.
 */
package edu.toronto.cs.se.mmtf.mid.impl;

import edu.toronto.cs.se.mmtf.MMTF;
import edu.toronto.cs.se.mmtf.MMTFException;
import edu.toronto.cs.se.mmtf.MultiModelLightTypeFactory;
import edu.toronto.cs.se.mmtf.MultiModelTypeFactory;
import edu.toronto.cs.se.mmtf.MultiModelTypeHierarchy;
import edu.toronto.cs.se.mmtf.mid.ExtendibleElement;
import edu.toronto.cs.se.mmtf.mid.ExtendibleElementEndpoint;
import edu.toronto.cs.se.mmtf.mid.MidFactory;
import edu.toronto.cs.se.mmtf.mid.MidPackage;
import edu.toronto.cs.se.mmtf.mid.Model;
import edu.toronto.cs.se.mmtf.mid.ModelEndpoint;
import edu.toronto.cs.se.mmtf.mid.MultiModel;
import edu.toronto.cs.se.mmtf.mid.constraint.MultiModelConstraintChecker;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelInstanceFactory;
import edu.toronto.cs.se.mmtf.mid.library.MultiModelRegistry;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelEndpointReference;
import edu.toronto.cs.se.mmtf.mid.relationship.ModelRel;
import edu.toronto.cs.se.mmtf.mid.relationship.RelationshipFactory;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Endpoint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ModelEndpointImpl extends ExtendibleElementEndpointImpl implements ModelEndpoint {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelEndpointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MidPackage.Literals.MODEL_ENDPOINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelEndpoint getSupertype() {
		ExtendibleElementEndpoint supertype = super.getSupertype();
		return (supertype == null) ? null : (ModelEndpoint) supertype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model getTarget() {
		ExtendibleElement target = super.getTarget();
		return (target == null) ? null : (Model) target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelEndpoint getMetatype() {
		ExtendibleElementEndpoint metatype = super.getMetatype();
		return (metatype == null) ? null : (ModelEndpoint) metatype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == ExtendibleElementEndpoint.class) {
			switch (baseOperationID) {
				case MidPackage.EXTENDIBLE_ELEMENT_ENDPOINT___GET_METATYPE: return MidPackage.MODEL_ENDPOINT___GET_METATYPE;
				case MidPackage.EXTENDIBLE_ELEMENT_ENDPOINT___GET_SUPERTYPE: return MidPackage.MODEL_ENDPOINT___GET_SUPERTYPE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case MidPackage.MODEL_ENDPOINT___GET_SUPERTYPE:
				return getSupertype();
			case MidPackage.MODEL_ENDPOINT___GET_TARGET:
				return getTarget();
			case MidPackage.MODEL_ENDPOINT___GET_METATYPE:
				return getMetatype();
			case MidPackage.MODEL_ENDPOINT___CREATE_SUBTYPE_AND_REFERENCE__MODELENDPOINTREFERENCE_STRING_MODEL_BOOLEAN_MODELREL:
				try {
					return createSubtypeAndReference((ModelEndpointReference)arguments.get(0), (String)arguments.get(1), (Model)arguments.get(2), (Boolean)arguments.get(3), (ModelRel)arguments.get(4));
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___REPLACE_SUBTYPE_AND_REFERENCE__MODELENDPOINT_MODELENDPOINTREFERENCE_STRING_MODEL_MODELREL:
				try {
					replaceSubtypeAndReference((ModelEndpoint)arguments.get(0), (ModelEndpointReference)arguments.get(1), (String)arguments.get(2), (Model)arguments.get(3), (ModelRel)arguments.get(4));
					return null;
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___DELETE_TYPE_AND_REFERENCE__BOOLEAN:
				try {
					deleteTypeAndReference((Boolean)arguments.get(0));
					return null;
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___CREATE_INSTANCE_AND_REFERENCE__MODEL_BOOLEAN_MODELREL:
				try {
					return createInstanceAndReference((Model)arguments.get(0), (Boolean)arguments.get(1), (ModelRel)arguments.get(2));
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___DELETE_INSTANCE_AND_REFERENCE__BOOLEAN:
				try {
					deleteInstanceAndReference((Boolean)arguments.get(0));
					return null;
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___REPLACE_INSTANCE_AND_REFERENCE__MODELENDPOINT_MODEL:
				try {
					replaceInstanceAndReference((ModelEndpoint)arguments.get(0), (Model)arguments.get(1));
					return null;
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___CREATE_TYPE_REFERENCE__MODELENDPOINTREFERENCE_BOOLEAN_BOOLEAN_MODELREL:
				try {
					return createTypeReference((ModelEndpointReference)arguments.get(0), (Boolean)arguments.get(1), (Boolean)arguments.get(2), (ModelRel)arguments.get(3));
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
			case MidPackage.MODEL_ENDPOINT___CREATE_INSTANCE_REFERENCE__BOOLEAN_MODELREL:
				try {
					return createInstanceReference((Boolean)arguments.get(0), (ModelRel)arguments.get(1));
				}
				catch (Throwable throwable) {
					throw new InvocationTargetException(throwable);
				}
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * Deletes this model type endpoint from the Type MID.
	 * 
	 * @param isFullDelete
	 *            True if the model type endpoint is going to be fully deleted,
	 *            false if it is going to be replaced later.
	 * @generated NOT
	 */
	protected void deleteType(boolean isFullDelete) {

		MultiModel multiModel = MultiModelRegistry.getMultiModel(this);
		super.deleteType(multiModel);
		if (isFullDelete) {
			ModelRel modelRelType = (ModelRel) eContainer();
			modelRelType.getModelEndpoints().remove(this);
		}
	}

	/**
	 * Adds a reference to this model type endpoint to the Type MID.
	 * 
	 * @param newModelTypeEndpointRef
	 *            The new reference to this model type endpoint to be added.
	 * @param modelTypeEndpointRef
	 *            The reference to the supertype of the model type endpoint,
	 *            null if such reference doesn't exist in the model relationship
	 *            type container.
	 * @param isModifiable
	 *            True if the new reference will allow modifications of the
	 *            referenced model type endpoint, false otherwise.
	 * @param isBinarySrc
	 *            True if the referenced model type endpoint is the source in
	 *            the binary model relationship container, false otherwise.
	 * @param containerModelRelType
	 *            The model relationship type that will contain the new
	 *            reference to the model type endpoint.
	 * @generated NOT
	 */
	protected void addTypeReference(ModelEndpointReference newModelTypeEndpointRef, ModelEndpointReference modelTypeEndpointRef, boolean isModifiable, boolean isBinarySrc, ModelRel containerModelRelType) {

		MultiModelTypeFactory.addTypeReference(newModelTypeEndpointRef, this, modelTypeEndpointRef, isModifiable, false);
		if (isBinarySrc) {
			containerModelRelType.getModelEndpointRefs().add(0, newModelTypeEndpointRef);
		}
		else {
			containerModelRelType.getModelEndpointRefs().add(newModelTypeEndpointRef);
		}
	}

	/**
	 * @generated NOT
	 */
	public ModelEndpointReference createTypeReference(ModelEndpointReference modelTypeEndpointRef, boolean isModifiable, boolean isBinarySrc, ModelRel containerModelRelType) throws MMTFException {

		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute TYPES level operation on INSTANCES level element");
		}

		ModelEndpointReference newModelTypeEndpointRef = RelationshipFactory.eINSTANCE.createModelEndpointReference();
		addTypeReference(newModelTypeEndpointRef, modelTypeEndpointRef, isModifiable, isBinarySrc, containerModelRelType);

		return newModelTypeEndpointRef;
	}

	/**
	 * Adds a subtype of this model type endpoint and a reference to it to the
	 * Type MID.
	 * 
	 * @param newModelTypeEndpoint
	 *            The new model type endpoint to be added.
	 * @param modelTypeEndpointRef
	 *            The reference to the supertype of the new model type endpoint,
	 *            null if such reference doesn't exist in the model relationship
	 *            type container.
	 * @param newModelTypeEndpointName
	 *            The name of the new model type endpoint.
	 * @param targetModelType
	 *            The model type that is the target of the new model type
	 *            endpoint.
	 * @param isBinarySrc
	 *            True if the model type endpoint is the source in the binary
	 *            model relationship type container, false otherwise.
	 * @param containerModelRelType
	 *            The model relationship type that will contain the new model
	 *            type endpoint.
	 * @return The created reference to the new model type endpoint.
	 * @throws MMTFException
	 *             If the new model endpoint to be added is a model instance
	 *             endpoint, or if the uri of the new model type endpoint is
	 *             already registered in the Type MID.
	 * @generated NOT
	 */
	protected ModelEndpointReference addSubtypeAndReference(ModelEndpoint newModelTypeEndpoint, ModelEndpointReference modelTypeEndpointRef, String newModelTypeEndpointName, Model targetModelType, boolean isBinarySrc, ModelRel containerModelRelType) throws MMTFException {

		MultiModel multiModel = MultiModelRegistry.getMultiModel(containerModelRelType);
		// create the "thing" and the corresponding reference
		super.addSubtype(newModelTypeEndpoint, containerModelRelType, containerModelRelType.getName() + MMTF.ENDPOINT_SEPARATOR + targetModelType.getName(), newModelTypeEndpointName);
		MultiModelTypeFactory.addModelTypeEndpoint(newModelTypeEndpoint, targetModelType, isBinarySrc, containerModelRelType);
		ModelEndpointReference newModelTypeEndpointRef = newModelTypeEndpoint.createTypeReference(modelTypeEndpointRef, true, isBinarySrc, containerModelRelType);
		// create references of the "thing" in subtypes of the container
		for (ModelRel modelRelSubtype : MultiModelTypeHierarchy.getSubtypes(containerModelRelType, multiModel)) {
			ModelEndpointReference modelSubtypeEndpointRef = (modelTypeEndpointRef == null) ?
				null :
				MultiModelTypeHierarchy.getReference(modelTypeEndpointRef, modelRelSubtype.getModelEndpointRefs());
			newModelTypeEndpoint.createTypeReference(modelSubtypeEndpointRef, false, isBinarySrc, modelRelSubtype);
		}

		return newModelTypeEndpointRef;
	}

	/**
	 * @generated NOT
	 */
	public ModelEndpointReference createSubtypeAndReference(ModelEndpointReference modelTypeEndpointRef, String newModelTypeEndpointName, Model targetModelType, boolean isBinarySrc, ModelRel containerModelRelType) throws MMTFException {

		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute TYPES level operation on INSTANCES level element");
		}

		ModelEndpoint newModelTypeEndpoint = MidFactory.eINSTANCE.createModelEndpoint();
		ModelEndpointReference newModelTypeEndpointRef = addSubtypeAndReference(newModelTypeEndpoint, modelTypeEndpointRef, newModelTypeEndpointName, targetModelType, isBinarySrc, containerModelRelType);

		return newModelTypeEndpointRef;
	}

	/**
	 * @generated NOT
	 */
	public void replaceSubtypeAndReference(ModelEndpoint oldModelTypeEndpoint, ModelEndpointReference modelTypeEndpointRef, String newModelTypeEndpointName, Model targetModelType, ModelRel containerModelRelType) throws MMTFException {

		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute TYPES level operation on INSTANCES level element");
		}

		oldModelTypeEndpoint.deleteTypeAndReference(false);
		MultiModel multiModel = MultiModelRegistry.getMultiModel(containerModelRelType);
		// modify the "thing" and the corresponding reference
		super.addSubtype(oldModelTypeEndpoint, containerModelRelType, containerModelRelType.getName() + MMTF.ENDPOINT_SEPARATOR + targetModelType.getName(), newModelTypeEndpointName);
		oldModelTypeEndpoint.setTarget(targetModelType);
		if (modelTypeEndpointRef != null) {
			ModelEndpointReference oldModelTypeEndpointRef = MultiModelTypeHierarchy.getReference(oldModelTypeEndpoint.getUri(), containerModelRelType.getModelEndpointRefs());
			oldModelTypeEndpointRef.setSupertypeRef(modelTypeEndpointRef);
		}
		// modify references of the "thing" in subtypes of the container
		for (ModelRel modelRelSubtype : MultiModelTypeHierarchy.getSubtypes(containerModelRelType, multiModel)) {
			ModelEndpointReference modelSubtypeEndpointRef = (modelTypeEndpointRef == null) ?
				null :
				MultiModelTypeHierarchy.getReference(modelTypeEndpointRef, modelRelSubtype.getModelEndpointRefs());
			ModelEndpointReference oldModelTypeEndpointRef = MultiModelTypeHierarchy.getReference(oldModelTypeEndpoint.getUri(), modelRelSubtype.getModelEndpointRefs());
			oldModelTypeEndpointRef.setSupertypeRef(modelSubtypeEndpointRef);
		}
	}

	/**
	 * @generated NOT
	 */
	public void deleteTypeAndReference(boolean isFullDelete) throws MMTFException {

		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute TYPES level operation on INSTANCES level element");
		}

		MultiModel multiModel = MultiModelRegistry.getMultiModel(this);
		// delete the "thing" and the corresponding reference
		ModelRel modelRelType = (ModelRel) eContainer();
		deleteType(isFullDelete);
		ModelEndpointReference modelTypeEndpointRef = MultiModelTypeHierarchy.getReference(getUri(), modelRelType.getModelEndpointRefs());
		MultiModelTypeFactory.removeModelTypeEndpointReference(modelTypeEndpointRef, isFullDelete);
		// delete references of the "thing" in subtypes of the container
		for (ModelRel modelRelSubtype : MultiModelTypeHierarchy.getSubtypes(modelRelType, multiModel)) {
			ModelEndpointReference modelSubtypeEndpointRef = MultiModelTypeHierarchy.getReference(getUri(), modelRelSubtype.getModelEndpointRefs());
			MultiModelTypeFactory.removeModelTypeEndpointReference(modelSubtypeEndpointRef, isFullDelete);
		}
	}

	/**
	 * Adds a reference to this model instance endpoint to an Instance MID.
	 * 
	 * @param newModelEndpointRef
	 *            The new reference to this model endpoint to be added.
	 * @param isBinarySrc
	 *            True if the model endpoint is the source in the binary model
	 *            relationship container, false otherwise.
	 * @param containerModelRel
	 *            The model relationship that will contain the new reference to
	 *            the model endpoint.
	 * @generated NOT
	 */
	protected void addInstanceReference(ModelEndpointReference newModelEndpointRef, boolean isBinarySrc, ModelRel containerModelRel) {

		boolean isContainer = (containerModelRel.eContainer() == null);
		super.addInstanceReference(newModelEndpointRef, isContainer);
		if (isBinarySrc) {
			containerModelRel.getModelEndpointRefs().add(0, newModelEndpointRef);
		} 
		else {
			containerModelRel.getModelEndpointRefs().add(newModelEndpointRef);
		}
	}

	/**
	 * @generated NOT
	 */
	public ModelEndpointReference createInstanceReference(boolean isBinarySrc, ModelRel containerModelRel) throws MMTFException {

		if (!MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute INSTANCES level operation on TYPES level element");
		}

		ModelEndpointReference newModelEndpointRef = RelationshipFactory.eINSTANCE.createModelEndpointReference();
		addInstanceReference(newModelEndpointRef, isBinarySrc, containerModelRel);

		return newModelEndpointRef;
	}

	/**
	 * Adds a model instance endpoint and a reference to it to an Instance MID.
	 * 
	 * @param newModelEndpoint
	 *            The new model endpoint to be added.
	 * @param targetModel
	 *            The model that is the target of the new model endpoint.
	 * @param isBinarySrc
	 *            True if the model endpoint is the source in the binary model
	 *            relationship container, false otherwise.
	 * @param modelRel
	 *            The model relationship that will contain the new model
	 *            endpoint.
	 * @return The created reference to the new model endpoint.
	 * @throws MMTFException
	 *             If the new model endpoint is a model type endpoint.
	 * @generated NOT
	 */
	protected ModelEndpointReference addInstanceAndReference(ModelEndpoint newModelEndpoint, Model targetModel, boolean isBinarySrc, ModelRel containerModelRel) throws MMTFException {

		MultiModelInstanceFactory.addBasicInstance(newModelEndpoint, this, null, targetModel.getName());
		MultiModelInstanceFactory.addInstanceEndpoint(newModelEndpoint, targetModel);
		if (isBinarySrc) {
			containerModelRel.getModelEndpoints().add(0, newModelEndpoint);
		}
		else {
			containerModelRel.getModelEndpoints().add(newModelEndpoint);
		}
		ModelEndpointReference modelEndpointRef = newModelEndpoint.createInstanceReference(isBinarySrc, containerModelRel);

		return modelEndpointRef;
	}

	/**
	 * @generated NOT
	 */
	public ModelEndpointReference createInstanceAndReference(Model targetModel, boolean isBinarySrc, ModelRel containerModelRel) throws MMTFException {

		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute TYPES level operation on INSTANCES level element");
		}

		ModelEndpoint newModelEndpoint = MidFactory.eINSTANCE.createModelEndpoint();
		ModelEndpointReference newModelEndpointRef = addInstanceAndReference(newModelEndpoint, targetModel, isBinarySrc, containerModelRel);

		return newModelEndpointRef;
	}

	/**
	 * @generated NOT
	 */
	public void replaceInstanceAndReference(ModelEndpoint oldModelEndpoint, Model targetModel) throws MMTFException {

		if (MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute TYPES level operation on INSTANCES level element");
		}
		if (this.eClass() != oldModelEndpoint.eClass() && this.eClass().isSuperTypeOf(oldModelEndpoint.eClass())) {
			throw new MMTFException("Can't replace a user-defined model endpoint with a native one");
		}

		oldModelEndpoint.deleteInstanceAndReference(false);
		MultiModelInstanceFactory.addBasicInstance(oldModelEndpoint, this, null, null);
		oldModelEndpoint.setTarget(targetModel);
	}

	/**
	 * @generated NOT
	 */
	public void deleteInstanceAndReference(boolean isFullDelete) throws MMTFException {

		if (!MultiModelConstraintChecker.isInstancesLevel(this)) {
			throw new MMTFException("Can't execute INSTANCES level operation on TYPES level element");
		}

		ModelRel modelRel = (ModelRel) eContainer();
		ModelEndpointReference modelEndpointRef = null;
		for (ModelEndpointReference modelEndpointRef2 : modelRel.getModelEndpointRefs()) {
			if (modelEndpointRef2.getObject() == this) {
				modelEndpointRef = modelEndpointRef2;
				break;
			}
		}
		while (modelEndpointRef.getModelElemRefs().size() > 0) {
			MultiModelInstanceFactory.removeModelElementReference(modelEndpointRef.getModelElemRefs().get(0));
		}
		if (isFullDelete) {
			modelRel.getModelEndpoints().remove(this);
			modelRel.getModelEndpointRefs().remove(modelEndpointRef);
		}
	}

} //ModelEndpointImpl
