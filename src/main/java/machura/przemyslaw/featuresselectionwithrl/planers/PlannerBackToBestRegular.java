/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.planers;

import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;

/**
 *
 * @author Przemek
 */
public class PlannerBackToBestRegular extends PlannerBackToBest {

    Integer numberOfBackToBestInProcess = 10;
    Integer timeStepsBetweenBacktToBest = 10;
    

    public PlannerBackToBestRegular(FSProcess process) {
        super(process);
        computeTimeStepsBetweenBackToBest(process.getTimeStepsNumber(), numberOfBackToBestInProcess);
    }

    public void setTimeStepsBetweenBackToBestAsFractionOfProcessTimeSteps(Double fraction) {
        this.timeStepsBetweenBacktToBest = (int) Math.round((double) process.getTimeStepsNumber() * fraction);
        computeNumberOfBackToBestInProcess(process.getTimeStepsNumber(), this.timeStepsBetweenBacktToBest);
    }

    public void setTimeStepsBetweenBackToBest(Integer timeStepsBeforeBackToBest) {
        this.timeStepsBetweenBacktToBest = timeStepsBeforeBackToBest;
        computeNumberOfBackToBestInProcess(process.getTimeStepsNumber(), this.timeStepsBetweenBacktToBest);
    }

    public void setNumberOfBackTobestInProcessAsFractionOfProcessTimeSteps(Double fraction) {
        this.numberOfBackToBestInProcess = (int) Math.round((double) process.getTimeStepsNumber() * fraction);
        computeTimeStepsBetweenBackToBest(process.getTimeStepsNumber(), this.numberOfBackToBestInProcess);
    }

    public void setNumberOfBackToBestInProcess(Integer numberOfBackToBestInProcess) {
        this.numberOfBackToBestInProcess = numberOfBackToBestInProcess;
        computeTimeStepsBetweenBackToBest(process.getTimeStepsNumber(), this.numberOfBackToBestInProcess);
    }

    private void computeTimeStepsBetweenBackToBest(Integer stepNumbersInProcess, Integer numberOfBackToBestInProcess) {
        timeStepsBetweenBacktToBest = stepNumbersInProcess / numberOfBackToBestInProcess;
    }

    private void computeNumberOfBackToBestInProcess(Integer stepNumbersInProcess, Integer timeStepsBeforeBacktToBest) {
        numberOfBackToBestInProcess = stepNumbersInProcess / timeStepsBeforeBacktToBest;
    }

    @Override
    public Boolean backCondition() {
        if ((process.getTimeStep() + 1) % (timeStepsBetweenBacktToBest) == 0) {
            return true;
        }
        return false;
    }

    public Integer getTimeStepsBetweenBacktToBest() {
        return timeStepsBetweenBacktToBest;
    }

    public void setTimeStepsBetweenBacktToBest(Integer timeStepsBetweenBacktToBest) {
        this.timeStepsBetweenBacktToBest = timeStepsBetweenBacktToBest;
    }

    public Integer getNumberOfBackToBestInProcess(){
        return this.numberOfBackToBestInProcess;
    }
    
    

}
