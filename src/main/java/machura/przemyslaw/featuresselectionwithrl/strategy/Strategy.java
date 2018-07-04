/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.strategy;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.ActionsSpace;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;

/**
 *
 * @author Przemek
 */
public abstract class Strategy {
    Integer simulatedAddition = 0;
    Integer simulatedDeletion = 0;
    Integer totalAdditionAction = 0;
    Integer totalDeletionAction = 0;
    Integer totalAdditionAndDeletion = 0;
    Integer additionSeries = 0;
    Integer deletionSeries = 0;   
    Integer maxFeaturesInSubset = 20;
    Integer numberOfFeaturesToSelect = 1; 
    Random random = new Random();
    
    

    public Strategy(Integer maxFeaturesInSubset) {
        this.maxFeaturesInSubset = maxFeaturesInSubset;
    }
    
    public Strategy(){
        
    }
    

    /**
     * Funkcja wybierająca losowo akcje z przekazanej listy (nazwa trochę
     * niefortunna- raczej powinno być selectSingleAction
     *
     * @param actionsList
     * @return
     */
    protected SelectedAction selectSingleActionRandomly(final ArrayList<Action> actionsList) {
        SelectedAction selectedAction = new SelectedAction();
        Action action = actionsList.get(random.nextInt(actionsList.size()));
        selectedAction.addFeaturesToAdd(action.getAddedFeatures());
        selectedAction.addFeaturesToRemove(action.getRemovedFeatures());

        return selectedAction;
    }
   
    /**
     * Funkcja służy do losowego wyboru cechy spośród przekazanych
     * @param features
     * @return
     * @throws Exception 
     */
    protected Feature selectSingleFeatureRandomly(TreeSet<Feature> features) throws Exception {
        Integer position = random.nextInt(features.size());
        int i = 0;
        for (Feature feature : features) {
            if (position.equals(i)) {
                return feature;
            }

            i++;
        }
        throw new Exception("Bład podczas losowania pojedynczej cechy");
    }
    
    
    public void updateSimulatedStatistics(SelectedAction selectedAction) {
        int featuresToAdd = selectedAction.getFeaturesToAddAmount();
        int featuresToRemove = selectedAction.getFeaturesToRemoveAmount();

        simulatedAddition += featuresToAdd;
        simulatedDeletion += featuresToRemove;

    }
    
    /**
     * @param selectedAction
     */
    public void updateStatistics(SelectedAction selectedAction) {
        int featuresToAdd = selectedAction.getFeaturesToAddAmount();
        int featuresToRemove = selectedAction.getFeaturesToRemoveAmount();

        if (featuresToAdd > 0 && featuresToRemove > 0) {
            totalAdditionAction += featuresToAdd;
            additionSeries += featuresToAdd;
            
            totalDeletionAction += featuresToRemove;
            deletionSeries += featuresToRemove;
        }else if (featuresToAdd > 0) {
            totalAdditionAction += featuresToAdd;
            additionSeries += featuresToAdd;
            deletionSeries = 0;
        } else if (featuresToRemove > 0) {
            totalDeletionAction += featuresToRemove;
            deletionSeries += featuresToRemove;
            additionSeries = 0;
        }
        
        totalAdditionAndDeletion = totalDeletionAction + totalAdditionAction;

    }
    

    public void updateSimulationStatisticsByConsistentAction(SelectedAction selectedAction) throws Exception {
        int featuresToAdd = selectedAction.getFeaturesToAddAmount();
        int featuresToRemove = selectedAction.getFeaturesToRemoveAmount();

        if(featuresToAdd > 0 && featuresToRemove > 0){
            throw new Exception("Wybrana akcja jest niespójna");
        }
        
        simulatedAddition += featuresToAdd;
        simulatedDeletion += featuresToRemove;
    }
    
    
    /**
     * Aktualizacja statystyk dla przypadku, gdzie akcja zawiera tylko dodanie
     * lub tylko usunięcie cech - jednej bądź wielu
     *
     * @param selectedAction
     */
    public void updateStatisticsByConsistentAction(SelectedAction selectedAction) throws Exception {
        int featuresToAdd = selectedAction.getFeaturesToAddAmount();
        int featuresToRemove = selectedAction.getFeaturesToRemoveAmount();

        if(featuresToAdd > 0 && featuresToRemove>0){
            throw new Exception("Wybrana akcja jest niespójna");
        }
        
        if (featuresToAdd > 0) {
            totalAdditionAction += featuresToAdd;
            additionSeries += featuresToAdd;
            deletionSeries = 0;
        } else if (featuresToRemove > 0) {
            totalDeletionAction += featuresToRemove;
            deletionSeries += featuresToRemove;
            additionSeries = 0;
        }
        
        totalAdditionAndDeletion = totalDeletionAction + totalAdditionAction;

    }
    
    public void updateGreedyAndAdditionProbability() throws Exception{
        
    }
    
    public void update(SelectedAction selectedAction){
        
    }
    
    protected Feature selectSingleFeatureFromAllowedToAddRandomly(ActionsSpace actionsSpace) throws Exception {
        return selectSingleFeatureRandomly((actionsSpace.getFeaturesAllowedToAdd()));
    }

    protected Feature selectSingleFeatureFromAllowedToRemoveRandomly(ActionsSpace actionsSpace) throws Exception {
        return selectSingleFeatureRandomly((actionsSpace.getFeaturesAllowedToRemove()));
    }
    
    
    public void setMaxFeaturesInSubset(Integer maxFeaturesInSubset) {
        this.maxFeaturesInSubset = maxFeaturesInSubset;
    }
    
    public abstract ArrayList<SelectedAction> simulateManyDifferentFeatureRandomSelection(ActionsSpace actionSpace, Integer actionsNumber) throws Exception;
    
    
    public abstract SelectedAction selectFeatures(final StateActionEntityCollection stateActionEntityCollection,
            final StateActionEntity stateActionEntity, 
            final ActionsSpace actionSpace) throws Exception;
    
    
    
    
    
    
    

    public Integer getTotalAdditionAction() {
        return totalAdditionAction;
    }

    public Integer getTotalDeletionAction() {
        return totalDeletionAction;
    }

    public Integer getTotalAdditionAndDeletion() {
        return totalAdditionAndDeletion;
    }

    public Integer getAdditionSeries() {
        return additionSeries;
    }

    public Integer getDeletionSeries() {
        return deletionSeries;
    }

    public Integer getMaxFeaturesInSubset() {
        return maxFeaturesInSubset;
    }

    public Integer getNumberOfFeaturesToSelect() {
        return numberOfFeaturesToSelect;
    }

    public Random getRandom() {
        return random;
    }

    public Integer getSimulatedAddition() {
        return simulatedAddition;
    }

    public void setSimulatedAddition(Integer simulatedAddition) {
        this.simulatedAddition = simulatedAddition;
    }

    public Integer getSimulatedDeletion() {
        return simulatedDeletion;
    }

    public void setSimulatedDeletion(Integer simulatedDeletion) {
        this.simulatedDeletion = simulatedDeletion;
    }

    public void setTotalAdditionAction(Integer totalAdditionAction) {
        this.totalAdditionAction = totalAdditionAction;
    }

    public void setTotalDeletionAction(Integer totalDeletionAction) {
        this.totalDeletionAction = totalDeletionAction;
    }

    public void setTotalAdditionAndDeletion(Integer totalAdditionAndDeletion) {
        this.totalAdditionAndDeletion = totalAdditionAndDeletion;
    }

    public void setAdditionSeries(Integer additionSeries) {
        this.additionSeries = additionSeries;
    }

    public void setDeletionSeries(Integer deletionSeries) {
        this.deletionSeries = deletionSeries;
    }

    public void setNumberOfFeaturesToSelect(Integer numberOfFeaturesToSelect) {
        this.numberOfFeaturesToSelect = numberOfFeaturesToSelect;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
    
    
    
   
}
