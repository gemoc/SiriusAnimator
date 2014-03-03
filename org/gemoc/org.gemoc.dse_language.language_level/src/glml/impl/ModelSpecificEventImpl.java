/**
 */
package glml.impl;

import gepl.Pattern;

import glml.DomainSpecificEvent;
import glml.GlmlPackage;
import glml.ModelSpecificAction;
import glml.ModelSpecificEvent;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Specific Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link glml.impl.ModelSpecificEventImpl#getReification <em>Reification</em>}</li>
 *   <li>{@link glml.impl.ModelSpecificEventImpl#getModelSpecificActions <em>Model Specific Actions</em>}</li>
 *   <li>{@link glml.impl.ModelSpecificEventImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelSpecificEventImpl extends NamedElementImpl implements ModelSpecificEvent {
	/**
	 * The cached value of the '{@link #getReification() <em>Reification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReification()
	 * @generated
	 * @ordered
	 */
	protected DomainSpecificEvent reification;

	/**
	 * The cached value of the '{@link #getModelSpecificActions() <em>Model Specific Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelSpecificActions()
	 * @generated
	 * @ordered
	 */
	protected EList<ModelSpecificAction> modelSpecificActions;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Pattern condition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelSpecificEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GlmlPackage.Literals.MODEL_SPECIFIC_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainSpecificEvent getReification() {
		if (reification != null && reification.eIsProxy()) {
			InternalEObject oldReification = (InternalEObject)reification;
			reification = (DomainSpecificEvent)eResolveProxy(oldReification);
			if (reification != oldReification) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GlmlPackage.MODEL_SPECIFIC_EVENT__REIFICATION, oldReification, reification));
			}
		}
		return reification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainSpecificEvent basicGetReification() {
		return reification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReification(DomainSpecificEvent newReification) {
		DomainSpecificEvent oldReification = reification;
		reification = newReification;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GlmlPackage.MODEL_SPECIFIC_EVENT__REIFICATION, oldReification, reification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModelSpecificAction> getModelSpecificActions() {
		if (modelSpecificActions == null) {
			modelSpecificActions = new EObjectContainmentWithInverseEList<ModelSpecificAction>(ModelSpecificAction.class, this, GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS, GlmlPackage.MODEL_SPECIFIC_ACTION__OWNING_MODEL_SPECIFIC_EVENT);
		}
		return modelSpecificActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern getCondition() {
		if (condition != null && condition.eIsProxy()) {
			InternalEObject oldCondition = (InternalEObject)condition;
			condition = (Pattern)eResolveProxy(oldCondition);
			if (condition != oldCondition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GlmlPackage.MODEL_SPECIFIC_EVENT__CONDITION, oldCondition, condition));
			}
		}
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pattern basicGetCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(Pattern newCondition) {
		Pattern oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GlmlPackage.MODEL_SPECIFIC_EVENT__CONDITION, oldCondition, condition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getModelSpecificActions()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS:
				return ((InternalEList<?>)getModelSpecificActions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GlmlPackage.MODEL_SPECIFIC_EVENT__REIFICATION:
				if (resolve) return getReification();
				return basicGetReification();
			case GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS:
				return getModelSpecificActions();
			case GlmlPackage.MODEL_SPECIFIC_EVENT__CONDITION:
				if (resolve) return getCondition();
				return basicGetCondition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GlmlPackage.MODEL_SPECIFIC_EVENT__REIFICATION:
				setReification((DomainSpecificEvent)newValue);
				return;
			case GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS:
				getModelSpecificActions().clear();
				getModelSpecificActions().addAll((Collection<? extends ModelSpecificAction>)newValue);
				return;
			case GlmlPackage.MODEL_SPECIFIC_EVENT__CONDITION:
				setCondition((Pattern)newValue);
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
			case GlmlPackage.MODEL_SPECIFIC_EVENT__REIFICATION:
				setReification((DomainSpecificEvent)null);
				return;
			case GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS:
				getModelSpecificActions().clear();
				return;
			case GlmlPackage.MODEL_SPECIFIC_EVENT__CONDITION:
				setCondition((Pattern)null);
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
			case GlmlPackage.MODEL_SPECIFIC_EVENT__REIFICATION:
				return reification != null;
			case GlmlPackage.MODEL_SPECIFIC_EVENT__MODEL_SPECIFIC_ACTIONS:
				return modelSpecificActions != null && !modelSpecificActions.isEmpty();
			case GlmlPackage.MODEL_SPECIFIC_EVENT__CONDITION:
				return condition != null;
		}
		return super.eIsSet(featureID);
	}

} //ModelSpecificEventImpl
