/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators;

/**
 *
 * @author Przemek
 */

public abstract class ActionProbabilityGenerator {
    Double coefficient = 2.0;
    Double minGeneratedValue = 0.0;
    Double maxGeneratedValue = 1.0;
        
    public ActionProbabilityGenerator(){
        
    }
    
    public ActionProbabilityGenerator(Double coefficient){
        this.coefficient = coefficient;
    }
    
    public abstract Double getMeanProbability();
    
    public abstract void switchCoefficient();
    
    public abstract Double generateProbability(Integer maxActionNumber, Integer performedActionNumber, Integer actionSeries);

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Double getMinGeneratedValue() {
        return minGeneratedValue;
    }

    public void setMinGeneratedValue(Double minGeneratedValue) {
        this.minGeneratedValue = minGeneratedValue;
    }

    public Double getMaxGeneratedValue() {
        return maxGeneratedValue;
    }

    public void setMaxGeneratedValue(Double maxGeneratedValue) {
        this.maxGeneratedValue = maxGeneratedValue;
    }
    

    
    
    
    
}
