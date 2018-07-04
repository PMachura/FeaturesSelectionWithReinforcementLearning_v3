/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class EntitiesStatisticGenerator {

    FSProcess process = null;

    Comparator<StateActionEntity> compareByClassificationEvaluation = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getClassificationEvaluation() - o2.getClassificationEvaluation();
        if (o1.equals(o2)) {
            return 0;
        }

        if (diff > 0) {
            return 1;
        }

        return -1;

    };

    Comparator<StateActionEntity> compareByStateValue = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getState().getValue() - o2.getState().getValue();
        if (o1.equals(o2)) {
            return 0;
        }
        if (diff > 0) {
            return 1;
        }
        return -1;

    };

    Comparator<StateActionEntity> compareByClassificationEvaluationByTestSet = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getState().getClassificationEvaluationByTestSet() - o2.getState().getClassificationEvaluationByTestSet();
        if (o1.equals(o2)) {
            return 0;
        }

        if (diff > 0) {
            return 1;
        }

        return -1;

    };

    Comparator<StateActionEntity> compareByStateValueByTestSet = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getState().getValueByTestSet() - o2.getState().getValueByTestSet();
        if (o1.equals(o2)) {
            return 0;
        }
        if (diff > 0) {
            return 1;
        }
        return -1;

    };

    /**
     * Klasyfikacja
     */
    EntitiesStatisticHolder bestClassifiedStatisticHolder = new EntitiesStatisticHolder();
    Double maxBestStateClassificationDifference = 0.01;
    Integer numberOfBestClassifiedStates = 30;

    /**
     * Wartość
     */
    EntitiesStatisticHolder bestValuedStatisticHolder = new EntitiesStatisticHolder();
    Double maxBestStateValueDifference = 0.01;
    Integer numberOfBestValuedStates = 30;

    EntitiesStatisticHolder wholeStatesStatisticHolder = new EntitiesStatisticHolder();

    public String getName() {
        return "MainStatisticGenerator";
    }

    public EntitiesStatisticHolder generatePartialStatistics(EntitiesStatisticHolder statisticHolder) {
        Debuger.println("[ENTITIES STATISTIC GENERATOR] Generowanie statystyk częściowych");
        statisticHolder.setAverageSubsetDifferentation(averageSubsetDifferentation(statisticHolder.getEntities()));
        statisticHolder.setAverageClassification(averageClassification(statisticHolder.getEntities()));
        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 1");
        statisticHolder.setVarianceClassification(varianceClassification(statisticHolder.getEntities(), statisticHolder.getAverageClassification()));
        statisticHolder.setAverageSubsetSize(averageSubsetSize(statisticHolder.getEntities()));
        statisticHolder.setVarianceSubsetSize(variancSubsetSize(statisticHolder.getEntities(), statisticHolder.getAverageSubsetSize()));
        statisticHolder.setMaxSubsetSize(maxSubsetSize(statisticHolder.getEntities()));
        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 2");
       

        statisticHolder.setAverageStateVisit(averageStateVisit(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateVisit(varianceStateVisit(statisticHolder.getEntities(), statisticHolder.getAverageStateVisit()));
        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 3");

        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 4");
        statisticHolder.setMaxStateVisit(maxStateVisit(statisticHolder.getEntities()));
        statisticHolder.setMaxStateDiscovery(maxStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setAverageStateDiscovery(averageStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateDiscovery(varianceStateDiscovery(statisticHolder.getEntities(), statisticHolder.getAverageStateDiscovery()));
        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 5");
        statisticHolder.setAverageStateValue(averageStatesValue(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateValue(varianceStatesValue(statisticHolder.getEntities(), statisticHolder.getAverageStateValue()));
        statisticHolder.setMinClassification(minClassification(statisticHolder.getEntities()));
        statisticHolder.setMinStateValue(minStateValue(statisticHolder.getEntities()));

        return statisticHolder;
    }

    public EntitiesStatisticHolder generateWholeStatistics(EntitiesStatisticHolder statisticHolder) {
        Debuger.println("[ENTITIES STATISTIC GENERATOR] Generowanie statystyk kompletnych");
        statisticHolder.setAverageSubsetDifferentation(averageSubsetDifferentation(statisticHolder.getEntities()));
        statisticHolder.setMaxClassification(statisticHolder.getEntities().last().getClassificationEvaluation());
        statisticHolder.setAverageClassification(averageClassification(statisticHolder.getEntities()));
        statisticHolder.setVarianceClassification(varianceClassification(statisticHolder.getEntities(), statisticHolder.getAverageClassification()));
        statisticHolder.setAverageSubsetSize(averageSubsetSize(statisticHolder.getEntities()));
        statisticHolder.setVarianceSubsetSize(variancSubsetSize(statisticHolder.getEntities(), statisticHolder.getAverageSubsetSize()));
        statisticHolder.setMaxSubsetSize(maxSubsetSize(statisticHolder.getEntities()));
        statisticHolder.setMinSubsetSize(minSubsetSize(statisticHolder.getEntities()));
        statisticHolder.setFeatureOccurenceIndexOrdered(featureOccurenceIndexOrdered(statisticHolder.getEntities()));
        statisticHolder.setAverageStateVisit(averageStateVisit(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateVisit(varianceStateVisit(statisticHolder.getEntities(), statisticHolder.getAverageStateVisit()));
        statisticHolder.setMaxStateValue(maxStateValue(statisticHolder.getEntities()));
        statisticHolder.setFirstStateVisit(firstStateVisit(statisticHolder.getEntities()));
        statisticHolder.setAverageFirstStateVisit(averageFirstStateVisit(statisticHolder.getEntities()));
        statisticHolder.setVarianceFirstStateVisit(varianceFirstStateVisit(statisticHolder.getEntities(), statisticHolder.getAverageFirstStateVisit()));
        statisticHolder.setAverageStateDiscovery(averageStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateDiscovery(varianceStateDiscovery(statisticHolder.getEntities(), statisticHolder.getAverageStateDiscovery()));
        statisticHolder.setFirstStateDiscovery(firstStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setAverageFirstStateDiscovery(averageFirstStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setVarianceFirstStateDiscovery(varianceFirstStateDiscovery(statisticHolder.getEntities(), statisticHolder.getAverageFirstStateDiscovery()));
        statisticHolder.setAverageStateValue(averageStatesValue(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateValue(varianceStatesValue(statisticHolder.getEntities(), statisticHolder.getAverageStateValue()));

        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 1");

        statisticHolder.setAverageStateValueByTestData(averageStatesValueByTestData(statisticHolder.getEntities()));
        statisticHolder.setVarianceStateValueByTestData(varianceStatesValueByTestData(statisticHolder.getEntities(), statisticHolder.getAverageStateValueByTestData()));
        statisticHolder.setAverageClassificationByTestData(averageClassificationByTestData(statisticHolder.getEntities()));
        statisticHolder.setVarianceClassificationByTestData(varianceClassificationByTestData(statisticHolder.getEntities(), statisticHolder.getAverageClassificationByTestData()));
        statisticHolder.setAverageTestAndTrainClassificationDifference(averageTestAndTrainStateClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceTestAndTrainClassificationDifference(varianceTestAndTrainClassificationDifference(statisticHolder.getEntities(), statisticHolder.getAverageTestAndTrainClassificationDifference()));
        statisticHolder.setAverageTestAndTrainStateValueDifference(averageTestAndTrainStateValueDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceTestAndTrainStateValueDifference(varianceTestAndTrainStateValueDifference(statisticHolder.getEntities(), statisticHolder.getAverageTestAndTrainStateValueDifference()));
        statisticHolder.setMaxClassificationByTestData(maxClassificationByTestData(statisticHolder.getEntities()));
        statisticHolder.setMaxStateValueByTestData(maxStateValueByTestData(statisticHolder.getEntities()));
        statisticHolder.setMaxTestAndTrainStateValueDifference(maxTestAndTrainStateValueDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxTestAndTrainClassificationDifference(maxTestAndTrainClassificationDifference(statisticHolder.getEntities()));

        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 2");

        statisticHolder.setMinClassification(minClassification(statisticHolder.getEntities()));
        statisticHolder.setMinStateValue(minStateValue(statisticHolder.getEntities()));

        statisticHolder.setMinClassificationByTestData(minClassificationByTestData(statisticHolder.getEntities()));
        statisticHolder.setMinStateValueByTestData(minStateValueByTestData(statisticHolder.getEntities()));

        statisticHolder.setMinTestAndTrainClassificationDifference(minTestAndTrainClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setMinTestAndTrainStateValueDifference(minTestAndTrainStateValueDifference(statisticHolder.getEntities()));

        statisticHolder.setMinWholeFeaturesSetClassificationDifference(minWholeFeaturesSetClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxWholeFeaturesSetClassificationDifference(maxWholeFeaturesSetClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setAverageWholeFeaturesSetClassificationDifference(averageWholeFeaturesSetClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceWholeFeaturesSetClassificationDifference(varianceWholeFeaturesSetClassificationDifference(statisticHolder.getEntities(), statisticHolder.getAverageWholeFeaturesSetClassificationDifference()));

        statisticHolder.setMinWholeFeaturesSetValueDifference(minWholeFeaturesSetValueDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxWholeFeaturesSetValueDifference(maxWholeFeaturesSetValueDifference(statisticHolder.getEntities()));
        statisticHolder.setAverageWholeFeaturesSetValueDifference(averageWholeFeaturesSetValueDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceWholeFeaturesSetValueDifference(varianceWholeFeaturesSetValueDifference(statisticHolder.getEntities(), statisticHolder.getAverageWholeFeaturesSetValueDifference()));

        statisticHolder.setMinStateVisit(minStateVisit(statisticHolder.getEntities()));
        statisticHolder.setMinStateDiscovery(minStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setMaxStateVisit(maxStateVisit(statisticHolder.getEntities()));
        statisticHolder.setMaxStateDiscovery(maxStateDiscovery(statisticHolder.getEntities()));

        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 3");

        statisticHolder.setLastStateVisit(lastStateVisit(statisticHolder.getEntities()));
        statisticHolder.setAverageLastStateVisit(averageLastStateVisit(statisticHolder.getEntities()));
        statisticHolder.setVarianceLastStateVisit(averageLastStateVisit(statisticHolder.getEntities()));

        statisticHolder.setLastStateDiscovery(lastStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setAverageLastStateDiscovery(averageLastStateDiscovery(statisticHolder.getEntities()));
        statisticHolder.setVarianceLastStateDiscovery(averageLastStateDiscovery(statisticHolder.getEntities()));

        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 4");

        statisticHolder.setBestClassifiedState(findBestEntity(statisticHolder.getEntities(), compareByClassificationEvaluation));
        statisticHolder.setBestValuedState(findBestEntity(statisticHolder.getEntities(), compareByStateValue));
        statisticHolder.setBestClassifiedByTestSetState(findBestEntity(statisticHolder.getEntities(), compareByClassificationEvaluationByTestSet));
        statisticHolder.setBestValuedByTestSetState(findBestEntity(statisticHolder.getEntities(), compareByStateValueByTestSet));

        Debuger.println("[ENTITIES STATISTIC GENERATOR] Postęp 5");
        statisticHolder.setMinWholeFeaturesSetClassificationByLearningSetDifference(minWholeFeaturesSetClassificationByLearningSetDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxWholeFeaturesSetClassificationByLearningSetDifference(maxWholeFeaturesSetClassificationByLearningSetDifference(statisticHolder.getEntities()));
        statisticHolder.setAverageWholeFeaturesSetClassificationByLearningSetDifference(averageWholeFeaturesSetClassificationByLearningSetDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceWholeFeaturesSetClassificationByLearningSetDifference(varianceWholeFeaturesSetClassificationByLearningSetDifference(statisticHolder.getEntities(), statisticHolder.getAverageWholeFeaturesSetClassificationByLearningSetDifference()));

        statisticHolder.setMinWholeFeaturesSetValueByLearningSetDifference(minWholeFeaturesSetValueByLearningSetDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxWholeFeaturesSetValueByLearningSetDifference(maxWholeFeaturesSetValueByLearningSetDifference(statisticHolder.getEntities()));
        statisticHolder.setAverageWholeFeaturesSetValueByLearningSetDifference(averageWholeFeaturesSetValueByLearningSetDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceWholeFeaturesSetValueByLearningSetDifference(varianceWholeFeaturesSetValueByLearningSetDifference(statisticHolder.getEntities(), statisticHolder.getAverageWholeFeaturesSetValueByLearningSetDifference()));

        statisticHolder.setMinValueByLearningSet(minValueByLearningSet(statisticHolder.getEntities()));
        statisticHolder.setMaxValueByLearningSet(maxValueByLearningSet(statisticHolder.getEntities()));
        statisticHolder.setAverageValueByLearningSet(averageValueByLearningSet(statisticHolder.getEntities()));
        statisticHolder.setVarianceValueByLearningSet(varianceValueByLearningSet(statisticHolder.getEntities(), statisticHolder.getAverageValueByLearningSet()));

        statisticHolder.setMinClassificationEvaluationByLearningSet(minClassificationEvaluationByLearningSet(statisticHolder.getEntities()));
        statisticHolder.setMaxClassificationEvaluationByLearningSet(maxClassificationEvaluationByLearningSet(statisticHolder.getEntities()));
        statisticHolder.setAverageClassificationEvaluationByLearningSet(averageClassificationEvaluationByLearningSet(statisticHolder.getEntities()));
        statisticHolder.setVarianceClassificationEvaluationByLearningSet(varianceClassificationEvaluationByLearningSet(statisticHolder.getEntities(), statisticHolder.getAverageClassificationEvaluationByLearningSet()));

        statisticHolder.setMinLearnAndTestClassificationDifference(minLearnAndTestClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxLearnAndTestClassificationDifference(maxLearnAndTestClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setAverageLearnAndTestClassificationDifference(averageLearnAndTestClassificationDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceLearnAndTestClassificationDifference(varianceLearnAndTestClassificationDifference(statisticHolder.getEntities(), statisticHolder.getAverageLearnAndTestClassificationDifference()));

        statisticHolder.setMinLearnAndTestValueDifference(minLearnAndTestValueDifference(statisticHolder.getEntities()));
        statisticHolder.setMaxLearnAndTestValueDifference(maxLearnAndTestValueDifference(statisticHolder.getEntities()));
        statisticHolder.setAverageLearnAndTestValueDifference(averageLearnAndTestValueDifference(statisticHolder.getEntities()));
        statisticHolder.setVarianceLearnAndTestValueDifference(varianceLearnAndTestValueDifference(statisticHolder.getEntities(), statisticHolder.getAverageLearnAndTestValueDifference()));

        
        statisticHolder.setBestNotOverfittedStateByValue(bestNotOverfittedStateByValue(statisticHolder.getEntities()));
        statisticHolder.setBestNotOverfittedStateByClassification(bestNotOverfittedStateByClassification(statisticHolder.getEntities()));
        
        return statisticHolder;
    }

    public EntitiesStatisticHolder generateStatisticsForBestClassifiedStates(FSProcess process) throws Exception {
        LinkedHashSet<StateActionEntity> entities = process.getStateActionEntityCollection().getEntities();
        EntitiesStatisticHolder statisticHolder = new EntitiesStatisticHolder();
        statisticHolder.setEntities(computeBestStatesByClassificationWithMaxDifference(entities));

        process.performEvaluationByTestData(statisticHolder.getEntities());
        computeWholeFeatureClassificationDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluation());
        computeWholeFeatureValueDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetValue());

        process.performEvaluationByLearnData(statisticHolder.getEntities());
        computeWholeFeatureClassificationByLearnSetDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluationByLearnData());
        computeWholeFeatureValueByLearnSetDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetValueByLearnData());

        generateWholeStatistics(statisticHolder);
        return statisticHolder;
    }

    public EntitiesStatisticHolder generateStatisticsForBestValuedStates(FSProcess process) throws Exception {
        LinkedHashSet<StateActionEntity> entities = process.getStateActionEntityCollection().getEntities();
        EntitiesStatisticHolder statisticHolder = new EntitiesStatisticHolder();
        statisticHolder.setEntities(computeBestStatesByValueWithMaxDifferent(entities));

        process.performEvaluationByTestData(statisticHolder.getEntities());
        computeWholeFeatureClassificationDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluation());
        computeWholeFeatureValueDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetValue());

        process.performEvaluationByLearnData(statisticHolder.getEntities());
        computeWholeFeatureClassificationByLearnSetDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetClassificationEvaluationByLearnData());
        computeWholeFeatureValueByLearnSetDifferent(statisticHolder.getEntities(), process.getWholeFeaturesSetValueByLearnData());

        generateWholeStatistics(statisticHolder);

        return statisticHolder;
    }

    public EntitiesStatisticHolder generatePartialStatisticsForWholeStates(FSProcess process) {
        LinkedHashSet<StateActionEntity> entities = process.getStateActionEntityCollection().getEntities();
        EntitiesStatisticHolder statisticHolder = new EntitiesStatisticHolder();
        statisticHolder.setEntities(new TreeSet(entities));
        generatePartialStatistics(statisticHolder);

        return statisticHolder;
    }

    public void generateStatistics(FSProcess process) throws Exception {
        this.process = process;
        this.bestClassifiedStatisticHolder = generateStatisticsForBestClassifiedStates(this.process);
        this.bestValuedStatisticHolder = generateStatisticsForBestValuedStates(this.process);
        this.wholeStatesStatisticHolder = generatePartialStatisticsForWholeStates(this.process);
    }

    public void generateStatisticsForBestStates(FSProcess process) throws Exception {
        this.process = process;
        this.bestClassifiedStatisticHolder = generateStatisticsForBestClassifiedStates(this.process);
        this.bestValuedStatisticHolder = generateStatisticsForBestValuedStates(this.process);
    }

    public void computeWholeFeatureClassificationDifferent(TreeSet<StateActionEntity> givenEntities, Double classificationEvaluationForWholeFeaturesSet) throws Exception {
        for (StateActionEntity entity : givenEntities) {
            entity.getState().computeWholeFeaturesClassificationDifference(classificationEvaluationForWholeFeaturesSet);
        }
    }

    public void computeWholeFeatureValueDifferent(TreeSet<StateActionEntity> givenEntities, Double valueForWholeFeaturesSet) throws Exception {
        for (StateActionEntity entity : givenEntities) {
            entity.getState().computeWholeFeaturesValueDifference(valueForWholeFeaturesSet);
        }
    }

    public void computeWholeFeatureClassificationByLearnSetDifferent(TreeSet<StateActionEntity> givenEntities, Double classificationEvaluationForWholeFeaturesSetByLearnSet) throws Exception {
        for (StateActionEntity entity : givenEntities) {
            entity.getState().computeWholeFeaturesSetClassificationByLearningSetDifference(classificationEvaluationForWholeFeaturesSetByLearnSet);
        }
    }

    public void computeWholeFeatureValueByLearnSetDifferent(TreeSet<StateActionEntity> givenEntities, Double valueForWholeFeaturesSetByLearnSet) throws Exception {
        for (StateActionEntity entity : givenEntities) {
            entity.getState().computeWholeFeaturesSetValueByLearningSetDifference(valueForWholeFeaturesSetByLearnSet);
        }
    }

    public TreeSet<StateActionEntity> comupteBestStatesByValueForGivenStatesNumber(LinkedHashSet<StateActionEntity> givenEntities) {
        return comupteBestStatesByValueForGivenStatesNumber(givenEntities, null);
    }

    public TreeSet<StateActionEntity> comupteBestStatesByValueForGivenStatesNumber(LinkedHashSet<StateActionEntity> givenEntities, Integer statesNumber) {
        if (statesNumber != null && statesNumber > 0) {
            numberOfBestValuedStates = statesNumber;
        }

        TreeSet<StateActionEntity> selected = new TreeSet<StateActionEntity>(compareByStateValue);
        Double currentMinStateValue = -Double.MAX_VALUE;
        Double currentMaxStateValue = -Double.MAX_VALUE;

        for (StateActionEntity entity : givenEntities) {
            Double entityValue = entity.getState().getValue();
            if (entityValue > currentMinStateValue) {
                if (selected.size() >= numberOfBestValuedStates) {
                    selected.remove(selected.first());
                }
                selected.add(entity);

                if (entityValue > currentMaxStateValue) {
                    currentMaxStateValue = entityValue;
                }

                currentMinStateValue = selected.first().getState().getValue();
            } else if (selected.size() < numberOfBestValuedStates) {
                selected.add(entity);
                if (entityValue > currentMaxStateValue) {
                    currentMaxStateValue = entityValue;
                }
                currentMinStateValue = selected.first().getState().getValue();
            }
        }

        return selected;
    }

    public TreeSet<StateActionEntity> comupteBestStatesByClassificationForGivenStatesNumber(LinkedHashSet<StateActionEntity> givenEntities) {
        return comupteBestStatesByClassificationForGivenStatesNumber(givenEntities, null);
    }

    public TreeSet<StateActionEntity> comupteBestStatesByClassificationForGivenStatesNumber(LinkedHashSet<StateActionEntity> givenEntities, Integer statesNumber) {
        if (statesNumber != null && statesNumber > 0) {
            numberOfBestClassifiedStates = statesNumber;
        }

        TreeSet<StateActionEntity> selected = new TreeSet<StateActionEntity>(compareByClassificationEvaluation);
        Double currentMinStateValue = -Double.MAX_VALUE;
        Double currentMaxStateValue = -Double.MAX_VALUE;

        for (StateActionEntity entity : givenEntities) {
            Double entityValue = entity.getState().getClassificationEvaluation();
            if (entityValue > currentMinStateValue) {
                if (selected.size() >= numberOfBestClassifiedStates) {
                    selected.remove(selected.first());
                }
                selected.add(entity);

                if (entityValue > currentMaxStateValue) {
                    currentMaxStateValue = entityValue;
                }

                currentMinStateValue = selected.first().getState().getValue();
            } else if (selected.size() < numberOfBestClassifiedStates) {
                selected.add(entity);
                if (entityValue > currentMaxStateValue) {
                    currentMaxStateValue = entityValue;
                }
                currentMinStateValue = selected.first().getState().getValue();
            }
        }

        return selected;
    }

    public TreeSet<StateActionEntity> computeBestStatesByValueWithMaxDifferent(LinkedHashSet<StateActionEntity> entities) {
        return computeBestStatesByValueWithMaxDifference(entities, null);
    }

    public TreeSet<StateActionEntity> computeBestStatesByValueWithMaxDifference(LinkedHashSet<StateActionEntity> entities, Double difference) {

        if (difference != null) {
            maxBestStateClassificationDifference = difference;
        }

        TreeSet<StateActionEntity> selected = new TreeSet<StateActionEntity>(compareByStateValue);

        Double max = -Double.MAX_VALUE;
        for (StateActionEntity save : entities) {
            if (!save.getState().equals(null) && !save.getState().getValue().equals(null)) {
                if (save.getState().getValue() > max) {
                    max = save.getState().getValue();
                    ArrayList<StateActionEntity> toRemove = new ArrayList<StateActionEntity>();
                    for (StateActionEntity bestEntity : selected) {
                        if (abs(bestEntity.getState().getValue() - max) > maxBestStateValueDifference) {
                            toRemove.add(bestEntity);
                        }
                    }
                    selected.removeAll(toRemove);
                    selected.add(save);
                } else if (abs(save.getState().getValue() - max) < maxBestStateValueDifference) {
                    selected.add(save);
                }
            }
        }
        bestValuedStatisticHolder.setEntities(selected);
        return selected;
    }

    public TreeSet<StateActionEntity> computeBestStatesByClassificationWithMaxDifference(LinkedHashSet<StateActionEntity> entities) {
        return computeBestStatesByClassificationWithMaxDifference(entities, null);
    }

    public TreeSet<StateActionEntity> computeBestStatesByClassificationWithMaxDifference(LinkedHashSet<StateActionEntity> entities, Double difference) {
        if (difference != null) {
            maxBestStateClassificationDifference = difference;
        }

        TreeSet<StateActionEntity> selected = new TreeSet<StateActionEntity>(compareByClassificationEvaluation);

        Double max = -Double.MAX_VALUE;
        for (StateActionEntity save : entities) {
            if (save.isEvaluatedByClassifier()) {
                if (save.getClassificationEvaluation() > max) {
                    max = save.getClassificationEvaluation();
                    ArrayList<StateActionEntity> toRemove = new ArrayList<StateActionEntity>();
                    for (StateActionEntity bestEntity : selected) {
                        if (abs(bestEntity.getClassificationEvaluation() - max) > maxBestStateClassificationDifference) {
                            toRemove.add(bestEntity);
                        }
                    }
                    selected.removeAll(toRemove);
                    selected.add(save);
                } else if (abs(save.getClassificationEvaluation() - max) < maxBestStateClassificationDifference) {
                    selected.add(save);
                }
            }
        }
        bestClassifiedStatisticHolder.setEntities(selected);
        return selected;
    }

    public StateActionEntity findBestEntity(TreeSet<StateActionEntity> entities, Comparator<StateActionEntity> comparator) {
        StateActionEntity best = null;
        for (StateActionEntity entity : entities) {
            if (best == null) {
                best = entity;
            } else {
                if (comparator.compare(entity, best) > 0) {
                    best = entity;
                }
            }
        }
        return best;
    }

    private double varianceStatesValueByTestData(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getValueByTestSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageStatesValueByTestData(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getValueByTestSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageWholeFeaturesSetValueDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetValueByTestSetDiffrence())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceWholeFeaturesSetValueDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetValueByTestSetDiffrence())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageWholeFeaturesSetClassificationDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetClassificationByTestSetDiffrence())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceWholeFeaturesSetClassificationDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetClassificationByTestSetDiffrence())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double varianceStatesValue(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getValue())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageStatesValue(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getValue())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceClassificationByTestData(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getClassificationEvaluationByTestSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageClassificationByTestData(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getClassificationEvaluationByTestSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceTestAndTrainStateValueDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getTrainAndTestValueDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageTestAndTrainStateValueDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getTrainAndTestValueDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceTestAndTrainClassificationDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getTrainAndTestClassificationDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageTestAndTrainStateClassificationDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getTrainAndTestClassificationDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceClassification(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getClassificationEvaluation())
                .filter(Objects::nonNull)
                .toArray(Double[]::new),
                average);
    }

    private double averageWholeFeaturesSetClassificationByLearningSetDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetClassificationByLearningSetDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageWholeFeaturesSetValueByLearningSetDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetValueByLearningSetDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageValueByLearningSet(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getValueByLearningSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageClassificationEvaluationByLearningSet(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getClassificationEvaluationByLearningSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageLearnAndTestClassificationDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getLearnAndTestClassificationDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageLearnAndTestValueDifference(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getLearnAndTestValueDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double varianceWholeFeaturesSetClassificationByLearningSetDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetClassificationByLearningSetDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double varianceWholeFeaturesSetValueByLearningSetDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getWholeFeaturesSetValueByLearningSetDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double varianceValueByLearningSet(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getValueByLearningSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double varianceClassificationEvaluationByLearningSet(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getClassificationEvaluationByLearningSet())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double varianceLearnAndTestClassificationDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getLearnAndTestClassificationDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double varianceLearnAndTestValueDifference(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getLearnAndTestValueDifference())
                .filter(Objects::nonNull)
                .toArray(Double[]::new), average);
    }

    private double averageClassification(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getClassificationEvaluation())
                .filter(Objects::nonNull)
                .toArray(Double[]::new));
    }

    private double averageSubsetSize(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getFeaturesNumber())
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public double variancSubsetSize(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getFeaturesNumber())
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public double averageStateVisit(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getVisitingCount())
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public double varianceStateVisit(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getVisitingCount())
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public double averageStateDiscovery(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities.stream().map((StateActionEntity sae) -> sae.getState().getDiscoveryCount())
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public double varianceStateDiscovery(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities.stream().map((StateActionEntity sae) -> sae.getState().getDiscoveryCount())
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public int minSubsetSize(TreeSet<StateActionEntity> entities) {
        Integer min = Integer.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getFeaturesNumber() < min) {
                min = entity.getState().getFeaturesNumber();

            }
        }
        return min;
    }

    public int maxSubsetSize(TreeSet<StateActionEntity> entities) {
        Integer max = Integer.MIN_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getFeaturesNumber() > max) {
                max = entity.getState().getFeaturesNumber();

            }
        }
        return max;
    }

    public Double minClassification(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getClassificationEvaluation() < min) {
                min = entity.getState().getClassificationEvaluation();

            }
        }
        return min;
    }

    public Double minStateValue(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getValue() < min) {
                min = entity.getState().getValue();

            }
        }
        return min;
    }

    public Integer minStateVisit(TreeSet<StateActionEntity> entities) {
        Integer min = Integer.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTimeSteps().size() < min) {
                min = entity.getState().getTimeSteps().size();

            }
        }
        return min;
    }

    public Integer minStateDiscovery(TreeSet<StateActionEntity> entities) {
        Integer min = Integer.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getDiscoveryTimeSteps().size() < min) {
                min = entity.getState().getDiscoveryTimeSteps().size();

            }
        }
        return min;
    }

    public Double minClassificationByTestData(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getClassificationEvaluationByTestSet() < min) {
                min = entity.getState().getClassificationEvaluationByTestSet();

            }
        }
        return min;
    }

    public Double minTestAndTrainClassificationDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTrainAndTestClassificationDifference() < min) {
                min = entity.getState().getTrainAndTestClassificationDifference();

            }
        }
        return min;
    }

    public Double minTestAndTrainStateValueDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTrainAndTestValueDifference() < min) {
                min = entity.getState().getTrainAndTestValueDifference();

            }
        }
        return min;
    }

    public Double minStateValueByTestData(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getValueByTestSet() < min) {
                min = entity.getState().getValueByTestSet();

            }
        }
        return min;
    }

    public Double minWholeFeaturesSetClassificationDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetClassificationByTestSetDiffrence() < min) {
                min = entity.getState().getWholeFeaturesSetClassificationByTestSetDiffrence();

            }
        }
        return min;
    }

    public Double minWholeFeaturesSetValueDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetValueByTestSetDiffrence() < min) {
                min = entity.getState().getWholeFeaturesSetValueByTestSetDiffrence();

            }
        }
        return min;
    }

    public Double minWholeFeaturesSetClassificationByLearningSetDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetClassificationByLearningSetDifference() < min) {
                min = entity.getState().getWholeFeaturesSetClassificationByLearningSetDifference();

            }
        }
        return min;
    }

    public Double minWholeFeaturesSetValueByLearningSetDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetValueByLearningSetDifference() < min) {
                min = entity.getState().getWholeFeaturesSetValueByLearningSetDifference();

            }
        }
        return min;
    }

    public Double minValueByLearningSet(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getValueByLearningSet() < min) {
                min = entity.getState().getValueByLearningSet();

            }
        }
        return min;
    }

    public Double minClassificationEvaluationByLearningSet(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getClassificationEvaluationByLearningSet() < min) {
                min = entity.getState().getClassificationEvaluationByLearningSet();

            }
        }
        return min;
    }

    public Double minLearnAndTestClassificationDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getLearnAndTestClassificationDifference() < min) {
                min = entity.getState().getLearnAndTestClassificationDifference();

            }
        }
        return min;
    }

    public Double minLearnAndTestValueDifference(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getLearnAndTestValueDifference() < min) {
                min = entity.getState().getLearnAndTestValueDifference();

            }
        }
        return min;
    }

    public StateActionEntity bestNotOverfittedStateByClassification(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        StateActionEntity founded = null;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getLearnAndTestClassificationDifference() < min) {
                min = entity.getState().getLearnAndTestClassificationDifference();
                founded = entity;
            }
        }
        return founded;
    }

    public StateActionEntity bestNotOverfittedStateByValue(TreeSet<StateActionEntity> entities) {
        Double min = Double.MAX_VALUE;
        StateActionEntity founded = null;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getLearnAndTestValueDifference() < min) {
                min = entity.getState().getLearnAndTestValueDifference();
                founded = entity;
            }
        }
        return founded;
    }

    public double maxWholeFeaturesSetClassificationByLearningSetDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetClassificationByLearningSetDifference() > max) {
                max = entity.getState().getWholeFeaturesSetClassificationByLearningSetDifference();

            }
        }
        return max;
    }

    public double maxWholeFeaturesSetValueByLearningSetDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetValueByLearningSetDifference() > max) {
                max = entity.getState().getWholeFeaturesSetValueByLearningSetDifference();

            }
        }
        return max;
    }

    public double maxValueByLearningSet(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getValueByLearningSet() > max) {
                max = entity.getState().getValueByLearningSet();

            }
        }
        return max;
    }

    public double maxClassificationEvaluationByLearningSet(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getClassificationEvaluationByLearningSet() > max) {
                max = entity.getState().getClassificationEvaluationByLearningSet();

            }
        }
        return max;
    }

    public double maxLearnAndTestClassificationDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getLearnAndTestClassificationDifference() > max) {
                max = entity.getState().getLearnAndTestClassificationDifference();

            }
        }
        return max;
    }

    public double maxLearnAndTestValueDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getLearnAndTestValueDifference() > max) {
                max = entity.getState().getLearnAndTestValueDifference();

            }
        }
        return max;
    }

    public double maxStateValue(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getValue() > max) {
                max = entity.getState().getValue();

            }
        }
        return max;
    }

    public double maxStateValueByTestData(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getValueByTestSet() > max) {
                max = entity.getState().getValueByTestSet();

            }
        }
        return max;
    }

    public double maxTestAndTrainStateValueDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTrainAndTestValueDifference() > max) {
                max = entity.getState().getTrainAndTestValueDifference();

            }
        }
        return max;
    }

    public double maxTestAndTrainClassificationDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTrainAndTestClassificationDifference() > max) {
                max = entity.getState().getTrainAndTestClassificationDifference();

            }
        }
        return max;
    }

    public double maxClassification(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getClassificationEvaluation() > max) {
                max = entity.getClassificationEvaluation();

            }
        }
        return max;
    }

    public double maxWholeFeaturesSetClassificationDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetClassificationByTestSetDiffrence() > max) {
                max = entity.getState().getWholeFeaturesSetClassificationByTestSetDiffrence();
            }
        }
        return max;
    }

    public double maxWholeFeaturesSetValueDifference(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getWholeFeaturesSetValueByTestSetDiffrence() > max) {
                max = entity.getState().getWholeFeaturesSetValueByTestSetDiffrence();
            }
        }
        return max;
    }

    public double maxClassificationByTestData(TreeSet<StateActionEntity> entities) {
        Double max = -Double.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getClassificationEvaluationByTestSet() > max) {
                max = entity.getState().getClassificationEvaluationByTestSet();

            }
        }
        return max;
    }

    public Integer maxStateVisit(TreeSet<StateActionEntity> entities) {
        Integer max = Integer.MIN_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTimeSteps().size() > max) {
                max = entity.getState().getTimeSteps().size();

            }
        }
        return max;
    }

    public Integer maxStateDiscovery(TreeSet<StateActionEntity> entities) {
        Integer max = Integer.MIN_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getDiscoveryTimeSteps().size() > max) {
                max = entity.getState().getDiscoveryTimeSteps().size();

            }
        }
        return max;
    }

    public int firstStateVisit(TreeSet<StateActionEntity> entities) {
        Integer min = Integer.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTimeSteps().size() > 0 && entity.getState().getTimeSteps().first() < min) {
                min = entity.getState().getTimeSteps().first();

            }
        }
        return min;
    }

    public int lastStateVisit(TreeSet<StateActionEntity> entities) {
        Integer max = Integer.MIN_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getTimeSteps().size() > 0 && entity.getState().getTimeSteps().last() > max) {
                max = entity.getState().getTimeSteps().last();

            }
        }
        return max;
    }

    public int lastStateDiscovery(TreeSet<StateActionEntity> entities) {
        Integer max = Integer.MIN_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getDiscoveryTimeSteps().size() > 0 && entity.getState().getDiscoveryTimeSteps().last() > max) {
                max = entity.getState().getDiscoveryTimeSteps().last();

            }
        }
        return max;
    }

    public Double averageFirstStateVisit(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getTimeSteps().size() > 0) {
                        return sae.getState().getTimeSteps().first();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public Double averageLastStateVisit(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getTimeSteps().size() > 0) {
                        return sae.getState().getTimeSteps().last();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public Double averageLastStateDiscovery(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getDiscoveryTimeSteps().size() > 0) {
                        return sae.getState().getDiscoveryTimeSteps().last();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public Double varianceFirstStateVisit(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getTimeSteps().size() > 0) {
                        return sae.getState().getTimeSteps().first();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public Double varianceLastStateVisit(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getTimeSteps().size() > 0) {
                        return sae.getState().getTimeSteps().last();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public Double varianceLastStateDiscovery(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getDiscoveryTimeSteps().size() > 0) {
                        return sae.getState().getDiscoveryTimeSteps().last();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public int firstStateDiscovery(TreeSet<StateActionEntity> entities) {
        Integer min = Integer.MAX_VALUE;
        for (StateActionEntity entity : entities) {
            if (entity.getState().getDiscoveryTimeSteps().size() > 0 && entity.getState().getDiscoveryTimeSteps().first() < min) {
                min = entity.getState().getDiscoveryTimeSteps().first();

            }
        }
        return min;
    }

    public Double averageFirstStateDiscovery(TreeSet<StateActionEntity> entities) {
        return StatisticCalculator.average(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getDiscoveryTimeSteps().size() > 0) {
                        return sae.getState().getDiscoveryTimeSteps().first();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new));
    }

    public Double varianceFirstStateDiscovery(TreeSet<StateActionEntity> entities, Double average) {
        return StatisticCalculator.variance(entities
                .stream()
                .map((StateActionEntity sae) -> {
                    if (sae.getState().getDiscoveryTimeSteps().size() > 0) {
                        return sae.getState().getDiscoveryTimeSteps().first();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new),
                average);
    }

    public Double averageSubsetDifferentation(TreeSet<StateActionEntity> entities) {
        if (entities.size() < 2) {
            return null;
        }

        TreeSet<StateActionEntity> tmp = new TreeSet<StateActionEntity>(entities);
        Double result = 0.0;

        for (StateActionEntity first : entities) {
            for (StateActionEntity second : tmp) {
                if (!first.equals(second)) {

                    result += StatisticCalculator.setDifferentation(first.getState().getFeaturesIndexes(), second.getState().getFeaturesIndexes());
                }
            }
            tmp.remove(first);
        }
        result = result / (double) ((double) entities.size() * ((double) entities.size() - 1.0) / 2.0);
        return result;
    }

    public TreeMap<Feature, Integer> featureOccurenceIndexOrdered(TreeSet<StateActionEntity> entities) {
        TreeMap<Feature, Integer> featureOccurance = new TreeMap<Feature, Integer>();
        for (StateActionEntity entity : entities) {
            TreeSet<Feature> features = entity.getState().getState();
            for (Feature feature : features) {
                if (featureOccurance.containsKey(feature)) {
                    featureOccurance.put(feature, featureOccurance.get(feature) + 1);
                } else {
                    featureOccurance.put(feature, 1);
                }
            }

        }
        return featureOccurance;
    }

    public LinkedHashMap<Feature, Integer> featureOccurenceOrderedByValue(TreeSet<StateActionEntity> entities) {
        return sortMapByValue(featureOccurenceIndexOrdered(entities));
    }

    public LinkedHashMap<Feature, Integer> sortMapByValue(TreeMap<Feature, Integer> map) {
        ArrayList<Entry<Feature, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        LinkedHashMap<Feature, Integer> result = new LinkedHashMap<Feature, Integer>();
        for (Entry<Feature, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public EntitiesStatisticHolder getBestClassifiedStatisticHolder() {
        return bestClassifiedStatisticHolder;
    }

    public Double getMaxBestStateClassificationDifference() {
        return maxBestStateClassificationDifference;
    }

    public EntitiesStatisticHolder getBestValuedStatisticHolder() {
        return bestValuedStatisticHolder;
    }

    public Double getMaxBestStateValueDifference() {
        return maxBestStateValueDifference;
    }

    public EntitiesStatisticHolder getWholeStatesStatisticHolder() {
        return wholeStatesStatisticHolder;
    }

    public FSProcess getProcess() {
        return process;
    }

    public void setProcess(FSProcess process) {
        this.process = process;
    }

    public Comparator<StateActionEntity> getCompareByClassificationEvaluation() {
        return compareByClassificationEvaluation;
    }

    public void setCompareByClassificationEvaluation(Comparator<StateActionEntity> compareByClassificationEvaluation) {
        this.compareByClassificationEvaluation = compareByClassificationEvaluation;
    }

    public Comparator<StateActionEntity> getCompareByStateValue() {
        return compareByStateValue;
    }

    public void setCompareByStateValue(Comparator<StateActionEntity> compareByStateValue) {
        this.compareByStateValue = compareByStateValue;
    }

    public Integer getNumberOfBestClassifiedStates() {
        return numberOfBestClassifiedStates;
    }

    public void setNumberOfBestClassifiedStates(Integer numberOfBestClassifiedStates) {
        this.numberOfBestClassifiedStates = numberOfBestClassifiedStates;
    }

    public Integer getNumberOfBestValuedStates() {
        return numberOfBestValuedStates;
    }

    public void setNumberOfBestValuedStates(Integer numberOfBestValuedStates) {
        this.numberOfBestValuedStates = numberOfBestValuedStates;
    }

    public Comparator<StateActionEntity> getCompareByClassificationEvaluationByTestSet() {
        return compareByClassificationEvaluationByTestSet;
    }

    public void setCompareByClassificationEvaluationByTestSet(Comparator<StateActionEntity> compareByClassificationEvaluationByTestSet) {
        this.compareByClassificationEvaluationByTestSet = compareByClassificationEvaluationByTestSet;
    }

    public Comparator<StateActionEntity> getCompareByStateValueByTestSet() {
        return compareByStateValueByTestSet;
    }

    public void setCompareByStateValueByTestSet(Comparator<StateActionEntity> compareByStateValueByTestSet) {
        this.compareByStateValueByTestSet = compareByStateValueByTestSet;
    }

}


