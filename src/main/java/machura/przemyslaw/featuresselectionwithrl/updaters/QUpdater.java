/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.updaters;

import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessKeeper;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessVariables;
import machura.przemyslaw.featuresselectionwithrl.returns.Reward;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;

/**
 *
 * @author Przemek
 */
public class QUpdater extends StateValueEntityUpdater{
    

    
    public QUpdater(){
        super();
    }
 
    
    public Action update(StateActionEntityCollection stateActionEntityCollection, ProcessKeeper processKeeper){
        Debuger.println("[AKTUALIZATOR SA]  Aktualizacja");
        ProcessVariables currentProcessVariables = processKeeper.getLastProcessVariables();
        Action actionToUpdate = currentProcessVariables.getAction();
        Double nextStateMaxActionValue = currentProcessVariables.getNextStateActionEntity().getMaximizingActionsValue();
        Double delta = currentProcessVariables.getReward().getValue() + (discountingCoefficient*nextStateMaxActionValue) - actionToUpdate.getValue();
        Double finalValue =  actionToUpdate.getValue() + stepSizeGenerator.generateStepSize(actionToUpdate.getUpdateCount()+1)*delta;
        actionToUpdate.setValue(finalValue);
        actionToUpdate.addUpdateTimeStep(currentProcessVariables.getTimeStep());
        Debuger.println("[AKTUALIZATOR SA]  Ustanowiona wartość: " + finalValue);
        return actionToUpdate;
    }
}
