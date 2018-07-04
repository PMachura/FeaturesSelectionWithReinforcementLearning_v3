/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import machura.przemyslaw.featuresselectionwithrl.planers.Planner;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerBackToBest;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcessWithPlanners;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedyManyFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedySingleFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.Strategy;

/**
 *
 * @author Przemek
 */
public class ProcessStatisticHolder {

    public Double executedTimeSteps = null;
    public Double executionTime = null;
    public Double classificationLearningTime = null;
    public Double classificationTestingTime = null;
    public Double maxNoImprovementSeries = null;
    public Double additionAction = null;
    public Double deletionAction = null;
    public Double simulatedAdditionAction = null;
    public Double simulatedDeletionAction = null;
    public Double totalGreedyAction = null;
    public Double totalRandomAction = null;
    public Double numberOfBackToBest = null;
    public Double numberOfFoundedStates = null;
    public Double wholeFeaturesSetClassificationEvaluation = null;
    public Double wholeFeaturesSetValue = null;
    public Double wholeFeaturesSetClassificationEvaluationByLearnData = null;
    public Double wholeFeaturesSetValueByLearnData = null;
    public Double wholeFeaturesSetLearnAndTestClassificationDifference = null;
    public Double wholeFeaturesSetLearnAndTestValueDifference = null;

    
    public void setWholeFeaturesSetLearnAndTestEvaluationDifference(){
        this.wholeFeaturesSetLearnAndTestClassificationDifference = this.wholeFeaturesSetClassificationEvaluationByLearnData - this.wholeFeaturesSetClassificationEvaluation;
        this.wholeFeaturesSetLearnAndTestValueDifference = this.wholeFeaturesSetValueByLearnData - this.wholeFeaturesSetValue;
    }

    public void addStatisitcs(FSProcessWithPlanners process) {
        executedTimeSteps += (double) process.getTimeStepsNumber();
        executionTime += (double) process.getProcessTimeWatch().totalTimeInSeconds();
        classificationLearningTime += (double) process.getClassificationTimeWatch().totalTimeInSeconds();
        classificationTestingTime += (double) process.getClassificationByTestDataTimeWatch().totalTimeInSeconds();
        maxNoImprovementSeries += (double) process.getBestStateActionsCollector().getMaxNoImprovementSeries();
        numberOfFoundedStates += (double) process.getStateActionEntityCollection().getEntities().size();
        
        wholeFeaturesSetClassificationEvaluation  += (double) process.getWholeFeaturesSetClassificationEvaluation();
        wholeFeaturesSetValue  += (double) process.getWholeFeaturesSetValue();
        wholeFeaturesSetClassificationEvaluationByLearnData  += (double) process.getWholeFeaturesSetClassificationEvaluationByLearnData();
        wholeFeaturesSetValueByLearnData  += (double) process.getWholeFeaturesSetValueByLearnData();

        Strategy strategy = process.getStrategy();
        additionAction += (double) strategy.getTotalAdditionAction();
        deletionAction += (double) strategy.getTotalDeletionAction();
        simulatedAdditionAction += (double) strategy.getSimulatedAddition();
        simulatedDeletionAction += (double) strategy.getSimulatedDeletion();
        if (strategy instanceof EGreedySingleFeatureStrategy) {
            EGreedySingleFeatureStrategy eGreedyStrategy = (EGreedySingleFeatureStrategy) strategy;
            totalGreedyAction += (double) eGreedyStrategy.getTotalGreedyAction();
            totalRandomAction += (double) eGreedyStrategy.getTotalRandomAction();
        } else if (strategy instanceof EGreedyManyFeatureStrategy) {
            EGreedyManyFeatureStrategy eGreedyStrategy = (EGreedyManyFeatureStrategy) strategy;
            totalGreedyAction += (double) eGreedyStrategy.getTotalGreedyAction();
            totalRandomAction += (double) eGreedyStrategy.getTotalRandomAction();
        }

        for (Planner planner
                : process.getPlanners()) {
            if (planner instanceof PlannerBackToBest) {
                numberOfBackToBest += (double) ((PlannerBackToBest) planner).getPerformedBacksToBest().size();
            }
        }
        
        setWholeFeaturesSetLearnAndTestEvaluationDifference();
    }

    public Boolean isNull(Double v1, Double v2) {
        if (v1 == null || v2 == null) {
            return true;
        }
        return false;
    }

    public void addStatisitcs(ProcessStatisticHolder psh) {
        
        if (psh.getWholeFeaturesSetClassificationEvaluation() != null) {
            if (wholeFeaturesSetClassificationEvaluation == null) {
                wholeFeaturesSetClassificationEvaluation = psh.getWholeFeaturesSetClassificationEvaluation();
            } else {
                wholeFeaturesSetClassificationEvaluation += psh.getWholeFeaturesSetClassificationEvaluation();
            }
        }
        if (psh.getWholeFeaturesSetValue() != null) {
            if (wholeFeaturesSetValue == null) {
                wholeFeaturesSetValue = psh.getWholeFeaturesSetValue();
            } else {
                wholeFeaturesSetValue += psh.getWholeFeaturesSetValue();
            }
        }
        if (psh.getWholeFeaturesSetClassificationEvaluationByLearnData() != null) {
            if (wholeFeaturesSetClassificationEvaluationByLearnData == null) {
                wholeFeaturesSetClassificationEvaluationByLearnData = psh.getWholeFeaturesSetClassificationEvaluationByLearnData();
            } else {
                wholeFeaturesSetClassificationEvaluationByLearnData += psh.getWholeFeaturesSetClassificationEvaluationByLearnData();
            }
        }
        if (psh.getWholeFeaturesSetValueByLearnData() != null) {
            if (wholeFeaturesSetValueByLearnData == null) {
                wholeFeaturesSetValueByLearnData = psh.getWholeFeaturesSetValueByLearnData();
            } else {
                wholeFeaturesSetValueByLearnData += psh.getWholeFeaturesSetValueByLearnData();
            }
        }
        
        
        
        
        
        if (psh.getExecutedTimeSteps() != null) {
            if (executedTimeSteps == null) {
                executedTimeSteps = psh.getExecutedTimeSteps();
            } else {
                executedTimeSteps += psh.getExecutedTimeSteps();
            }
        }
        if (psh.getExecutionTime() != null) {
            if (executionTime == null) {
                executionTime = psh.getExecutionTime();
            } else {
                executionTime += psh.getExecutionTime();
            }
        }
        if (psh.getNumberOfFoundedStates() != null) {
            if (numberOfFoundedStates == null) {
                numberOfFoundedStates = psh.getNumberOfFoundedStates();
            } else {
                numberOfFoundedStates += psh.getNumberOfFoundedStates();
            }
        }

        if (psh.getClassificationLearningTime() != null) {
            if (classificationLearningTime == null) {
                classificationLearningTime = psh.getClassificationLearningTime();
            } else {
                classificationLearningTime += psh.getClassificationLearningTime();
            }
        }
        if (psh.getClassificationTestingTime() != null) {
            if (classificationTestingTime == null) {
                classificationTestingTime = psh.getClassificationTestingTime();
            } else {
                classificationTestingTime += psh.getClassificationTestingTime();
            }
        }
        if (psh.getMaxNoImprovementSeries() != null) {
            if (maxNoImprovementSeries == null) {
                maxNoImprovementSeries = psh.getMaxNoImprovementSeries();
            } else {
                maxNoImprovementSeries += psh.getMaxNoImprovementSeries();
            }
        }
        if (psh.getAdditionAction() != null) {
            if (additionAction == null) {
                additionAction = psh.getAdditionAction();
            } else {
                additionAction += psh.getAdditionAction();
            }
        }
        if (psh.getDeletionAction() != null) {
            if (deletionAction == null) {
                deletionAction = psh.getDeletionAction();
            } else {
                deletionAction += psh.getDeletionAction();
            }
        }

        if (psh.getSimulatedAdditionAction() != null) {
            if (simulatedAdditionAction == null) {
                simulatedAdditionAction = psh.getSimulatedAdditionAction();
            } else {
                simulatedAdditionAction += psh.getSimulatedAdditionAction();
            }
        }
        if (psh.getSimulatedDeletionAction() != null) {
            if (simulatedDeletionAction == null) {
                simulatedDeletionAction = psh.getSimulatedDeletionAction();
            } else {
                simulatedDeletionAction += psh.getSimulatedDeletionAction();
            }
        }
        if (psh.getTotalGreedyAction() != null) {
            if (totalGreedyAction == null) {
                totalGreedyAction = psh.getTotalGreedyAction();
            } else {
                totalGreedyAction += psh.getTotalGreedyAction();
            }
        }
        if (psh.getTotalRandomAction() != null) {
            if (totalRandomAction == null) {
                totalRandomAction = psh.getTotalRandomAction();
            } else {
                totalRandomAction += psh.getTotalRandomAction();
            }
        }

        if (psh.getNumberOfBackToBest() != null) {
            if (numberOfBackToBest == null) {
                numberOfBackToBest = psh.getNumberOfBackToBest();
            } else {
                numberOfBackToBest += psh.getNumberOfBackToBest();
            }
        }
        
        setWholeFeaturesSetLearnAndTestEvaluationDifference();
    }

    public void setStatisitcs(FSProcessWithPlanners process) {
        executedTimeSteps = (double) process.getTimeStepsNumber();
        executionTime = (double) process.getProcessTimeWatch().totalTimeInSeconds();
        classificationLearningTime = (double) process.getClassificationTimeWatch().totalTimeInSeconds();
        classificationTestingTime = (double) process.getClassificationByTestDataTimeWatch().totalTimeInSeconds();
        maxNoImprovementSeries = (double) process.getBestStateActionsCollector().getMaxNoImprovementSeries();
        numberOfFoundedStates = (double) process.getStateActionEntityCollection().getEntities().size();

        wholeFeaturesSetClassificationEvaluation  = (double) process.getWholeFeaturesSetClassificationEvaluation();
        wholeFeaturesSetValue  = (double) process.getWholeFeaturesSetValue();
        wholeFeaturesSetClassificationEvaluationByLearnData  = (double) process.getWholeFeaturesSetClassificationEvaluationByLearnData();
        wholeFeaturesSetValueByLearnData  = (double) process.getWholeFeaturesSetValueByLearnData();
        
        
        Strategy strategy = process.getStrategy();
        additionAction = (double) strategy.getTotalAdditionAction();
        deletionAction = (double) strategy.getTotalDeletionAction();
        simulatedAdditionAction = (double) strategy.getSimulatedAddition();
        simulatedDeletionAction = (double) strategy.getSimulatedDeletion();
        if (strategy instanceof EGreedySingleFeatureStrategy) {
            EGreedySingleFeatureStrategy eGreedyStrategy = (EGreedySingleFeatureStrategy) strategy;
            totalGreedyAction = (double) eGreedyStrategy.getTotalGreedyAction();
            totalRandomAction = (double) eGreedyStrategy.getTotalRandomAction();
        } else if (strategy instanceof EGreedyManyFeatureStrategy) {
            EGreedyManyFeatureStrategy eGreedyStrategy = (EGreedyManyFeatureStrategy) strategy;
            totalGreedyAction = (double) eGreedyStrategy.getTotalGreedyAction();
            totalRandomAction = (double) eGreedyStrategy.getTotalRandomAction();
        }

        for (Planner planner : process.getPlanners()) {
            if (planner instanceof PlannerBackToBest) {
                numberOfBackToBest = (double) ((PlannerBackToBest) planner).getPerformedBacksToBest().size();
            }
        }
        
        setWholeFeaturesSetLearnAndTestEvaluationDifference();
    }

    public Double getExecutedTimeSteps() {
        return executedTimeSteps;
    }

    public void setExecutedTimeSteps(Double executedTimeSteps) {
        this.executedTimeSteps = executedTimeSteps;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }

    public Double getClassificationLearningTime() {
        return classificationLearningTime;
    }

    public void setClassificationLearningTime(Double classificationLearningTime) {
        this.classificationLearningTime = classificationLearningTime;
    }

    public Double getClassificationTestingTime() {
        return classificationTestingTime;
    }

    public void setClassificationTestingTime(Double classificationTestingTime) {
        this.classificationTestingTime = classificationTestingTime;
    }

    public Double getMaxNoImprovementSeries() {
        return maxNoImprovementSeries;
    }

    public void setMaxNoImprovementSeries(Double maxNoImprovementSeries) {
        this.maxNoImprovementSeries = maxNoImprovementSeries;
    }

    public Double getAdditionAction() {
        return additionAction;
    }

    public void setAdditionAction(Double additionAction) {
        this.additionAction = additionAction;
    }

    public Double getDeletionAction() {
        return deletionAction;
    }

    public void setDeletionAction(Double deletionAction) {
        this.deletionAction = deletionAction;
    }

    public Double getSimulatedAdditionAction() {
        return simulatedAdditionAction;
    }

    public void setSimulatedAdditionAction(Double simulatedAdditionAction) {
        this.simulatedAdditionAction = simulatedAdditionAction;
    }

    public Double getSimulatedDeletionAction() {
        return simulatedDeletionAction;
    }

    public void setSimulatedDeletionAction(Double simulatedDeletionAction) {
        this.simulatedDeletionAction = simulatedDeletionAction;
    }

    public Double getTotalGreedyAction() {
        return totalGreedyAction;
    }

    public void setTotalGreedyAction(Double totalGreedyAction) {
        this.totalGreedyAction = totalGreedyAction;
    }

    public Double getTotalRandomAction() {
        return totalRandomAction;
    }

    public void setTotalRandomAction(Double totalRandomAction) {
        this.totalRandomAction = totalRandomAction;
    }

    public Double getNumberOfBackToBest() {
        return numberOfBackToBest;
    }

    public void setNumberOfBackToBest(Double numberOfBackToBest) {
        this.numberOfBackToBest = numberOfBackToBest;
    }

    public Double getNumberOfFoundedStates() {
        return numberOfFoundedStates;
    }

    public void setNumberOfFoundedStates(Double numberOfFoundedStates) {
        this.numberOfFoundedStates = numberOfFoundedStates;
    }

    public Double getWholeFeaturesSetClassificationEvaluation() {
        return wholeFeaturesSetClassificationEvaluation;
    }

    public void setWholeFeaturesSetClassificationEvaluation(Double wholeFeaturesSetClassificationEvaluation) {
        this.wholeFeaturesSetClassificationEvaluation = wholeFeaturesSetClassificationEvaluation;
    }

    public Double getWholeFeaturesSetValue() {
        return wholeFeaturesSetValue;
    }

    public void setWholeFeaturesSetValue(Double wholeFeaturesSetValue) {
        this.wholeFeaturesSetValue = wholeFeaturesSetValue;
    }

    public Double getWholeFeaturesSetClassificationEvaluationByLearnData() {
        return wholeFeaturesSetClassificationEvaluationByLearnData;
    }

    public void setWholeFeaturesSetClassificationEvaluationByLearnData(Double wholeFeaturesSetClassificationEvaluationByLearnData) {
        this.wholeFeaturesSetClassificationEvaluationByLearnData = wholeFeaturesSetClassificationEvaluationByLearnData;
    }

    public Double getWholeFeaturesSetValueByLearnData() {
        return wholeFeaturesSetValueByLearnData;
    }

    public void setWholeFeaturesSetValueByLearnData(Double wholeFeaturesSetValueByLearnData) {
        this.wholeFeaturesSetValueByLearnData = wholeFeaturesSetValueByLearnData;
    }

    public Double getWholeFeaturesSetLearnAndTestClassificationDifference() {
        return wholeFeaturesSetLearnAndTestClassificationDifference;
    }

    public void setWholeFeaturesSetLearnAndTestClassificationDifference(Double wholeFeaturesSetLearnAndTestClassificationDifference) {
        this.wholeFeaturesSetLearnAndTestClassificationDifference = wholeFeaturesSetLearnAndTestClassificationDifference;
    }

    public Double getWholeFeaturesSetLearnAndTestValueDifference() {
        return wholeFeaturesSetLearnAndTestValueDifference;
    }

    public void setWholeFeaturesSetLearnAndTestValueDifference(Double wholeFeaturesSetLearnAndTestValueDifference) {
        this.wholeFeaturesSetLearnAndTestValueDifference = wholeFeaturesSetLearnAndTestValueDifference;
    }

    
    
}
