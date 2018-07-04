/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process;

import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.helpers.ProcessOutputPrinter;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessVariables;
import weka.core.Instances;

/**
 *
 * @author Przemek
 */
public class FSSimpleProcess extends FSProcess {

    public FSSimpleProcess(Instances data, String dataFile) throws Exception {
        super(data, dataFile);
    }

    @Override
    public void run() throws Exception {

        processTimeWatch.start();
        currentStateActionEntity = stateActionEntityCollection.createOrGetEntity(processState.getState());
        nextStateActionEntity = null;
        currentAction = null;

        classificationEvaluation = performClassificationEvaluation(processState, currentStateActionEntity); //UWAGA TUTAJ TRZEBA DOPISAĆ USTANOWIENIE WARTOŚCI STANU
        currentStateActionEntity.getState().setValue(rewardGenerator.generateReward(classificationEvaluation, strategy.getMaxFeaturesInSubset(), currentStateActionEntity.getFeaturesNumber()).getValue());
        currentStateActionEntity.getState().addDiscoveryTimeStep(timeStep);
        Boolean addedToBestCollection =  bestStateActionsCollector.add(currentStateActionEntity);
        Debuger.println("[PROCES] Czy dodano do kolekcji najlepszych stanów: " + addedToBestCollection);
        
        for (timeStep = 0; timeStep < timeStepsNumber; timeStep++) {
            System.out.println("**********************KROK  [" + timeStep + "]  *******************************");

            Debuger.println("[PROCES] Obecna encja, początek pętli");
            Debuger.println(ProcessOutputPrinter.print(currentStateActionEntity));
            
            Debuger.println("[PROCES] Przestrzeń ackji, początek pętli");
            Debuger.println(ProcessOutputPrinter.print(actionSpace));

            currentStateActionEntity.getState().addTimeStep(timeStep);
            selectedAction = strategy.selectFeatures(stateActionEntityCollection, currentStateActionEntity, actionSpace);
            currentAction = currentStateActionEntity.createOrGetAction(selectedAction);
            currentAction.addTimeStep(timeStep);

            Debuger.println("[PROCES] Wybrana akcja " + ProcessOutputPrinter.print(selectedAction));

            processState.updateBySelectedAction(selectedAction);
            nextStateActionEntity = stateActionEntityCollection.createOrGetEntity(processState.getState());
            nextStateActionEntity.getState().addDiscoveryTimeStep(timeStep);

            Debuger.println("[PROCES] Stan procesu " + ProcessOutputPrinter.print(processState));

            classificationEvaluation = performClassificationEvaluation(processState, nextStateActionEntity);
            reward = rewardGenerator.generateReward(classificationEvaluation, strategy.getMaxFeaturesInSubset(), nextStateActionEntity.getFeaturesNumber());

            Debuger.println("[PROCES] Ocena klasyfikatora " + classificationEvaluation);
            Debuger.println("[PROCES] Wartość nagrody " + reward.getValue());

            
            
            processVariables = new ProcessVariables();
            processVariables.setAction(currentAction);
            processVariables.setCurrentStateActionEntity(currentStateActionEntity);
            processVariables.setNextStateActionEntity(nextStateActionEntity);
            processVariables.setReward(reward);
            processVariables.setTimeStep(timeStep);
            Boolean addedToKeeper = processKeeper.add(processVariables);
            Debuger.println("[PROCES] Czy dodano do historii stanów: " + addedToKeeper);

            entityUpdater.update(stateActionEntityCollection, processKeeper);


            nextStateActionEntity.getState().setValue(currentAction.getValue()); 
            nextStateActionEntity.getState().setValue(reward.getValue());
            
            addedToBestCollection =  bestStateActionsCollector.add(nextStateActionEntity);
            Debuger.println("[PROCES] Czy dodano do kolekcji najlepszych stanów: " + addedToBestCollection);

            Debuger.println("[PROCES] Obecna encja, koniec pętli");
            Debuger.println(ProcessOutputPrinter.print(currentStateActionEntity));
            
            Debuger.println("[PROCES] Następna encja, koniec pętli");
            Debuger.println(ProcessOutputPrinter.print(nextStateActionEntity));

            currentStateActionEntity = nextStateActionEntity;
            actionSpace.updateAllowedFeaturesAndSubset(selectedAction);
            
            if(checkInterruption()){
                break;
            }
        }

        processTimeWatch.addToTotalTime();
    }
}
