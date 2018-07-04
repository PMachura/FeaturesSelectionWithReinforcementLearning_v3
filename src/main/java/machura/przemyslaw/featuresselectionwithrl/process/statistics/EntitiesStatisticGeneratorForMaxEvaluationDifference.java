/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import java.util.LinkedHashSet;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class EntitiesStatisticGeneratorForMaxEvaluationDifference extends EntitiesStatisticGenerator {
    
    @Override
    public String getName(){
        return "GenStatOMaxRoznicyWartosci";
    }
    
     @Override
     public EntitiesStatisticHolder generateStatisticsForBestClassifiedStates(FSProcess process) throws Exception {
        LinkedHashSet<StateActionEntity> entities = process.getStateActionEntityCollection().getEntities();
        EntitiesStatisticHolder statisticHolder = new EntitiesStatisticHolder();
        statisticHolder.setEntities(computeBestStatesByClassificationWithMaxDifference(entities));
        process.performEvaluationByTestData(statisticHolder.getEntities());
        computeWholeFeatureClassificationDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluation());
        computeWholeFeatureValueDifferent(statisticHolder.getEntities(),process.getWholeFeaturesSetValue());
        
        process.performEvaluationByLearnData(statisticHolder.getEntities());
        computeWholeFeatureClassificationByLearnSetDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluationByLearnData());
        computeWholeFeatureValueByLearnSetDifferent(statisticHolder.getEntities(),process.getWholeFeaturesSetValueByLearnData());
        
        generateWholeStatistics(statisticHolder);
        return statisticHolder;
    }

     @Override
    public EntitiesStatisticHolder generateStatisticsForBestValuedStates(FSProcess process) throws Exception {
        LinkedHashSet<StateActionEntity> entities = process.getStateActionEntityCollection().getEntities();
        EntitiesStatisticHolder statisticHolder = new EntitiesStatisticHolder();
        statisticHolder.setEntities(computeBestStatesByValueWithMaxDifferent(entities));
        process.performEvaluationByTestData(statisticHolder.getEntities());
        computeWholeFeatureClassificationDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluation());
        computeWholeFeatureValueDifferent(statisticHolder.getEntities(),process.getWholeFeaturesSetValue());
        
        process.performEvaluationByLearnData(statisticHolder.getEntities());
        computeWholeFeatureClassificationByLearnSetDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluationByLearnData());
        computeWholeFeatureValueByLearnSetDifferent(statisticHolder.getEntities(),process.getWholeFeaturesSetValueByLearnData());
        
        generateWholeStatistics(statisticHolder);

        return statisticHolder;
    }
    
     @Override
    public void generateStatistics(FSProcess process) throws Exception {
        this.process = process;
        this.bestClassifiedStatisticHolder = generateStatisticsForBestClassifiedStates(this.process);
        this.bestValuedStatisticHolder = generateStatisticsForBestValuedStates(this.process);
        this.wholeStatesStatisticHolder = generatePartialStatisticsForWholeStates(this.process);
    }
    
    @Override
    public void generateStatisticsForBestStates(FSProcess process) throws Exception {
        this.process = process;
        this.bestClassifiedStatisticHolder = generateStatisticsForBestClassifiedStates(this.process);
        this.bestValuedStatisticHolder = generateStatisticsForBestValuedStates(this.process);

    }
    
}
