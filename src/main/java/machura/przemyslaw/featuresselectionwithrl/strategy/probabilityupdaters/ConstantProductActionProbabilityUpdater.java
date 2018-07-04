/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters;

import machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters.ActionProbabilityUpdater;

/**
 *
 * @author Przemek
 */
public class ConstantProductActionProbabilityUpdater extends ActionProbabilityUpdater{
    
    public ConstantProductActionProbabilityUpdater(){
        this.coefficient = 1.001;
    }
  
    @Override
    public Double update(Double value) {
        Double result = value * this.coefficient;
        if(result >= 1.0){
            return 1.0;
        }
        if(result < minGeneratedValue)
            return minGeneratedValue;
        
        if(result > maxGeneratedValue)
            return maxGeneratedValue;
        
        return result;
    }

    @Override
    public Double setCoefficientToObtainMaxGeneratedValueInGivenSteps(Double initialProbability, Integer stepsNumber) {
        this.coefficient = Math.pow((maxGeneratedValue/initialProbability), (double)(1.0 / (double)stepsNumber));
        return this.coefficient;
    }
}
