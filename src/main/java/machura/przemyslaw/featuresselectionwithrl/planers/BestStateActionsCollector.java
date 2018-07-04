/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.planers;

import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class BestStateActionsCollector {

    Comparator<StateActionEntity> compareByStateValue = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getState().getValue() - o2.getState().getValue();
        if (o1.equals(o2)) {
            return 0;
        }
        if (diff > 0) {
            return 1;
        }
        return -1;

    };

    TreeSet<StateActionEntity> entities = new TreeSet<StateActionEntity>(compareByStateValue);
    Integer maxStoredEntities = 5;
    Double currentMinStateValue = 0.0;
    Double currentMaxStateValue = 0.0;
    Integer noImprovementSeries = 0;
    Integer maxNoImprovementSeries = 0;
    Random random = new Random();

    
    public Boolean updateMaxNoImprovementSeries(){
        if(noImprovementSeries > maxNoImprovementSeries){
            maxNoImprovementSeries = noImprovementSeries;
            return true;
        }
        return false;
    }
    
    public Boolean add(StateActionEntity entity) {
        if(maxStoredEntities.equals(0))
            return false;
        
        Boolean isAdded = false;
        Double entityValue = entity.getState().getValue();
        if (entityValue > currentMinStateValue) {
            if (entities.size() >= maxStoredEntities) {
                entities.remove(entities.first());
            }
            isAdded = entities.add(entity);
            
            if(entityValue > currentMaxStateValue){
                currentMaxStateValue = entityValue;
                noImprovementSeries = 0;
            }else{
                noImprovementSeries++;
                updateMaxNoImprovementSeries();
            }
            currentMinStateValue = entities.first().getState().getValue();
        } else if (entities.size() < maxStoredEntities) {
            isAdded = entities.add(entity);
            if(entityValue > currentMaxStateValue){
                currentMaxStateValue = entityValue;
                noImprovementSeries = 0;
            }else{
                noImprovementSeries++;
                updateMaxNoImprovementSeries();
            }
            currentMinStateValue = entities.first().getState().getValue();
        }
        return isAdded;
    }

    public StateActionEntity getRandomed() {
        Integer number = random.nextInt(entities.size());
        Integer count = 0;
        for (StateActionEntity entity : entities) {
            if (number.equals(count)) {
                return entity;
            }
            count++;
        }
        return entities.first();

    }

    public Comparator<StateActionEntity> getCompareByStateValue() {
        return compareByStateValue;
    }

    public void setCompareByStateValue(Comparator<StateActionEntity> compareByStateValue) {
        this.compareByStateValue = compareByStateValue;
    }

    public TreeSet<StateActionEntity> getEntities() {
        return entities;
    }

    public void setEntities(TreeSet<StateActionEntity> entities) {
        this.entities = entities;
    }

    public Integer getMaxStoredEntities() {
        return maxStoredEntities;
    }

    public void setMaxStoredEntities(Integer maxStoredEntities) {
        this.maxStoredEntities = maxStoredEntities;
    }

    public Double getCurrentMinStateValue() {
        return currentMinStateValue;
    }

    public void setCurrentMinStateValue(Double currentMinStateValue) {
        this.currentMinStateValue = currentMinStateValue;
    }

    public Double getCurrentMaxStateValue() {
        return currentMaxStateValue;
    }

    public void setCurrentMaxStateValue(Double currentMaxStateValue) {
        this.currentMaxStateValue = currentMaxStateValue;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Integer getNoImprovementSeries() {
        return noImprovementSeries;
    }

    public void setNoImprovementSeries(Integer noImprovementSeries) {
        this.noImprovementSeries = noImprovementSeries;
    }

    public Integer getMaxNoImprovementSeries() {
        return maxNoImprovementSeries;
    }

    public void setMaxNoImprovementSeries(Integer maxNoImprovementSeries) {
        this.maxNoImprovementSeries = maxNoImprovementSeries;
    }
    
    
    
    

}
