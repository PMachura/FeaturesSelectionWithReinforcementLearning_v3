/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.updaters.stepsizegenerators;

import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import weka.core.pmml.jaxbbindings.Coefficient;

/**
 *
 * @author Przemek
 */
public class StepSizeGeneratorActionUpdateNumberDivider extends StepSizeGenerator{

    @Override
    public Double generateStepSize(Integer actionUpdateNumber) {
        Double val = 1.0/(double)Math.pow((double) actionUpdateNumber, coefiicent);
        Debuger.println("[STEP SIZE GENERTOR]  Rozmiar kroku: " + val);
        return val;
    }
    
}
