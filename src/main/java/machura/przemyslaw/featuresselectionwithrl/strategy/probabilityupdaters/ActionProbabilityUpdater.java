/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters;

/**
 *
 * @author Przemek
 */
public abstract class ActionProbabilityUpdater {
    
    Double coefficient = 1.0;
    Double maxGeneratedValue = 1.0;
    Double minGeneratedValue = 0.0;
       
    public abstract Double update(Double value);
    public abstract Double setCoefficientToObtainMaxGeneratedValueInGivenSteps(Double initialProbability, Integer stepsNumber);

    public Double getCoefficient() {
        return coefficient;
    }

    public Double getMaxGeneratedValue() {
        return maxGeneratedValue;
    }

    public void setMaxGeneratedValue(Double maxGeneratedValue) {
        this.maxGeneratedValue = maxGeneratedValue;
    }

    public Double getMinGeneratedValue() {
        return minGeneratedValue;
    }

    public void setMinGeneratedValue(Double minGeneratedValue) {
        this.minGeneratedValue = minGeneratedValue;
    }

   
    
    
}
