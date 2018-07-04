/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.updaters;

import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessKeeper;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessVariables;
import machura.przemyslaw.featuresselectionwithrl.returns.Reward;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;

/**
 *
 * @author Przemek
 */
public class SarsaUpdater extends StateValueEntityUpdater {
    

    
    public SarsaUpdater(){
        super();
    }

    public SarsaUpdater(double discountingCoefficient) {
        super(discountingCoefficient);

    }
      
    
    @Override
    public Action update(StateActionEntityCollection stateActionEntityCollection, ProcessKeeper processKeeper){
        if(processKeeper.keepedStepsSize() < 2){
            return null;
        }
        Debuger.println("[AKTUALIZATOR SA]  Aktualizacja");
        ProcessVariables previousProcessVariables = processKeeper.getLastProcessVariables(2);
        Action actionToUpdate = previousProcessVariables.getAction();
        Reward reward = previousProcessVariables.getReward();
        
        ProcessVariables currentProcessVariables = processKeeper.getLastProcessVariables();
        Action currentAction = currentProcessVariables.getAction();
        
        Double delta = reward.getValue() + discountingCoefficient*currentAction.getValue() - actionToUpdate.getValue();
        Double finalValue = actionToUpdate.getValue() + stepSizeGenerator.generateStepSize(actionToUpdate.getUpdateCount()+1)*delta;
        actionToUpdate.setValue(finalValue);
        actionToUpdate.addUpdateTimeStep(currentProcessVariables.getTimeStep());
        Debuger.println("[AKTUALIZATOR SA]  Ustanowiona wartość: " + finalValue);
        return actionToUpdate;
    }
    
 
    
}
