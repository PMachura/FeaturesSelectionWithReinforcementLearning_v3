/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.updaters;

import java.util.Iterator;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessKeeper;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessVariables;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;

/**
 *
 * @author Przemek
 */
public class SarsaNStepUpdater extends StateValueEntityUpdater {

    protected Integer nStep = 3;
    
    private Double calculateNStepDiscountedReturn(ProcessKeeper processKeeper){
        
        /**
         * Fragment obliczający zdyskontowaną sumę z otrzymanych nagród
         */
        Iterator<ProcessVariables> iter = processKeeper.getProcessVariablesList().descendingIterator();
        ProcessVariables processVariables;
        int count = 0;
        Double ret = 0.0;
        iter.next();
        while (iter.hasNext() && (count < nStep)) {
            processVariables = iter.next();
            ret += processVariables.getReward().getValue() * Math.pow(discountingCoefficient, nStep - count - 1);
            count++;
        }
        if (count < nStep) {
            throw new RuntimeException("Zbyt mało zapamiętanych kroków, by obliczyć żądany zwrot");
        }
        
        Debuger.println("Wartość zwrotu z nagród " + ret);
        /**
         * Fragment dodający do wartości obliczonego zwrotu zdyskontowaną wartość ostatnio wykonanej akcji
         */
        ProcessVariables currentProcessVariables = processKeeper.getLastProcessVariables();
        Action currentAction = currentProcessVariables.getAction();
        ret += Math.pow(discountingCoefficient, nStep)*currentAction.getValue();
        
        Debuger.println("Ostateczna wartość zwrotu " + ret);
        return ret;
    }
    
    
    
    
    @Override
    public Action update(StateActionEntityCollection stateActionEntityCollection, ProcessKeeper processKeeper){
        if(processKeeper.keepedStepsSize() < nStep + 1){
            return null;
        }
        Debuger.println("[AKTUALIZATOR SA]  Aktualizacja");
        ProcessVariables processVariablesToUpdate = processKeeper.getLastProcessVariables(nStep + 1);
        Action actionToUpdate = processVariablesToUpdate.getAction();
        
        Double nStepDiscountedReturn = calculateNStepDiscountedReturn(processKeeper);
        
        Double delta = nStepDiscountedReturn - actionToUpdate.getValue();
        Double finalValue =  actionToUpdate.getValue() + stepSizeGenerator.generateStepSize(actionToUpdate.getUpdateCount()+1)*delta;
        actionToUpdate.setValue(finalValue);
        actionToUpdate.addUpdateTimeStep(processKeeper.getLastProcessVariables().getTimeStep());
        Debuger.println("[AKTUALIZATOR SA]  Ustanowiona wartość: " + finalValue);
        
        return actionToUpdate;
    }

    public Integer getnStep() {
        return nStep;
    }

    public void setnStep(Integer nStep) {
        this.nStep = nStep;
    }
    
    
    
    
}
