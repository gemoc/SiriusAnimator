/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.gemoc.executionengine.java.sequential_xdsml.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.gemoc.executionengine.java.sequential_xdsml.DSAProject;
import org.gemoc.executionengine.java.sequential_xdsml.Sequential_xdsmlPackage;
import org.gemoc.executionframework.xdsml_base.impl.ProjectResourceImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DSA Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.gemoc.executionengine.java.sequential_xdsml.impl.DSAProjectImpl#getCodeExecutorClass <em>Code Executor Class</em>}</li>
 *   <li>{@link org.gemoc.executionengine.java.sequential_xdsml.impl.DSAProjectImpl#getEntryPoint <em>Entry Point</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DSAProjectImpl extends ProjectResourceImpl implements DSAProject {
	/**
	 * The default value of the '{@link #getCodeExecutorClass() <em>Code Executor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeExecutorClass()
	 * @generated
	 * @ordered
	 */
	protected static final String CODE_EXECUTOR_CLASS_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCodeExecutorClass() <em>Code Executor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeExecutorClass()
	 * @generated
	 * @ordered
	 */
	protected String codeExecutorClass = CODE_EXECUTOR_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntryPoint() <em>Entry Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryPoint()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRY_POINT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getEntryPoint() <em>Entry Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryPoint()
	 * @generated
	 * @ordered
	 */
	protected String entryPoint = ENTRY_POINT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DSAProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Sequential_xdsmlPackage.Literals.DSA_PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCodeExecutorClass() {
		return codeExecutorClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodeExecutorClass(String newCodeExecutorClass) {
		String oldCodeExecutorClass = codeExecutorClass;
		codeExecutorClass = newCodeExecutorClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Sequential_xdsmlPackage.DSA_PROJECT__CODE_EXECUTOR_CLASS, oldCodeExecutorClass, codeExecutorClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntryPoint() {
		return entryPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryPoint(String newEntryPoint) {
		String oldEntryPoint = entryPoint;
		entryPoint = newEntryPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Sequential_xdsmlPackage.DSA_PROJECT__ENTRY_POINT, oldEntryPoint, entryPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Sequential_xdsmlPackage.DSA_PROJECT__CODE_EXECUTOR_CLASS:
				return getCodeExecutorClass();
			case Sequential_xdsmlPackage.DSA_PROJECT__ENTRY_POINT:
				return getEntryPoint();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Sequential_xdsmlPackage.DSA_PROJECT__CODE_EXECUTOR_CLASS:
				setCodeExecutorClass((String)newValue);
				return;
			case Sequential_xdsmlPackage.DSA_PROJECT__ENTRY_POINT:
				setEntryPoint((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Sequential_xdsmlPackage.DSA_PROJECT__CODE_EXECUTOR_CLASS:
				setCodeExecutorClass(CODE_EXECUTOR_CLASS_EDEFAULT);
				return;
			case Sequential_xdsmlPackage.DSA_PROJECT__ENTRY_POINT:
				setEntryPoint(ENTRY_POINT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Sequential_xdsmlPackage.DSA_PROJECT__CODE_EXECUTOR_CLASS:
				return CODE_EXECUTOR_CLASS_EDEFAULT == null ? codeExecutorClass != null : !CODE_EXECUTOR_CLASS_EDEFAULT.equals(codeExecutorClass);
			case Sequential_xdsmlPackage.DSA_PROJECT__ENTRY_POINT:
				return ENTRY_POINT_EDEFAULT == null ? entryPoint != null : !ENTRY_POINT_EDEFAULT.equals(entryPoint);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (codeExecutorClass: ");
		result.append(codeExecutorClass);
		result.append(", entryPoint: ");
		result.append(entryPoint);
		result.append(')');
		return result.toString();
	}

} //DSAProjectImpl
