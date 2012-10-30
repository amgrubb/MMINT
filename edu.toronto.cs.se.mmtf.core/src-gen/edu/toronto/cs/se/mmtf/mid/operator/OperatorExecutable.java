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
package edu.toronto.cs.se.mmtf.mid.operator;

import edu.toronto.cs.se.mmtf.mid.Model;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Executable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The executable implementation interface of an operator.
 * <!-- end-model-doc -->
 *
 *
 * @see edu.toronto.cs.se.mmtf.mid.operator.OperatorPackage#getOperatorExecutable()
 * @model abstract="true"
 * @generated
 */
public interface OperatorExecutable extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Executes the operator.
	 * @param actualParameters The list of model parameters.
	 * <!-- end-model-doc -->
	 * @model required="true" exceptions="edu.toronto.cs.se.mmtf.mid.operator.Exception" actualParametersRequired="true" actualParametersMany="true"
	 * @generated
	 */
	EList<Model> execute(EList<Model> actualParameters) throws Exception;

} // OperatorExecutable
