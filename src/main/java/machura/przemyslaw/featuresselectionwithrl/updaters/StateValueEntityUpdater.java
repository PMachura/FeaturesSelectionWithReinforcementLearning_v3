/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.updaters;

import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessKeeper;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;
import machura.przemyslaw.featuresselectionwithrl.updaters.stepsizegenerators.StepSizeGenerator;
import machura.przemyslaw.featuresselectionwithrl.updaters.stepsizegenerators.StepSizeGeneratorActionUpdateNumberDivider;

/**
 *
 * @author Przemek
 */
public abstract class StateValueEntityUpdater {

    protected Double discountingCoefficient = 0.8;
    protected StepSizeGenerator stepSizeGenerator = new StepSizeGeneratorActionUpdateNumberDivider();

    public StateValueEntityUpdater() {
        this.discountingCoefficient = 0.8;
    }

    public StateValueEntityUpdater(double discountingCoefficient) {
        this.discountingCoefficient = discountingCoefficient;
    }
    
    public abstract Action update(StateActionEntityCollection stateActionEntityCollection, ProcessKeeper processKeeper);

    public double getDiscountingCoefficient() {
        return discountingCoefficient;
    }
   
    public StepSizeGenerator getStepSizeGenerator(){
        return this.stepSizeGenerator;
    }

    public void setDiscountingCoefficient(Double discountingCoefficient) {
        this.discountingCoefficient = discountingCoefficient;
    }

    public void setStepSizeGenerator(StepSizeGenerator stepSizeGenerator) {
        this.stepSizeGenerator = stepSizeGenerator;
    }
    
    
       
    
}
