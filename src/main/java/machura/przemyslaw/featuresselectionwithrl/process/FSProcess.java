/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.helpers.DataCategory;
import machura.przemyslaw.featuresselectionwithrl.helpers.DataSetHelper;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.helpers.TimeWatch;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.ActionsSpace;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationTool;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationToolFactory;
import machura.przemyslaw.featuresselectionwithrl.planers.BestStateActionsCollector;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessKeeper;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessState;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessVariables;
import machura.przemyslaw.featuresselectionwithrl.process.interruptors.ProcessInterruptor;
import machura.przemyslaw.featuresselectionwithrl.returns.Reward;
import machura.przemyslaw.featuresselectionwithrl.returns.RewardGenerator;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntityCollection;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedySingleFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.Strategy;
import machura.przemyslaw.featuresselectionwithrl.updaters.QUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.StateValueEntityUpdater;
import weka.core.Instances;

/**
 *
 * @author Przemek
 */
public abstract class FSProcess {

    String description = "Proces";

    String dataFile = null;
    Instances data = null;
    Instances trainingData = null;
    Instances testingData = null;
    Double trainingDataPercentage = 0.7;
    Double wholeFeaturesSetClassificationEvaluation = null;
    Double wholeFeaturesSetValue = null;
    Double wholeFeaturesSetClassificationEvaluationByLearnData = null;
    Double wholeFeaturesSetValueByLearnData = null;

    Integer timeStep = 0;
    Integer timeStepsNumber = 100;

    Strategy strategy = new EGreedySingleFeatureStrategy();

    TimeWatch processTimeWatch = new TimeWatch();
    TimeWatch classificationTimeWatch = new TimeWatch();
    TimeWatch classificationByTestDataTimeWatch = new TimeWatch();

    ClassificationTool classificationTool = ClassificationToolFactory.createJ48ClassificationTool();

    StateActionEntityCollection stateActionEntityCollection = new StateActionEntityCollection();
    ProcessState processState = null;
    SelectedAction selectedAction = new SelectedAction();
    ActionsSpace actionSpace = null;
    Double classificationEvaluation = null;
    Reward reward = null;
    StateValueEntityUpdater entityUpdater = new QUpdater();

    StateActionEntity currentStateActionEntity = null;
    StateActionEntity nextStateActionEntity = null;
    Action currentAction = null;

    ProcessVariables processVariables = null;
    ProcessKeeper processKeeper = new ProcessKeeper();

    BestStateActionsCollector bestStateActionsCollector = new BestStateActionsCollector();

    RewardGenerator rewardGenerator = new RewardGenerator();

    ArrayList<ProcessInterruptor> processInterruptors = new ArrayList<ProcessInterruptor>();

    
    
    public FSProcess(Instances data, String dataFile) throws Exception {

        
        data.setClassIndex(data.numAttributes() - 1);
        this.data = data;
        Map<DataCategory, Instances> splitedData = DataSetHelper.holdOutClassProportionalSplit(data, trainingDataPercentage);
        this.trainingData = splitedData.get(DataCategory.TRAINING);
        this.testingData = splitedData.get(DataCategory.TEST);

        this.dataFile = dataFile;

        this.classificationTool.buildSubsetEvaluators(this.trainingData);

        this.processState = new ProcessState(data.numAttributes() - 1);

        this.actionSpace = new ActionsSpace(this.trainingData);
    }


    public Boolean checkInterruption() {
        for (ProcessInterruptor interruptor : processInterruptors) {
            if (interruptor.interrupt(this)) {
                return true;
            }
        }
        return false;
    }

    public abstract void run() throws Exception;

    public void setTimeStepsNumber(Integer timeStepsNumber) {
        this.timeStepsNumber = timeStepsNumber;
    }

    public void setMaxFeaturesInSubsetAsAFractionOfFeaturesInDataset(Double fraction) {
        if (fraction == null) {
            fraction = 0.2;
        }
        this.strategy.setMaxFeaturesInSubset((int) Math.round((double) (trainingData.numAttributes() - 1) * fraction));
    }
    
    public Double  performEvaluationForSplitedInputDataWithWholeFeatures() throws Exception{
         wholeFeaturesSetClassificationEvaluation = classificationTool.trainAndEvaluateClassifierByTestData(trainingData, testingData) / 100.0;
         wholeFeaturesSetValue = rewardGenerator.generateReward(wholeFeaturesSetClassificationEvaluation, data.numAttributes()-1, data.numAttributes() - 1).getValue();
         return wholeFeaturesSetClassificationEvaluation;
    }
    
    public Double  performEvaluationForSplitedInputDataWithWholeFeaturesByLearnSet() throws Exception{
         wholeFeaturesSetClassificationEvaluationByLearnData = classificationTool.trainAndEvaluateClassifierByTestData(trainingData, trainingData) / 100.0;
         wholeFeaturesSetValueByLearnData = rewardGenerator.generateReward(wholeFeaturesSetClassificationEvaluationByLearnData, data.numAttributes()-1, data.numAttributes() - 1).getValue();
         return wholeFeaturesSetClassificationEvaluationByLearnData;
    }

    public Double performClassificationEvaluation(ProcessState processState, StateActionEntity stateActionEntity) throws Exception {
        if (!stateActionEntity.isEvaluatedByClassifier()) {
            Debuger.println("[PROCES] Ewaluacja przez klasyfikator");
            classificationTimeWatch.start();
            classificationEvaluation = classificationTool.evaluateSubsetByWrapperSubsetEvaluator(processState.getBitState());
            stateActionEntity.setClassificationEvaluation(classificationEvaluation);
            classificationTimeWatch.addToTotalTime();
        } else {
            Debuger.println("[PROCES] Ewaluacja już znaleziona");
            classificationEvaluation = stateActionEntity.getClassificationEvaluation();
        }
        return classificationEvaluation;
    }

    
    
    public TreeSet<StateActionEntity> performEvaluationByTestData(TreeSet<StateActionEntity> entities) throws Exception {
        Debuger.println("[PROCES] Ewaluacja przez zbiór testowy");
        classificationByTestDataTimeWatch.start();
   
        classificationTool.buildClassifierSubsetEvaluator(trainingData);
        ProcessState processState = new ProcessState(trainingData.numAttributes() - 1);
        Double classificationEvaluation = 0.0;
        for (StateActionEntity entity : entities) {
            if(entity.getState().getClassificationEvaluationByTestSet() != null)
                continue;
            
            System.out.println("PETLA FOR");
            processState.setByState(entity.getState());
            classificationEvaluation = classificationTool.evaluateSubsetByTestDataWithClassifierSubsetEvaluator(processState.getBitState(), testingData);
            entity.getState().setClassificationEvaluationByTestSet(classificationEvaluation);          
            reward = rewardGenerator.generateReward(classificationEvaluation, data.numAttributes() - 1, entity.getFeaturesNumber());
            entity.getState().setValueByTestSet(reward.getValue());            
            entity.getState().computeTrainAndTestEvaluationDifference();
            
            System.out.println("EWALUACJA TESTOWY" + entity.getState().getClassificationEvaluationByTestSet());
            
        }
        classificationByTestDataTimeWatch.addToTotalTime();
        return entities;
    }
    
    public TreeSet<StateActionEntity> performEvaluationByLearnData(TreeSet<StateActionEntity> entities) throws Exception {
        Debuger.println("[PROCES] Ewaluacja przez zbiór uczący");
        classificationByTestDataTimeWatch.start();
   
        classificationTool.buildClassifierSubsetEvaluator(trainingData);
        ProcessState processState = new ProcessState(trainingData.numAttributes() - 1);
        Double classificationEvaluation = 0.0;
        for (StateActionEntity entity : entities) {
            if(entity.getState().getClassificationEvaluationByLearningSet() != null)
                continue;
            
            System.out.println("PETLA FOR");
            processState.setByState(entity.getState());
            classificationEvaluation = classificationTool.evaluateSubsetByTestDataWithClassifierSubsetEvaluator(processState.getBitState(), trainingData);
            entity.getState().setClassificationEvaluationByLearningSet(classificationEvaluation);          
            reward = rewardGenerator.generateReward(classificationEvaluation, data.numAttributes() - 1, entity.getFeaturesNumber());
            entity.getState().setValueByLearningSet(reward.getValue());            
            entity.getState().computeLearnAndTestEvaluationDifference();
            
            System.out.println("EWALUACJA UCZĄCY" + entity.getState().getClassificationEvaluationByLearningSet());
            
        }
        classificationByTestDataTimeWatch.addToTotalTime();
        return entities;
    }


    public void setClassificationTool(ClassificationTool classificationTool) throws Exception {
        this.classificationTool = classificationTool;
        this.classificationTool.buildSubsetEvaluators(trainingData);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTrainingData(Instances trainingData) {
        this.trainingData = trainingData;
    }

    public void setTimeStep(Integer timeStep) {
        this.timeStep = timeStep;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setProcessTimeWatch(TimeWatch processTimeWatch) {
        this.processTimeWatch = processTimeWatch;
    }

    public void setClassificationTimeWatch(TimeWatch classificationTimeWatch) {
        this.classificationTimeWatch = classificationTimeWatch;
    }

    public void setStateActionEntityCollection(StateActionEntityCollection stateActionEntityCollection) {
        this.stateActionEntityCollection = stateActionEntityCollection;
    }

    public void setSelectedAction(SelectedAction selectedAction) {
        this.selectedAction = selectedAction;
    }

    public void setActionSpace(ActionsSpace actionSpace) {
        this.actionSpace = actionSpace;
    }

    public void setClassificationEvaluation(Double classificationEvaluation) {
        this.classificationEvaluation = classificationEvaluation;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public void setProcessVariables(ProcessVariables processVariables) {
        this.processVariables = processVariables;
    }

    public void setProcessKeeper(ProcessKeeper processKeeper) {
        this.processKeeper = processKeeper;
    }

    public void setRewardGenerator(RewardGenerator rewardGenerator) {
        this.rewardGenerator = rewardGenerator;
    }

    public String getDescription() {
        return description;
    }

    public Instances getTrainingData() {
        return trainingData;
    }

    public ClassificationTool getClassificationTool() {
        return classificationTool;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public StateActionEntityCollection getStateActionEntityCollection() {
        return stateActionEntityCollection;
    }

    public SelectedAction getSelectedAction() {
        return selectedAction;
    }

    public ActionsSpace getActionSpace() {
        return actionSpace;
    }

    public Double getClassificationEvaluation() {
        return classificationEvaluation;
    }

    public Reward getReward() {
        return reward;
    }

    public TimeWatch getProcessTimeWatch() {
        return processTimeWatch;
    }

    public TimeWatch getClassificationTimeWatch() {
        return classificationTimeWatch;
    }

    public ProcessVariables getProcessVariables() {
        return processVariables;
    }

    public ProcessKeeper getProcessKeeper() {
        return processKeeper;
    }

    public Integer getTimeStep() {
        return timeStep;
    }

    public Integer getTimeStepsNumber() {
        return timeStepsNumber;
    }

    public RewardGenerator getRewardGenerator() {
        return rewardGenerator;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public void setProcessState(ProcessState processState) {
        this.processState = processState;
    }

    public StateValueEntityUpdater getEntityUpdater() {
        return entityUpdater;
    }

    public void setEntityUpdater(StateValueEntityUpdater entityUpdater) {
        this.entityUpdater = entityUpdater;
    }

    public StateActionEntity getCurrentStateActionEntity() {
        return currentStateActionEntity;
    }

    public void setCurrentStateActionEntity(StateActionEntity currentStateActionEntity) {
        this.currentStateActionEntity = currentStateActionEntity;
    }

    public StateActionEntity getNextStateActionEntity() {
        return nextStateActionEntity;
    }

    public void setNextStateActionEntity(StateActionEntity nextStateActionEntity) {
        this.nextStateActionEntity = nextStateActionEntity;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public BestStateActionsCollector getBestStateActionsCollector() {
        return bestStateActionsCollector;
    }

    public void setBestStateActionsCollector(BestStateActionsCollector bestStateActionsCollector) {
        this.bestStateActionsCollector = bestStateActionsCollector;
    }

    public Instances getTestingData() {
        return testingData;
    }

    public void setTestingData(Instances testingData) {
        this.testingData = testingData;
    }

    public Double getTrainingDataPercentage() {
        return trainingDataPercentage;
    }

    public void setTrainingDataPercentage(Double trainingDataPercentage) {
        this.trainingDataPercentage = trainingDataPercentage;
    }

    public ArrayList<ProcessInterruptor> getProcessInterruptors() {
        return processInterruptors;
    }

    public void setProcessInterruptors(ArrayList<ProcessInterruptor> processInterruptors) {
        this.processInterruptors = processInterruptors;
    }

    public TimeWatch getClassificationByTestDataTimeWatch() {
        return classificationByTestDataTimeWatch;
    }

    public void setClassificationByTestDataTimeWatch(TimeWatch classificationByTestDataTimeWatch) {
        this.classificationByTestDataTimeWatch = classificationByTestDataTimeWatch;
    }

    public Double getWholeFeaturesSetClassificationEvaluation() {
        return wholeFeaturesSetClassificationEvaluation;
    }

    public void setWholeFeaturesSetClassificationEvaluation(Double wholeFeaturesSetClassificationEvaluation) {
        this.wholeFeaturesSetClassificationEvaluation = wholeFeaturesSetClassificationEvaluation;
    }

    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
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
    
}
