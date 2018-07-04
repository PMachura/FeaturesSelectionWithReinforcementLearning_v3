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
public class ConstantProbabilityGenerator extends ActionProbabilityGenerator {

    public ConstantProbabilityGenerator(){
        this.coefficient = 0.7;
    }
    
    @Override
    public Double getMeanProbability() {
        return this.coefficient;
    }

    @Override
    public void switchCoefficient() {
        this.coefficient = 1.0 - this.coefficient;
    }

    @Override
    public Double generateProbability(Integer maxActionNumber, Integer performedActionNumber, Integer actionSeries) {
        return this.coefficient;
    }

    @Override
    public void setCoefficient(Double coefficient) {
        if(coefficient < this.minGeneratedValue){
            this.coefficient = this.minGeneratedValue;
        }else if(coefficient > this.maxGeneratedValue){
            this.coefficient = this.maxGeneratedValue;
        }else{
            this.coefficient = coefficient;
        }
    }
    
    
    
}
