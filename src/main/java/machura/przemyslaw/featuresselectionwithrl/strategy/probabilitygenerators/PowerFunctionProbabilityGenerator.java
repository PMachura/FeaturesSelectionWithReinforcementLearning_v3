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
public class PowerFunctionProbabilityGenerator extends ActionProbabilityGenerator{
    
    public PowerFunctionProbabilityGenerator(Double coefficient){
       this.coefficient = coefficient;
    }
    
    public PowerFunctionProbabilityGenerator(){
        this.coefficient = 3.0;
    }
    
    
    public void switchCoefficient(){
        this.coefficient = 1.0/this.coefficient;
    }
    
    public Double getMeanProbability(){
        return  (100.0*(coefficient/(coefficient + 1.0))) / 100.0;
    }
    
    /**
     * Aktualizacja prawdopodobieństwa akcji (np. prawdopodobieństwa dodania cechy) 
     * na bazie ilości wykonania tej akcji, co wskazywane jest przez obecny stan procesu (np. ilość już dodanych cech)
     */
    @Override
    public Double generateProbability(Integer maxActionNumber, Integer performedActionNumber, Integer actionSeries) {
        //return 100.0 - ((100.0/(double)Math.pow(maxActionNumber,coefficient))*(double)Math.pow(performedActionNumber,coefficient));
        Double value = (100.0 - 100.0 * Math.pow((double) performedActionNumber / (double) maxActionNumber, coefficient)) / 100.0;
        if(value < this.minGeneratedValue){
            return this.minGeneratedValue;
        }else if(value > this.maxGeneratedValue){
            return this.maxGeneratedValue;
        }
        
        return value;
    }
    
    
}
