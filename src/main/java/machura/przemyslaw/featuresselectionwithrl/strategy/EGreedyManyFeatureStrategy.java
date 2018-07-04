/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.strategy;

import java.util.ArrayList;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.ActionsSpace;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;

/**
 *
 * @author Przemek
 */
public class EGreedyManyFeatureStrategy extends EGreedySingleFeatureStrategy {

    protected SelectedAction selectManyFeaturesRandomly(ActionsSpace actionSpace, int featuresNumber) throws Exception {
        SelectedAction selectedAction = new SelectedAction();
        ActionsSpace actionSpaceCopy = ActionsSpace.createActionSpaceWithAllowedActionOnly(actionSpace);

        for (int i = 0; i < featuresNumber; i++) {
            SelectedAction singleAction = selectSingleFeatureRandomly(actionSpaceCopy);
            selectedAction.addAll(singleAction);
            System.out.println("Wybrana pojedyncza " + selectedAction);
            actionSpaceCopy.removeAllowedFeaturesAndSubsetBySelectedAction(singleAction);

            updateStatisticsByConsistentAction(singleAction);
            updateGreedyAndAdditionProbability();
        }
        return selectedAction;

    }

    /**
     * Funkcja służy do wykonania akcji zgodnie z ideą strategii e-zachłannej W
     * przypadku wyboru niezachłannego zostanie wybranych kilka cech do dodania
     * lub usunięcia
     *
     * @param maximizingActions
     * @param actionSpace
     * @param actionsNumber
     * @return
     * @throws Exception
     */
    protected SelectedAction selectManyFeatures(final StateActionEntityCollection stateActionEntityCollection,
            StateActionEntity stateActionEntity,
            final ActionsSpace actionSpace,
            Integer featuresNumber) throws Exception {

        ArrayList<Action> maximizingActions = stateActionEntity.getMaximizingActions();
        SelectedAction selectedAction = new SelectedAction();
        /*
        Zbiór akcji maksymalizującech nie jest pusty
        Następnie w ifie sprawdzamy, czy wybrać akcję losowo, czy zachłannie
         */
        if (maximizingActions != null && maximizingActions.size() > 0) {

            Double generated = random.nextDouble();

            //Akcja będzie wybrana losowo
            if (generated > greedyActionProbability) {
                selectedAction = selectManyFeaturesRandomly(actionSpace, featuresNumber);
                increaseTotalRandomAction();
            } /*
            Akcja będzie wybrana zachłannie (losowo z pośród maksymalizujących akcji)
            Uwaga - tutaj zostaje przekazana ta sama akcja (czyli TreeSet<Integer>), która została przekazana do metody w obiekcie HashMap<TreeSet<Integer>, Double> actionsValue
             */ else {
                selectedAction = selectSingleActionRandomly(maximizingActions);
                increaseTotalGreedyAction();
                updateStatistics(selectedAction);
                updateGreedyAndAdditionProbability();
            }
            /*
            Zbiór akcji maksymalizującech  jest pusty, więc akcję wybieramy losowo
             */
        } else {
            selectedAction = selectManyFeaturesRandomly(actionSpace, featuresNumber);
            increaseTotalRandomAction();
            //updateStatistics(selectedAction);
            //updateGreedyAndAdditionProbability();
        }
        return selectedAction;
    }

    /**
     * Funkcja służy do wykonania akcji zgodnie z ideą strategii e-zachłannej W
     * przypadku wyboru niezachłannego zostanie wybranych kilka cech do dodania
     * lub usunięcia
     *
     * @param stateActionEntityCollection
     * @param maximizingActions
     * @param stateActionEntity
     * @param actionSpace
     * @return
     * @throws Exception
     */
    @Override
    public SelectedAction selectFeatures(final StateActionEntityCollection stateActionEntityCollection,
            StateActionEntity stateActionEntity,
            final ActionsSpace actionSpace) throws Exception {

        return selectManyFeatures(stateActionEntityCollection, stateActionEntity, actionSpace, numberOfFeaturesToSelect);
    }

    @Override
    public ArrayList<SelectedAction> simulateManyDifferentFeatureRandomSelection(ActionsSpace actionSpace, Integer actionsNumber) throws Exception {
        ArrayList<SelectedAction> selectedActionsList = new ArrayList<>();
        ActionsSpace actionSpaceCopy = ActionsSpace.createActionSpaceWithAllowedActionOnly(actionSpace);

        for (int i = 0; i < actionsNumber; i++) {
            SelectedAction selectedAction = new SelectedAction();
            for (int j = 0; j < numberOfFeaturesToSelect; j++) {
                SelectedAction singleAction = selectSingleFeatureRandomly(actionSpaceCopy);
                selectedAction.addAll(singleAction);

                actionSpaceCopy.removeAllowedFeaturesBySelectedAction(singleAction);
            }
            selectedActionsList.add(selectedAction);
            updateSimulatedStatistics(selectedAction);

        }
        return selectedActionsList;
    }
}
