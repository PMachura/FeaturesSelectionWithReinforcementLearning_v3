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
public class PowerFunctionWithSeriesPunishmentProbabilityGenerator extends PowerFunctionProbabilityGenerator {

    public PowerFunctionWithSeriesPunishmentProbabilityGenerator(Double coefficient){
        this.coefficient = coefficient;
    }
    
    public PowerFunctionWithSeriesPunishmentProbabilityGenerator(){
        this.coefficient = 2.0;
    }
    
    /**
     * Aktualizacja prawdopodobieństwa akcji (np. prawdopodobieństwa dodania cechy) 
     * na bazie ilości wykonania tej akcji, co wskazywane jest przez obecny stan procesu (np. ilość już dodanych cech)
     * oraz na bazie długości serii - tzn. ilości wykonania tej akcji bez przerwy pod rząd, gdzie długość serii zmniejsza prawdopodobieństwo akcji
     */
    @Override
    public Double generateProbability(Integer maxActionNumber, Integer performedActionNumber, Integer actionSeries) {
        return super.generateProbability(maxActionNumber, performedActionNumber, actionSeries)*
        ( ( (double) maxActionNumber - (double) (actionSeries) ) / (double)(maxActionNumber) );
    }
   
}
