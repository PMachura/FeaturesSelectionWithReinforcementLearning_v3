/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import java.util.ArrayList;
import java.util.Objects;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcessWithPlanners;
import static machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticCalculator.average;
import static machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticCalculator.findMax;
import static machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticCalculator.findMin;
import static machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticCalculator.variance;

/**
 *
 * @author Przemek
 */
public class ProcessStatisticGenerator {
    ArrayList<ProcessStatisticHolder> processStatisticHolderList = new ArrayList<ProcessStatisticHolder>();
    ProcessStatisticHolder manyProcessStatistickHolderForAverage = new ProcessStatisticHolder();
    ProcessStatisticHolder manyProcessStatistickHolderForVariance = new ProcessStatisticHolder();
    ProcessStatisticHolder manyProcessStatistickHolderForMin = new ProcessStatisticHolder();
    ProcessStatisticHolder manyProcessStatistickHolderForMax = new ProcessStatisticHolder();
    
    public void generateAverageStatistics(){
        manyProcessStatistickHolderForAverage.setWholeFeaturesSetLearnAndTestClassificationDifference(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        manyProcessStatistickHolderForAverage.setWholeFeaturesSetLearnAndTestValueDifference(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
         manyProcessStatistickHolderForAverage.setWholeFeaturesSetClassificationEvaluation(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluation)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setWholeFeaturesSetValue(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setWholeFeaturesSetClassificationEvaluationByLearnData(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluationByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setWholeFeaturesSetValueByLearnData(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValueByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        
        
        
        
        manyProcessStatistickHolderForAverage.setNumberOfFoundedStates(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfFoundedStates)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setExecutedTimeSteps(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutedTimeSteps)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setExecutionTime(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutionTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setClassificationLearningTime(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setClassificationLearningTime(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setClassificationTestingTime(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationTestingTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setMaxNoImprovementSeries(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getMaxNoImprovementSeries)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setAdditionAction(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setDeletionAction(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setSimulatedAdditionAction(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setSimulatedDeletionAction(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setTotalGreedyAction(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalGreedyAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setTotalRandomAction(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalRandomAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForAverage.setNumberOfBackToBest(average(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfBackToBest)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
    }
    
    
    public void generateVarianceStatistics(){
        
        manyProcessStatistickHolderForVariance.setWholeFeaturesSetLearnAndTestClassificationDifference(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        manyProcessStatistickHolderForVariance.setWholeFeaturesSetLearnAndTestValueDifference(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setWholeFeaturesSetClassificationEvaluation(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluation)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setWholeFeaturesSetValue(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setWholeFeaturesSetClassificationEvaluationByLearnData(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluationByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setWholeFeaturesSetValueByLearnData(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValueByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        
        
        manyProcessStatistickHolderForVariance.setNumberOfFoundedStates(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfFoundedStates)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setExecutedTimeSteps(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutedTimeSteps)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setExecutionTime(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutionTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setClassificationLearningTime(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setClassificationLearningTime(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setClassificationTestingTime(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationTestingTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setMaxNoImprovementSeries(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getMaxNoImprovementSeries)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setAdditionAction(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setDeletionAction(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setSimulatedAdditionAction(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setSimulatedDeletionAction(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setTotalGreedyAction(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalGreedyAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setTotalRandomAction(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalRandomAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForVariance.setNumberOfBackToBest(variance(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfBackToBest)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
    }
    
    public void generateMinStatistics(){
        manyProcessStatistickHolderForMin.setWholeFeaturesSetLearnAndTestClassificationDifference(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        manyProcessStatistickHolderForMin.setWholeFeaturesSetLearnAndTestValueDifference(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        
        manyProcessStatistickHolderForMin.setWholeFeaturesSetClassificationEvaluation(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluation)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setWholeFeaturesSetValue(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setWholeFeaturesSetClassificationEvaluationByLearnData(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluationByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setWholeFeaturesSetValueByLearnData(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValueByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        
        manyProcessStatistickHolderForMin.setNumberOfFoundedStates(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfFoundedStates)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        
        manyProcessStatistickHolderForMin.setExecutedTimeSteps(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutedTimeSteps)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setExecutionTime(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutionTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setClassificationLearningTime(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setClassificationLearningTime(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setClassificationTestingTime(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationTestingTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setMaxNoImprovementSeries(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getMaxNoImprovementSeries)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setAdditionAction(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setDeletionAction(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setSimulatedAdditionAction(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setSimulatedDeletionAction(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setTotalGreedyAction(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalGreedyAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setTotalRandomAction(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalRandomAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMin.setNumberOfBackToBest(findMin(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfBackToBest)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
    }
    
    public void generateMaxStatistics(){
        manyProcessStatistickHolderForMax.setWholeFeaturesSetLearnAndTestClassificationDifference(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        manyProcessStatistickHolderForMax.setWholeFeaturesSetLearnAndTestValueDifference(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setWholeFeaturesSetClassificationEvaluation(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluation)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setWholeFeaturesSetValue(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setWholeFeaturesSetClassificationEvaluationByLearnData(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetClassificationEvaluationByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setWholeFeaturesSetValueByLearnData(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getWholeFeaturesSetValueByLearnData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        
        manyProcessStatistickHolderForMax.setNumberOfFoundedStates(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfFoundedStates)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setExecutedTimeSteps(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutedTimeSteps)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setExecutionTime(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getExecutionTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setClassificationLearningTime(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setClassificationLearningTime(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationLearningTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setClassificationTestingTime(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getClassificationTestingTime)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setMaxNoImprovementSeries(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getMaxNoImprovementSeries)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setAdditionAction(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setDeletionAction(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setSimulatedAdditionAction(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedAdditionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setSimulatedDeletionAction(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getSimulatedDeletionAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setTotalGreedyAction(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalGreedyAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setTotalRandomAction(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getTotalRandomAction)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        
        manyProcessStatistickHolderForMax.setNumberOfBackToBest(findMax(processStatisticHolderList.stream()
                .map(ProcessStatisticHolder::getNumberOfBackToBest)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
    }
    
    
    public void generateStatistics(){
        generateAverageStatistics();
        generateVarianceStatistics();
        generateMinStatistics();
        generateMaxStatistics();
    }
    
    public void addStatisticHolderToList(ProcessStatisticHolder processStatisticHolder){
        this.processStatisticHolderList.add(processStatisticHolder);
    }
    public ProcessStatisticHolder createAndAddStatisticHolderToList(FSProcessWithPlanners process){
        ProcessStatisticHolder sh = new ProcessStatisticHolder();
        sh.setStatisitcs(process);
        this.processStatisticHolderList.add(sh);
        return sh;
    }
    
    public void resetProcessStatisticHolderList(){
        this.processStatisticHolderList = new ArrayList<ProcessStatisticHolder>();
    }

    public ArrayList<ProcessStatisticHolder> getProcessStatisticHolderList() {
        return processStatisticHolderList;
    }

    public void setProcessStatisticHolderList(ArrayList<ProcessStatisticHolder> processStatisticHolderList) {
        this.processStatisticHolderList = processStatisticHolderList;
    }

    public ProcessStatisticHolder getManyProcessStatistickHolderForAverage() {
        return manyProcessStatistickHolderForAverage;
    }

    public void setManyProcessStatistickHolderForAverage(ProcessStatisticHolder manyProcessStatistickHolderForAverage) {
        this.manyProcessStatistickHolderForAverage = manyProcessStatistickHolderForAverage;
    }

    public ProcessStatisticHolder getManyProcessStatistickHolderForVariance() {
        return manyProcessStatistickHolderForVariance;
    }

    public void setManyProcessStatistickHolderForVariance(ProcessStatisticHolder manyProcessStatistickHolderForVariance) {
        this.manyProcessStatistickHolderForVariance = manyProcessStatistickHolderForVariance;
    }

    public ProcessStatisticHolder getManyProcessStatistickHolderForMin() {
        return manyProcessStatistickHolderForMin;
    }

    public void setManyProcessStatistickHolderForMin(ProcessStatisticHolder manyProcessStatistickHolderForMin) {
        this.manyProcessStatistickHolderForMin = manyProcessStatistickHolderForMin;
    }

    public ProcessStatisticHolder getManyProcessStatistickHolderForMax() {
        return manyProcessStatistickHolderForMax;
    }

    public void setManyProcessStatistickHolderForMax(ProcessStatisticHolder manyProcessStatistickHolderForMax) {
        this.manyProcessStatistickHolderForMax = manyProcessStatistickHolderForMax;
    }
    
    

    
    
    
    
    
    
    
}
