/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.obeo.dsl.debug;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Target</b></em>'. <!-- end-user-doc
 * --> <!-- begin-model-doc --> a {@link DebugTarget} is a debuggable execution context, for instance a
 * debugggable process of a virtual machine. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link fr.obeo.dsl.debug.DebugTarget#getName <em>Name</em>}</li>
 * <li>{@link fr.obeo.dsl.debug.DebugTarget#getState <em>State</em>}</li>
 * <li>{@link fr.obeo.dsl.debug.DebugTarget#getThreads <em>Threads</em>}</li>
 * <li>{@link fr.obeo.dsl.debug.DebugTarget#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 * 
 * @see fr.obeo.dsl.debug.DebugPackage#getDebugTarget()
 * @model
 * @generated
 */
public interface DebugTarget extends Contextual {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> A name of this {@link DebugTarget}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see fr.obeo.dsl.debug.DebugPackage#getDebugTarget_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link fr.obeo.dsl.debug.DebugTarget#getName <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute. The literals are from the enumeration
	 * {@link fr.obeo.dsl.debug.DebugTargetState}. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The current {@link TargetState} of this {@link DebugerTarget}. <!-- end-model-doc
	 * -->
	 * 
	 * @return the value of the '<em>State</em>' attribute.
	 * @see fr.obeo.dsl.debug.DebugTargetState
	 * @see #setState(DebugTargetState)
	 * @see fr.obeo.dsl.debug.DebugPackage#getDebugTarget_State()
	 * @model
	 * @generated
	 */
	DebugTargetState getState();

	/**
	 * Sets the value of the '{@link fr.obeo.dsl.debug.DebugTarget#getState <em>State</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>State</em>' attribute.
	 * @see fr.obeo.dsl.debug.DebugTargetState
	 * @see #getState()
	 * @generated
	 */
	void setState(DebugTargetState value);

	/**
	 * Returns the value of the '<em><b>Threads</b></em>' containment reference list. The list contents are of
	 * type {@link fr.obeo.dsl.debug.Thread}. It is bidirectional and its opposite is '
	 * {@link fr.obeo.dsl.debug.Thread#getParent <em>Parent</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> Current {@link Thread threads} running in this {@link DebugerTarget}. <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>Threads</em>' containment reference list.
	 * @see fr.obeo.dsl.debug.DebugPackage#getDebugTarget_Threads()
	 * @see fr.obeo.dsl.debug.Thread#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<fr.obeo.dsl.debug.Thread> getThreads();

} // DebugTarget
