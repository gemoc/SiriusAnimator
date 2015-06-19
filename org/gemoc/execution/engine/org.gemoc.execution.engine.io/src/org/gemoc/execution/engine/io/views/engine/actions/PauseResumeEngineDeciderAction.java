package org.gemoc.execution.engine.io.views.engine.actions;

import org.gemoc.execution.engine.io.SharedIcons;
import org.gemoc.execution.engine.io.views.AbstractUserDecider;
import org.gemoc.gemoc_language_workbench.api.core.EngineStatus.RunStatus;
import org.gemoc.gemoc_language_workbench.api.core.IExecutionEngine;
import org.gemoc.gemoc_language_workbench.api.core.ILogicalStepDecider;

public class PauseResumeEngineDeciderAction extends AbstractEngineAction
{
	
	
	private DeciderAction _currentAction;
	
	private DeciderAction _stepByStepDeciderAction;
	
	public PauseResumeEngineDeciderAction()
	{
		super();
	}
	
	protected void init(){
		_stepByStepDeciderAction = DeciderManager.getStepByStepDeciderAction();
		_currentAction = _stepByStepDeciderAction;		
		updateButton();
	}
	
	protected void updateButton() {
		if (_currentAction != null)
		{
			// base text from Decider
			setText(_currentAction.getText());
			setToolTipText(_currentAction.getToolTipText());
			//setImageDescriptor(_currentAction.getImageDescriptor());
			if (getCurrentSelectedEngine() == null || getCurrentSelectedEngine().getRunningStatus().equals(RunStatus.Stopped))
			{
				setEnabled(false);
				setImageDescriptor(SharedIcons.SUSPEND_ENGINE_DECIDER_ICON);
			}
			else
			{
				setEnabled(	true);
				
				// find the decider opposed to the one currently used by the engine
				_currentAction = DeciderManager.getSwitchDeciderAction(getCurrentSelectedEngine().getExecutionContext().getLogicalStepDecider());
				if(_currentAction.equals(_stepByStepDeciderAction)){
					setToolTipText("Suspend selected engine using "+ _currentAction.getText());
					setImageDescriptor(SharedIcons.SUSPEND_ENGINE_DECIDER_ICON);
				} else {
					setToolTipText("Resume selected engine using "+ _currentAction.getText());
					setImageDescriptor(SharedIcons.RESUME_ENGINE_DECIDER_ICON);
				}
			}
			
			
		}
	}

	@Override
	public void run()
	{
		if (getCurrentSelectedEngine() != null
			&& _currentAction != null)
		{
			
			ILogicalStepDecider savedDecider = getCurrentSelectedEngine().getExecutionContext().getLogicalStepDecider();
			// apply the decider change
			_currentAction.run();			
			// now switch UI to the alternative Action by  refreshing UI
			updateButton();
			// relaunch the engine Ie. unlock possibly locked StepByStepDecider, for non "StepByStepDecider, simply let them run one more time
			if(savedDecider instanceof AbstractUserDecider){
				// get the equivalent decision from the new Decider
				org.gemoc.execution.engine.trace.gemoc_execution_trace.LogicalStep selectedlogicalStep;
				try {
					selectedlogicalStep = getCurrentSelectedEngine().getExecutionContext().getLogicalStepDecider().decide(getCurrentSelectedEngine(), getCurrentSelectedEngine().getPossibleLogicalSteps());
					((AbstractUserDecider) savedDecider).decideFromTimeLine(selectedlogicalStep);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	@Override
	public void motorSelectionChanged(IExecutionEngine engine) {
		super.motorSelectionChanged(engine);
		if(engine != null){
			_currentAction.setEngine(engine);
			updateButton();
		}
	}

	
	

	

}
