/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.strategy;

import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.ActionProbabilityGenerator;
import java.util.ArrayList;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.ActionsSpace;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters.ActionProbabilityUpdater;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters.ConstantProductActionProbabilityUpdater;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.PowerFunctionProbabilityGenerator;

/**
 *
 * @author Przemek
 */
public class EGreedySingleFeatureStrategy extends Strategy {

    Boolean additionProbabilityGeneratorSwitch = false;
    Long stepsBeforeAdditionProbabilitySwitch = (long) 0;
    ActionProbabilityGenerator additionProbabilityGenerator = new PowerFunctionProbabilityGenerator(); //generator prawdopodobieństwa tego, czy w przypadku losowego wyboru cecha ma zostać dodana, czy usunięta

    Boolean greedyActionProbabilityUpdate = false;
    ActionProbabilityUpdater greedyActionProbabilityUpdater = new ConstantProductActionProbabilityUpdater();

    Double greedyActionProbability = 0.2; //Pr. podjęcia akcji zachłannej
    Double randomActionProbability = 1.0 - greedyActionProbability; //Pr. podjęcia akcji losowej
    
    Integer totalGreedyAction = 0;
    Integer totalRandomAction = 0;

    public EGreedySingleFeatureStrategy(Integer maxFeaturesInSubset) {
        super(maxFeaturesInSubset);
    }

    public EGreedySingleFeatureStrategy() {

    }

    /**
     * Aktualizacja prawdopodobieństwa wyboru akcji zachłannie Aktualizacja
     * zmiany trendu prawdopodobieństwa dodania cechy
     *
     * @throws Exception
     */
    public void updateGreedyAndAdditionProbability() throws Exception {
        if (greedyActionProbabilityUpdate) {
            updateGreedyActionProbability();
            Debuger.println("[STRATEGIA] Aktualizacja prawdopodobieństwa akcji zachłannej: " + greedyActionProbability);
        }
        if (additionProbabilityGeneratorSwitch) {
            Integer totalActions = totalAdditionAction + totalDeletionAction;
            if (!totalActions.equals(totalAdditionAndDeletion)) {
                throw new Exception("Błąd przy obliczaniu sumy akcji dodwanai i odejmowania cech");
            }
            if ((totalActions % stepsBeforeAdditionProbabilitySwitch) == 0) {
                Debuger.println("[STRATEGIA] Aktualizacja zmiany trendu prawdopodobieństwa dodania cechy");
                switchAdditionProbabilityTrend();
            }

        }
    }

    /**
     * Aktualizacja prawdopodobieństwa wybrania akcji w sposób zachłanny
     */
    public void updateGreedyActionProbability() {
        greedyActionProbability = greedyActionProbabilityUpdater.update(greedyActionProbability);
        randomActionProbability = 1.0 - greedyActionProbability;
    }

    public void switchAdditionProbabilityTrend() {
        additionProbabilityGenerator.switchCoefficient();
    }

    /**
     * Aktywujemy zmianę trendu w prawdopodobieństwie wyboru dodania cechy, w
     * przypadku gdy akcja wybierana jest losowo
     *
     * @throws Exception
     */
    public void setOnAdditionProbabilityGeneratorSwitch() throws Exception {

        stepsBeforeAdditionProbabilitySwitch = calculateStepsBeforeAdditionProbabilitySwitch();
        if (stepsBeforeAdditionProbabilitySwitch.equals(0)) {
            additionProbabilityGeneratorSwitch = false;
        } else {
            additionProbabilityGeneratorSwitch = true;
        }
        Debuger.println("[STRATEGIA] Kroki przed zmianą trendu prawdopodobieństwa dodania atrybutu" + stepsBeforeAdditionProbabilitySwitch);
    }

    /**
     * Funkcja włącza aktualizację prawdopodobieństwa wyboru akcji zachłannie
     * Dodatkowo obliczana jest współczynnik aktualizacji, tak by osiągnąć
     * ustanowioną wartość maksymalną w ostatnim kroku eksperymentu, czyli na
     * podstawie liczby kroków
     */
    public void setOnGreedyActionProbabilityUpdate(Integer stepsNumber) {
        greedyActionProbabilityUpdate = true;
        greedyActionProbabilityUpdater.setCoefficientToObtainMaxGeneratedValueInGivenSteps(greedyActionProbability, stepsNumber);
        Debuger.println("Wspolczynnik aktualizacji prawdopodobienstwa akcji zachlannej " + greedyActionProbabilityUpdater.getCoefficient());
    }

    /**
     * Obliczenie średniej ilości kroków, jakie należy wykonać przed zmianą
     * trendu prawdopodobieństwa dodania cechy. Krok rozumiany jest tu jako
     * dodanie bądź usunięcie pojedynczej cechy
     *
     * @return
     * @throws Exception
     */
    protected long calculateStepsBeforeAdditionProbabilitySwitch() throws Exception {
        Double meanAdditionProbability = additionProbabilityGenerator.getMeanProbability();
        Debuger.println("[STRATEGIA] Średnie prawdopodobieństwo dodania atrybutu " + meanAdditionProbability);
        if (meanAdditionProbability.equals(0.5)) {
            return 0;
        }

        Double meanAdditionPerStep = ((2.0 * meanAdditionProbability) - 1.0);
        if (meanAdditionPerStep < 0) {
            throw new Exception("Błąd przy wzynaczaniu kroków zmiany prawdopodobieństwa dodania bądź usunięcia cechy");
        }

        return Math.round((double)2 * (double)maxFeaturesInSubset / (double)meanAdditionPerStep); // UWAGA TU ZMIENIŁEM- dodałem mnożenie przez 2

    }

    /**
     * Funkcja wybierająca losowo cechę do dodania lub usunięcia z podzbioru
     *
     * @param actionSpace
     * @return
     * @throws Exception
     */
    protected SelectedAction selectSingleFeatureRandomly(ActionsSpace actionSpace) throws Exception {

        Debuger.println("[STRATEGIA] Akcja będzie wybrana losowo");
        SelectedAction selectedAction = new SelectedAction();

        //Czy dodać, czy odjąć ?
        Double insertOrRemove = random.nextDouble();

        Double additionProbability = additionProbabilityGenerator.generateProbability(maxFeaturesInSubset, actionSpace.getFeaturesInSubsetAmount(), additionSeries);
        Debuger.println("[STRATEGIA] Prawdopodobieństwo dodania cechy: " + additionProbability);
        if (actionSpace.getFeaturesAllowedToRemove().isEmpty() && actionSpace.getFeaturesAllowedToAdd().isEmpty()) {
            return selectedAction;
        }
        Debuger.println("[STRATEGIA] Rozmiar zbioru z ActionSpace: " + actionSpace.getFeaturesInSubsetAmount());
        //Trzeba dodać, bo zbiór możliwych do usunięca pusty, a możemy dodawać
        if (actionSpace.getFeaturesAllowedToRemove().isEmpty() && actionSpace.getFeaturesInSubsetAmount() < maxFeaturesInSubset) {
            Debuger.println("[STRATEGIA] Konieczność dodania cechy");
            Feature singleAction = selectSingleFeatureFromAllowedToAddRandomly(actionSpace);
            selectedAction.addFeatureToAdd(singleAction);
            //Trzeba usunąć, bo zbiór możliwych do dodania jest pusty lub osiągnięto maksymalną liczbę cech w zbiorze
        } else if (actionSpace.getFeaturesAllowedToAdd().isEmpty() || actionSpace.getFeaturesInSubsetAmount() >= maxFeaturesInSubset) {
            Debuger.println("[STRATEGIA] Konieczność usunięcia cechy");
            Feature singleAction = selectSingleFeatureFromAllowedToRemoveRandomly(actionSpace);
            selectedAction.addFeatureToRemove(singleAction);
            //Dodaj tak wynika z losowania
        } else if (additionProbability >= insertOrRemove) {
            Debuger.println("[STRATEGIA] Dodanie cechy- efekt losowania");
            Feature singleAction = selectSingleFeatureFromAllowedToAddRandomly(actionSpace);
            selectedAction.addFeatureToAdd(singleAction);
            //Usuń tak wynika z losowania
        } else if (additionProbability < insertOrRemove) {
            Debuger.println("[STRATEGIA] Usunięcie cechy- efekt losowania");
            Feature singleAction = selectSingleFeatureFromAllowedToRemoveRandomly(actionSpace);
            selectedAction.addFeatureToRemove(singleAction);
        }

        return selectedAction;
    }
    
    @Override
    public ArrayList<SelectedAction> simulateManyDifferentFeatureRandomSelection(ActionsSpace actionSpace, Integer actionsNumber) throws Exception{
        Debuger.println("[STRATEGIA] Symulacja akcji wybieranych w sposób losowy");
        ArrayList<SelectedAction> selectedActionsList = new ArrayList<>();
        ActionsSpace actionSpaceCopy = ActionsSpace.createActionSpaceWithAllowedActionOnly(actionSpace);

        for (int i = 0; i < actionsNumber; i++) {
            SelectedAction singleAction = selectSingleFeatureRandomly(actionSpaceCopy);
            selectedActionsList.add(singleAction);
            actionSpaceCopy.removeAllowedFeaturesBySelectedAction(singleAction);
            updateSimulationStatisticsByConsistentAction(singleAction);
        }
        return selectedActionsList;
    }

    /**
     * Funkcja służy do wykonania akcji zgodnie z ideą strategii e-zachłannej W
     * przypadku wyboru niezachłannego zostanie wybrana tylko jedna cecha do
     * dodania lub usunięcia
     *
     * @param actionSpace
     * @return
     * @throws Exception
     */
    @Override
    public SelectedAction selectFeatures(final StateActionEntityCollection stateActionEntityCollection,
            StateActionEntity stateActionEntity,
            final ActionsSpace actionSpace) throws Exception {
        ArrayList<Action> maximizingActions = stateActionEntity.getMaximizingActions();
        SelectedAction selectedAction = new SelectedAction();

        /*
        Zbiór akcji maksymalizującech nie jest pusty
        Następnie w ifie sprawdzamy, czy wybrać akcję losowo, czy zachłannie
         */
        if (maximizingActions != null && maximizingActions.size() > 0) {
            Debuger.println("[STRATEGIA] Akcja będzie wybrana losowo lub zachłannie");
            Double generated = random.nextDouble();

            //Akcja będzie wybrana losowo
            if (generated > greedyActionProbability) {
                Debuger.println("[STRATEGIA] Akcja będzie wybrana losowo- efekt losowania");
                selectedAction = selectSingleFeatureRandomly(actionSpace);
                increaseTotalRandomAction();
            } /*
            Akcja będzie wybrana zachłannie (losowo z pośród maksymalizujących akcji)
            Uwaga - tutaj zostaje przekazana ta sama akcja (czyli TreeSet<Integer>), która została przekazana do metody w obiekcie HashMap<TreeSet<Integer>, Double> actionsValue
             */ 
            else {
                Debuger.println("[STRATEGIA] Akcja będzie wybrana zachłannie- efekt losowania");
                selectedAction = selectSingleActionRandomly(maximizingActions);
                increaseTotalGreedyAction();
            }
            /*
        Zbiór akcji maksymalizującech  jest pusty, więc akcję wybieramy losowo
             */
        } else {
            Debuger.println("[STRATEGIA] Akcja będzie wybrana losowo- zbiór akcji maksymalizujących pusty");
            selectedAction = selectSingleFeatureRandomly(actionSpace);
            increaseTotalRandomAction();
        }
        updateStatisticsByConsistentAction(selectedAction);
        updateGreedyAndAdditionProbability();
        return selectedAction;
    }

    

    public void setGreedyActionProbability(Double probability) {
        this.greedyActionProbability = probability;
        this.randomActionProbability = 1.0 - this.greedyActionProbability;
    }

    public void setGreedyActionProbabilityUpdate(Boolean greedyActionProbabilityUpdate) {
        this.greedyActionProbabilityUpdate = greedyActionProbabilityUpdate;
    }

    protected void increaseTotalGreedyAction(){
        totalGreedyAction++;
    }
    
    protected void increaseTotalRandomAction(){
        totalRandomAction++;
    }

    public Boolean getAdditionProbabilityGeneratorSwitch() {
        return additionProbabilityGeneratorSwitch;
    }

    public Long getStepsBeforeAdditionProbabilitySwitch() {
        return stepsBeforeAdditionProbabilitySwitch;
    }

    public ActionProbabilityGenerator getAdditionProbabilityGenerator() {
        return additionProbabilityGenerator;
    }

    public Boolean getGreedyActionProbabilityUpdate() {
        return greedyActionProbabilityUpdate;
    }

    public ActionProbabilityUpdater getGreedyActionProbabilityUpdater() {
        return greedyActionProbabilityUpdater;
    }

    public Double getGreedyActionProbability() {
        return greedyActionProbability;
    }

    public Double getRandomActionProbability() {
        return randomActionProbability;
    }

    public Integer getTotalGreedyAction() {
        return totalGreedyAction;
    }

    public Integer getTotalRandomAction() {
        return totalRandomAction;
    }

    public void setAdditionProbabilityGenerator(ActionProbabilityGenerator additionProbabilityGenerator) {
        this.additionProbabilityGenerator = additionProbabilityGenerator;
    }

    public void setGreedyActionProbabilityUpdater(ActionProbabilityUpdater greedyActionProbabilityUpdater) {
        this.greedyActionProbabilityUpdater = greedyActionProbabilityUpdater;
    }
    
    
    
    
    
}
