/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.planers;

import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;

/**
 *
 * @author Przemek
 */
public class PlannerBackToBestNoImprovement extends PlannerBackToBest {

    Integer noImprovementStepsBeforeBack = 1000;

    public PlannerBackToBestNoImprovement(FSProcess process) {
        super(process);
    }
    
    public void setNoImprovementStepsBeforeBackAsFractionOfProcessTimeSteps(Double fraction){
        this.noImprovementStepsBeforeBack = (int) Math.round((double) process.getTimeStepsNumber() * fraction);
    }
    
    @Override
    public Boolean backCondition() {
        if(process.getBestStateActionsCollector().getNoImprovementSeries() >= noImprovementStepsBeforeBack){
            return true;
        }
        return false;
    }

    public Integer getNoImprovementStepsBeforeBack() {
        return noImprovementStepsBeforeBack;
    }

    public void setNoImprovementStepsBeforeBack(Integer noImprovementStepsBeforeBack) {
        this.noImprovementStepsBeforeBack = noImprovementStepsBeforeBack;
    }
    
    
    
    
}
