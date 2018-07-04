/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.actions;

import java.util.Objects;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;

/**
 *
 * @author Przemek
 */
public class Action {

    TreeSet<Feature> addedFeatures;
    TreeSet<Feature> removedFeatures;
    Double value;
    TreeSet<Integer> timeSteps;
    Integer executionCount;  
    TreeSet<Integer> simulationTimeSteps;
    Integer simulationCount;
    TreeSet<Integer> updateTimeSteps;
    Integer updateCount;

    public Action() {
        this.addedFeatures = new TreeSet<Feature>();
        this.removedFeatures = new TreeSet<Feature>();
        this.value = 0.0;
        this.timeSteps = new TreeSet<Integer>();
        this.executionCount = 0;
        this.simulationTimeSteps = new TreeSet<Integer>();
        this.simulationCount = 0;
        this.updateTimeSteps = new TreeSet<Integer>();
        this.updateCount = 0;
    }

    public boolean addTimeStep(Integer timeStep) {
        boolean isAdded = timeSteps.add(timeStep);
        if (isAdded) {
            executionCount++;
        }
        
        return isAdded;
    }
    public boolean addSimulationTimeStep(Integer timeStep) {
        boolean isAdded = simulationTimeSteps.add(timeStep);
        if (isAdded) {
            simulationCount++;
        }
        
        return isAdded;
    }
    
    public boolean addUpdateTimeStep(Integer timeStep){
        boolean isAdded = updateTimeSteps.add(timeStep);
        if (isAdded) {
            updateCount++;
        }
        
        return isAdded;
    }
    
    public Action(SelectedAction selectedAction) {
        this();
        this.addedFeatures = new TreeSet<Feature>(selectedAction.getFeaturesToAdd());
        this.removedFeatures = new TreeSet<Feature>(selectedAction.getFeaturesToRemove());
    }

    public void increaseExecutionCount() {
        this.executionCount++;
    }
    
    public void increaseUpdateCount(){
        this.updateCount++;
    }

    public boolean isAction(SelectedAction selectedAction) {
        if (!addedFeatures.equals(selectedAction.getFeaturesToAdd())) {
            return false;
        }
        if (!removedFeatures.equals(selectedAction.getFeaturesToRemove())) {
            return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Action other = (Action) obj;
        if (!Objects.equals(this.addedFeatures, other.addedFeatures)) {
            return false;
        }
        if (!Objects.equals(this.removedFeatures, other.removedFeatures)) {
            return false;
        }
        return true;
    }

    public TreeSet<Feature> getAddedFeatures() {
        return addedFeatures;
    }

    public void setAddedFeatures(TreeSet<Feature> addedFeatures) {
        this.addedFeatures = addedFeatures;
    }

    public TreeSet<Feature> getRemovedFeatures() {
        return removedFeatures;
    }

    public void setRemovedFeatures(TreeSet<Feature> removedFeatures) {
        this.removedFeatures = removedFeatures;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TreeSet<Integer> getTimeSteps() {
        return timeSteps;
    }

    public Integer getExecutionCount() {
        return executionCount;
    }

    public void setTimeSteps(TreeSet<Integer> timeSteps) {
        this.timeSteps = timeSteps;
    }

    public void setExecutionCount(Integer executionCount) {
        this.executionCount = executionCount;
    }

    public TreeSet<Integer> getSimulationTimeSteps() {
        return simulationTimeSteps;
    }

    public Integer getSimulationCount() {
        return simulationCount;
    }

    public TreeSet<Integer> getUpdateTimeSteps() {
        return updateTimeSteps;
    }

    public void setUpdateTimeSteps(TreeSet<Integer> updateTimeSteps) {
        this.updateTimeSteps = updateTimeSteps;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }
    
    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        description.append("Akcja:");
        description.append("  ");

        description.append("Cechy dodane:");
        description.append(" ");
        description.append("[");
        for (Feature feature : addedFeatures) {
            description.append(feature);
            description.append(" ");
        }
        description.append("]");
        description.append(" ");
        description.append("Cechy usunięte: ");
        description.append(" ");
        description.append("[");
        for (Feature feature : removedFeatures) {
            description.append(feature);
            description.append(" ");
        }
        description.append("]");

        description.append("   ");
        description.append("Wartość: " + "[" + value + "]");

        return description.toString();
    }

}
