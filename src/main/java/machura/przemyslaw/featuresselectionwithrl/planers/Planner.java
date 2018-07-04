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
public abstract class Planner {
    
    protected FSProcess process = null;
        
    public abstract void run() throws Exception;
}
