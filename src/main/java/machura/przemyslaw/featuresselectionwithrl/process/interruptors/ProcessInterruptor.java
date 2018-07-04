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
public abstract class ProcessInterruptor {
    protected Boolean interruptionDone = false;
    public abstract Boolean interrupt(FSProcess process);

    public Boolean getInterruptionDone() {
        return interruptionDone;
    }

    public void setInterruptionDone(Boolean interruptionDone) {
        this.interruptionDone = interruptionDone;
    }
    
    
}
