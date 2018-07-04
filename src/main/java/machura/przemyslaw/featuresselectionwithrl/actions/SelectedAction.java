/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.actions;

import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;

/**
 *
 * @author Przemek
 */
public class SelectedAction {
    private TreeSet<Feature> featuresToRemove;
    private TreeSet<Feature> featuresToAdd;
    private Integer timeStep;

    public SelectedAction(){
        this.featuresToAdd = new TreeSet<Feature>();
        this.featuresToRemove = new TreeSet<Feature>();
        this.timeStep = null;
    }
    
    public SelectedAction(Action action){
        this.featuresToAdd = new TreeSet<Feature>(action.getAddedFeatures());
        this.featuresToRemove = new TreeSet<Feature>(action.getRemovedFeatures());
    }
    
    public void addAll(SelectedAction selectedAction){
        addFeaturesToAdd(selectedAction.getFeaturesToAdd());
        addFeaturesToRemove(selectedAction.getFeaturesToRemove());
    }
    
    public void addFeaturesToAdd(TreeSet<Feature> featuresToAdd){
        this.featuresToAdd.addAll(featuresToAdd);
    }
    
    public void addFeaturesToRemove(TreeSet<Feature> featuresToRemove){
        this.featuresToRemove.addAll(featuresToRemove);
    }
    
    public void addFeatureToRemove(Feature feature){
        featuresToRemove.add(feature);
    }
    
    public void addFeatureToAdd(Feature feature){
        featuresToAdd.add(feature);
    }
    
    public int getFeaturesToAddAmount(){
        return featuresToAdd.size();
    }
    
    public int getFeaturesToRemoveAmount(){
        return featuresToRemove.size();
    }
    
    public TreeSet<Feature> getFeaturesToRemove() {
        return featuresToRemove;
    }

    public void setFeaturesToRemove(TreeSet<Feature> featuresToRemove) {
        this.featuresToRemove = featuresToRemove;
    }

    public TreeSet<Feature> getFeaturesToAdd() {
        return featuresToAdd;
    }

    public void setFeaturesToAdd(TreeSet<Feature> featuresToAdd) {
        this.featuresToAdd = featuresToAdd;
    }

    public Integer getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Integer timeStep) {
        this.timeStep = timeStep;
    }
    
    
       
    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        description.append("Wybrana akcja:");
        description.append("  ");
        
        description.append("Cechy do dodania:");
        description.append(" ");
        description.append("[");
        for(Feature feature : featuresToAdd){
           description.append(feature); 
           description.append(" ");
        }
        description.append("]");
        description.append(" ");
        description.append("Cechy do usunięcia: ");
        description.append(" ");
        description.append("[");
        for(Feature feature : featuresToRemove){
           description.append(feature); 
           description.append(" ");
        }
        description.append("]");       
        
        return description.toString();
    }
}
