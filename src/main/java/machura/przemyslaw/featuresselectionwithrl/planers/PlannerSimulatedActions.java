/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.planers;

import java.util.ArrayList;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessKeeper;
import machura.przemyslaw.featuresselectionwithrl.helpers.ProcessOutputPrinter;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessState;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessVariables;
import machura.przemyslaw.featuresselectionwithrl.returns.Reward;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class PlannerSimulatedActions extends Planner{
    
    Integer simulatedActionNumber = 2;
    
    public PlannerSimulatedActions(FSProcess process) {
        this.process = process;

    }
    
    
    @Override
    public void run() throws Exception {
        Debuger.println("[PLANNER SiumlatedActions]");
        ProcessKeeper processKeeperCopy = process.getProcessKeeper().copy(); 
        ArrayList<SelectedAction> simulatedActionsList = process.getStrategy().simulateManyDifferentFeatureRandomSelection(process.getActionSpace(), simulatedActionNumber);
        for (SelectedAction simulatedAction : simulatedActionsList) {
            Debuger.println("[PLANNER SiumlatedActions] Symulowana akcja: " + ProcessOutputPrinter.print(simulatedAction));
            Debuger.println("[PLANNER SiumlatedActions] Obecna encja");
            Debuger.println(ProcessOutputPrinter.print(process.getCurrentStateActionEntity()));
            Action action = process.getCurrentStateActionEntity().createOrGetAction(simulatedAction);
            action.addSimulationTimeStep(process.getTimeStep());
            ProcessState simulatedNextProcessState = process.getProcessState().copy(); 
            simulatedNextProcessState.updateBySelectedAction(simulatedAction);
            StateActionEntity nextStateActionEntity = process.getStateActionEntityCollection().createOrGetEntity(simulatedNextProcessState.getState());
            Double classificationEvaluation = process.performClassificationEvaluation(simulatedNextProcessState, nextStateActionEntity);
            Reward reward = process.getRewardGenerator().generateReward(classificationEvaluation, process.getStrategy().getMaxFeaturesInSubset(), nextStateActionEntity.getFeaturesNumber());

            Debuger.println("[PLANNER SiumlatedActions] Następny stan symulowany: " + ProcessOutputPrinter.print(simulatedNextProcessState));
            Debuger.println("[PLANNER SiumlatedActions] Klasyfikacja: " + classificationEvaluation + "  Nagroda: " + reward.getValue());
            
            ProcessVariables processVariables = new ProcessVariables();
            processVariables.setAction(action);
            processVariables.setCurrentStateActionEntity(process.getCurrentStateActionEntity());
            processVariables.setNextStateActionEntity(nextStateActionEntity);
            processVariables.setReward(reward);
            processVariables.setTimeStep(process.getTimeStep());
            processKeeperCopy.add(processVariables);
            process.getEntityUpdater().update(process.getStateActionEntityCollection(), processKeeperCopy);
            processKeeperCopy.removeLast();
            
            nextStateActionEntity.getState().setValue(reward.getValue());
            nextStateActionEntity.getState().addDiscoveryTimeStep(process.getTimeStep());
            
            
            Boolean addedToBestStatesCollection = process.getBestStateActionsCollector().add(nextStateActionEntity); 
            Debuger.println("[PLANNER SimulatedActions] Czy dodano do kolekcji najlepszych stanów: " + addedToBestStatesCollection);
            
            Debuger.println("[PLANNER SiumlatedActions] Następna encja");
            Debuger.println(ProcessOutputPrinter.print(nextStateActionEntity));
            

        }
    }

    public Integer getSimulatedActionNumber() {
        return simulatedActionNumber;
    }

    public void setSimulatedActionNumber(Integer simulatedActionNumber) {
        this.simulatedActionNumber = simulatedActionNumber;
    }

 
    
    
}
