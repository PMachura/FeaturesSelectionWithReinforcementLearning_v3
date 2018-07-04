/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.updaters.stepsizegenerators;

/**
 *
 * @author Przemek
 */
public abstract class StepSizeGenerator {
    protected Double coefiicent = 0.75;
    
    public abstract Double generateStepSize(Integer actionUpdateNumber);

    public Double getCoefiicent() {
        return coefiicent;
    }

    public void setCoefiicent(Double coefiicent) {
        this.coefiicent = coefiicent;
    }
    
    
    
}
