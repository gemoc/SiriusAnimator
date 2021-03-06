/**
 */
package org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Gemoc_execution_traceFactory
 * @model kind="package"
 * @generated
 */
public interface Gemoc_execution_tracePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "gemoc_execution_trace";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.inira.fr/gemoc_execution_trace";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "gemoc_execution_trace";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Gemoc_execution_tracePackage eINSTANCE = org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ChoiceImpl <em>Choice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ChoiceImpl
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getChoice()
	 * @generated
	 */
	int CHOICE = 0;

	/**
	 * The feature id for the '<em><b>Next Choices</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__NEXT_CHOICES = 0;

	/**
	 * The feature id for the '<em><b>Possible Logical Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__POSSIBLE_LOGICAL_STEPS = 1;

	/**
	 * The feature id for the '<em><b>Chosen Logical Step</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__CHOSEN_LOGICAL_STEP = 2;

	/**
	 * The feature id for the '<em><b>Context State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__CONTEXT_STATE = 3;

	/**
	 * The feature id for the '<em><b>Previous Choice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__PREVIOUS_CHOICE = 4;

	/**
	 * The feature id for the '<em><b>Selected Next Choice</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__SELECTED_NEXT_CHOICE = 5;

	/**
	 * The feature id for the '<em><b>Branch</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__BRANCH = 6;

	/**
	 * The feature id for the '<em><b>Owned MSE Occurrences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__OWNED_MSE_OCCURRENCES = 7;

	/**
	 * The number of structural features of the '<em>Choice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Choice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ExecutionTraceModelImpl <em>Execution Trace Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ExecutionTraceModelImpl
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getExecutionTraceModel()
	 * @generated
	 */
	int EXECUTION_TRACE_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Choices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_MODEL__CHOICES = 0;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_MODEL__BRANCHES = 1;

	/**
	 * The feature id for the '<em><b>Reached States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_MODEL__REACHED_STATES = 2;

	/**
	 * The number of structural features of the '<em>Execution Trace Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Execution Trace Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_TRACE_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.SolverStateImpl <em>Solver State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.SolverStateImpl
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getSolverState()
	 * @generated
	 */
	int SOLVER_STATE = 2;

	/**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLVER_STATE__MODEL = 0;

	/**
	 * The feature id for the '<em><b>Serializable Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLVER_STATE__SERIALIZABLE_MODEL = 1;

	/**
	 * The number of structural features of the '<em>Solver State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLVER_STATE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Solver State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLVER_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ModelStateImpl <em>Model State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ModelStateImpl
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getModelState()
	 * @generated
	 */
	int MODEL_STATE = 3;

	/**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STATE__MODEL = 0;

	/**
	 * The feature id for the '<em><b>Context State</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STATE__CONTEXT_STATE = 1;

	/**
	 * The number of structural features of the '<em>Model State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STATE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Model State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ContextStateImpl <em>Context State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ContextStateImpl
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getContextState()
	 * @generated
	 */
	int CONTEXT_STATE = 4;

	/**
	 * The feature id for the '<em><b>Model State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STATE__MODEL_STATE = 0;

	/**
	 * The feature id for the '<em><b>Solver State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STATE__SOLVER_STATE = 1;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STATE__CHOICE = 2;

	/**
	 * The number of structural features of the '<em>Context State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Context State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.BranchImpl <em>Branch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.BranchImpl
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getBranch()
	 * @generated
	 */
	int BRANCH = 5;

	/**
	 * The feature id for the '<em><b>Start Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__START_INDEX = 0;

	/**
	 * The feature id for the '<em><b>Stop Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__STOP_INDEX = 1;

	/**
	 * The feature id for the '<em><b>Choices</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__CHOICES = 2;

	/**
	 * The number of structural features of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>ISerializable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getISerializable()
	 * @generated
	 */
	int ISERIALIZABLE = 6;


	/**
	 * Returns the meta object for class '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Choice</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice
	 * @generated
	 */
	EClass getChoice();

	/**
	 * Returns the meta object for the reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getNextChoices <em>Next Choices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Next Choices</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getNextChoices()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_NextChoices();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getPossibleLogicalSteps <em>Possible Logical Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Possible Logical Steps</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getPossibleLogicalSteps()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_PossibleLogicalSteps();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getChosenLogicalStep <em>Chosen Logical Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Chosen Logical Step</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getChosenLogicalStep()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_ChosenLogicalStep();

	/**
	 * Returns the meta object for the containment reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getContextState <em>Context State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getContextState()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_ContextState();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getPreviousChoice <em>Previous Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Previous Choice</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getPreviousChoice()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_PreviousChoice();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getSelectedNextChoice <em>Selected Next Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Selected Next Choice</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getSelectedNextChoice()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_SelectedNextChoice();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getBranch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Branch</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getBranch()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_Branch();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getOwnedMSEOccurrences <em>Owned MSE Occurrences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned MSE Occurrences</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Choice#getOwnedMSEOccurrences()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_OwnedMSEOccurrences();

	/**
	 * Returns the meta object for class '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel <em>Execution Trace Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution Trace Model</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel
	 * @generated
	 */
	EClass getExecutionTraceModel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel#getChoices <em>Choices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Choices</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel#getChoices()
	 * @see #getExecutionTraceModel()
	 * @generated
	 */
	EReference getExecutionTraceModel_Choices();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Branches</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel#getBranches()
	 * @see #getExecutionTraceModel()
	 * @generated
	 */
	EReference getExecutionTraceModel_Branches();

	/**
	 * Returns the meta object for the containment reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel#getReachedStates <em>Reached States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reached States</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ExecutionTraceModel#getReachedStates()
	 * @see #getExecutionTraceModel()
	 * @generated
	 */
	EReference getExecutionTraceModel_ReachedStates();

	/**
	 * Returns the meta object for class '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.SolverState <em>Solver State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Solver State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.SolverState
	 * @generated
	 */
	EClass getSolverState();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.SolverState#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.SolverState#getModel()
	 * @see #getSolverState()
	 * @generated
	 */
	EReference getSolverState_Model();

	/**
	 * Returns the meta object for the attribute '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.SolverState#getSerializableModel <em>Serializable Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serializable Model</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.SolverState#getSerializableModel()
	 * @see #getSolverState()
	 * @generated
	 */
	EAttribute getSolverState_SerializableModel();

	/**
	 * Returns the meta object for class '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ModelState <em>Model State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ModelState
	 * @generated
	 */
	EClass getModelState();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ModelState#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ModelState#getModel()
	 * @see #getModelState()
	 * @generated
	 */
	EReference getModelState_Model();

	/**
	 * Returns the meta object for the reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ModelState#getContextState <em>Context State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Context State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ModelState#getContextState()
	 * @see #getModelState()
	 * @generated
	 */
	EReference getModelState_ContextState();

	/**
	 * Returns the meta object for class '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState <em>Context State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState
	 * @generated
	 */
	EClass getContextState();

	/**
	 * Returns the meta object for the reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState#getModelState <em>Model State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState#getModelState()
	 * @see #getContextState()
	 * @generated
	 */
	EReference getContextState_ModelState();

	/**
	 * Returns the meta object for the containment reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState#getSolverState <em>Solver State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Solver State</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState#getSolverState()
	 * @see #getContextState()
	 * @generated
	 */
	EReference getContextState_SolverState();

	/**
	 * Returns the meta object for the container reference '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState#getChoice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Choice</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.ContextState#getChoice()
	 * @see #getContextState()
	 * @generated
	 */
	EReference getContextState_Choice();

	/**
	 * Returns the meta object for class '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Branch</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch
	 * @generated
	 */
	EClass getBranch();

	/**
	 * Returns the meta object for the attribute '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch#getStartIndex <em>Start Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Index</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch#getStartIndex()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_StartIndex();

	/**
	 * Returns the meta object for the attribute '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch#getStopIndex <em>Stop Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stop Index</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch#getStopIndex()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_StopIndex();

	/**
	 * Returns the meta object for the reference list '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch#getChoices <em>Choices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Choices</em>'.
	 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.Branch#getChoices()
	 * @see #getBranch()
	 * @generated
	 */
	EReference getBranch_Choices();

	/**
	 * Returns the meta object for data type '<em>ISerializable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ISerializable</em>'.
	 * @model instanceClass="byte[]"
	 * @generated
	 */
	EDataType getISerializable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Gemoc_execution_traceFactory getGemoc_execution_traceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ChoiceImpl <em>Choice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ChoiceImpl
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getChoice()
		 * @generated
		 */
		EClass CHOICE = eINSTANCE.getChoice();

		/**
		 * The meta object literal for the '<em><b>Next Choices</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__NEXT_CHOICES = eINSTANCE.getChoice_NextChoices();

		/**
		 * The meta object literal for the '<em><b>Possible Logical Steps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__POSSIBLE_LOGICAL_STEPS = eINSTANCE.getChoice_PossibleLogicalSteps();

		/**
		 * The meta object literal for the '<em><b>Chosen Logical Step</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__CHOSEN_LOGICAL_STEP = eINSTANCE.getChoice_ChosenLogicalStep();

		/**
		 * The meta object literal for the '<em><b>Context State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__CONTEXT_STATE = eINSTANCE.getChoice_ContextState();

		/**
		 * The meta object literal for the '<em><b>Previous Choice</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__PREVIOUS_CHOICE = eINSTANCE.getChoice_PreviousChoice();

		/**
		 * The meta object literal for the '<em><b>Selected Next Choice</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__SELECTED_NEXT_CHOICE = eINSTANCE.getChoice_SelectedNextChoice();

		/**
		 * The meta object literal for the '<em><b>Branch</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__BRANCH = eINSTANCE.getChoice_Branch();

		/**
		 * The meta object literal for the '<em><b>Owned MSE Occurrences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__OWNED_MSE_OCCURRENCES = eINSTANCE.getChoice_OwnedMSEOccurrences();

		/**
		 * The meta object literal for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ExecutionTraceModelImpl <em>Execution Trace Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ExecutionTraceModelImpl
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getExecutionTraceModel()
		 * @generated
		 */
		EClass EXECUTION_TRACE_MODEL = eINSTANCE.getExecutionTraceModel();

		/**
		 * The meta object literal for the '<em><b>Choices</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_TRACE_MODEL__CHOICES = eINSTANCE.getExecutionTraceModel_Choices();

		/**
		 * The meta object literal for the '<em><b>Branches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_TRACE_MODEL__BRANCHES = eINSTANCE.getExecutionTraceModel_Branches();

		/**
		 * The meta object literal for the '<em><b>Reached States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXECUTION_TRACE_MODEL__REACHED_STATES = eINSTANCE.getExecutionTraceModel_ReachedStates();

		/**
		 * The meta object literal for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.SolverStateImpl <em>Solver State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.SolverStateImpl
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getSolverState()
		 * @generated
		 */
		EClass SOLVER_STATE = eINSTANCE.getSolverState();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOLVER_STATE__MODEL = eINSTANCE.getSolverState_Model();

		/**
		 * The meta object literal for the '<em><b>Serializable Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLVER_STATE__SERIALIZABLE_MODEL = eINSTANCE.getSolverState_SerializableModel();

		/**
		 * The meta object literal for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ModelStateImpl <em>Model State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ModelStateImpl
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getModelState()
		 * @generated
		 */
		EClass MODEL_STATE = eINSTANCE.getModelState();

		/**
		 * The meta object literal for the '<em><b>Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_STATE__MODEL = eINSTANCE.getModelState_Model();

		/**
		 * The meta object literal for the '<em><b>Context State</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_STATE__CONTEXT_STATE = eINSTANCE.getModelState_ContextState();

		/**
		 * The meta object literal for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ContextStateImpl <em>Context State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.ContextStateImpl
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getContextState()
		 * @generated
		 */
		EClass CONTEXT_STATE = eINSTANCE.getContextState();

		/**
		 * The meta object literal for the '<em><b>Model State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_STATE__MODEL_STATE = eINSTANCE.getContextState_ModelState();

		/**
		 * The meta object literal for the '<em><b>Solver State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_STATE__SOLVER_STATE = eINSTANCE.getContextState_SolverState();

		/**
		 * The meta object literal for the '<em><b>Choice</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_STATE__CHOICE = eINSTANCE.getContextState_Choice();

		/**
		 * The meta object literal for the '{@link org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.BranchImpl <em>Branch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.BranchImpl
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getBranch()
		 * @generated
		 */
		EClass BRANCH = eINSTANCE.getBranch();

		/**
		 * The meta object literal for the '<em><b>Start Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__START_INDEX = eINSTANCE.getBranch_StartIndex();

		/**
		 * The meta object literal for the '<em><b>Stop Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__STOP_INDEX = eINSTANCE.getBranch_StopIndex();

		/**
		 * The meta object literal for the '<em><b>Choices</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BRANCH__CHOICES = eINSTANCE.getBranch_Choices();

		/**
		 * The meta object literal for the '<em>ISerializable</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gemoc.executionframework.reflectivetrace.gemoc_execution_trace.impl.Gemoc_execution_tracePackageImpl#getISerializable()
		 * @generated
		 */
		EDataType ISERIALIZABLE = eINSTANCE.getISerializable();

	}

} //Gemoc_execution_tracePackage
