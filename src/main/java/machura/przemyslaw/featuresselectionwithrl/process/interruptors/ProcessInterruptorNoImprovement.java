/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.interruptors;

import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;

/**
 *
 * @author Przemek
 */
public class ProcessInterruptorNoImprovement extends ProcessInterruptor {

    protected Integer maxNoImprovementSeries = 10000;
    
    @Override
    public Boolean interrupt(FSProcess process) {
        if(process.getBestStateActionsCollector().getNoImprovementSeries() >= maxNoImprovementSeries){
            interruptionDone = true;
        }
        return interruptionDone;
    }
    

    public Integer getMaxNoImprovementSeries() {
        return maxNoImprovementSeries;
    }

    public void setMaxNoImprovementSeries(Integer maxNoImprovementSeries) {
        this.maxNoImprovementSeries = maxNoImprovementSeries;
    }
    
    
    
}
