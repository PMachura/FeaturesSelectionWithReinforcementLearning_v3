/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.planers;

import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.helpers.ProcessOutputPrinter;

/**
 *
 * @author Przemek
 */
public abstract class PlannerBackToBest extends Planner {

    TreeSet<Integer> performedBacksToBest = new TreeSet<Integer>();
    
    public PlannerBackToBest(FSProcess process) {
        this.process = process;
    }
    

    public abstract Boolean backCondition();

    public void backToBest() {

        Debuger.println("[PLANNER BackToBest" + " " +  this.getClass().getSimpleName()+ "]" + "  " + "Powrót" );
        process.setCurrentStateActionEntity(process.getBestStateActionsCollector().getRandomed());
        process.getProcessState().setByState(process.getCurrentStateActionEntity().getState());
        process.getActionSpace().updateByState(process.getCurrentStateActionEntity().getState());
        Debuger.println("[PLANNER BackToBest" + " " +  this.getClass().getSimpleName() + "]" + "  " + ProcessOutputPrinter.print(process.getActionSpace()));
        Debuger.println("[PLANNER BackToBest" + " " +  this.getClass().getSimpleName() + "]"  + "  " + ProcessOutputPrinter.print(process.getProcessState()));

        process.getProcessKeeper().clear();
        performedBacksToBest.add(process.getTimeStep());

    }

    @Override
    public void run() { 
        Debuger.println("[PLANNER BackToBest" + " " +  this.getClass().getSimpleName()+ "]");
       if(backCondition()){
           backToBest();
       }
    }

    public TreeSet<Integer> getPerformedBacksToBest() {
        return performedBacksToBest;
    }

    public void setPerformedBacksToBest(TreeSet<Integer> performedBacksToBest) {
        this.performedBacksToBest = performedBacksToBest;
    } 

}
