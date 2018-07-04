/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import static machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticCalculator.*;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class EntitiesStatisticHolderCollection {

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

    Comparator<StateActionEntity> compareByLearnAndTestClassificationDifference = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getState().getLearnAndTestClassificationDifference() - o2.getState().getLearnAndTestClassificationDifference();
        if (o1.equals(o2)) {
            return 0;
        }
        if (diff > 0) {
            return -1;
        }
        return 1;

    };
    
    
    Comparator<StateActionEntity> compareByLearnAndTestValueDifference = (StateActionEntity o1, StateActionEntity o2) -> {
        Double diff = o1.getState().getLearnAndTestValueDifference() - o2.getState().getLearnAndTestValueDifference();
        if (o1.equals(o2)) {
            return 0;
        }
        if (diff > 0) {
            return -1;
        }
        return 1;

    };
    

    public StateActionEntity findBestEntity(StateActionEntity[] entities, Comparator<StateActionEntity> comparator) {
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

    ArrayList<EntitiesStatisticHolder> statisticList = new ArrayList<EntitiesStatisticHolder>();

    StateActionEntity uniqueBestValuedState = null;
    StateActionEntity uniqueBestClassifiedState = null;
    StateActionEntity uniqueBestValuedByTestSetState = null;
    StateActionEntity uniqueBestClassifiedByTestSetState = null;
    StateActionEntity uniqueBestNotOverfittedStateByValue = null;
    StateActionEntity uniqueBestNotOverfittedStateByClassification = null;

    Integer totalStateActionEntities = null;
    Double uniqueMaxClassification = null;
    Double uniqueMaxStateValue = null;
    Double uniqueMinClassification = null;
    Double uniqueMinStateValue = null;
    Double uniqueMinClassificationByTestData = null;
    Double uniqueMinStateValueByTestData = null;
    Double uniqueMaxClassificationByTestData = null;
    Double uniqueMaxStateValueByTestData = null;
    Double uniqueMaxTestAndTrainStateValueDifference = null;
    Double uniqueMaxTestAndTrainClassificationDifference = null;
    Double uniqueMinTestAndTrainStateValueDifference = null;
    Double uniqueMinTestAndTrainClassificationDifference = null;
    Double uniqueMinWholeFeaturesSetClassificationDifference = null;
    Double uniqueMaxWholeFeaturesSetClassificationDifference = null;
    Integer uniqueMinStateVisit = null;
    Integer uniqueMaxStateVisit = null;
    Integer uniqueMinStateDiscovery = null;
    Integer uniqueMaxStateDiscovery = null;
    Integer uniqueLastStateVisit = null;
    Integer uniqueLastStateDiscovery = null;
    Integer uniqueFirstStateVisit = null;
    Integer uniqueFirstStateDiscovery = null;
    Integer uniqueMaxSubsetSize = null;
    Double uniqueMaxWholeFeaturesSetValueDifference = null;
    Double uniqueMinWholeFeaturesSetValueDifference = null;

    Double uniqueMaxWholeFeaturesSetClassificationByLearningSetDifference = null;
    Double uniqueMinWholeFeaturesSetClassificationByLearningSetDifference = null;
    Double uniqueMaxWholeFeaturesSetValueByLearningSetDifference = null;
    Double uniqueMinWholeFeaturesSetValueByLearningSetDifference = null;
    Double uniqueMaxValueByLearningSet = null;
    Double uniqueMinValueByLearningSet = null;
    Double uniqueMaxClassificationEvaluationByLearningSet = null;
    Double uniqueMinClassificationEvaluationByLearningSet = null;
    Double uniqueMaxLearnAndTestClassificationDifference = null;
    Double uniqueMinLearnAndTestClassificationDifference = null;
    Double uniqueMaxLearnAndTestValueDifference = null;
    Double uniqueMinLearnAndTestValueDifference = null;

    Integer uniqueBestGeneratedSubsetsDifference = null;

    TreeMap<Feature, Integer> totalFeatureOccurenceIndexOrdered = new TreeMap<Feature, Integer>();

    Double minTestAndTrainStateValueDifference = null;
    Double minTestAndTrainClassificationDifference = null;
    Double minClassification = null;
    Double minStateValue = null;
    Double minClassificationByTestData = null;
    Double minStateValueByTestData = null;
    Double maxClassification = null;
    Double maxStateValue = null;
    Double averageClassification = null;
    Double varianceClassification = null;
    Double averageSubsetSize = null;
    Double varianceSubsetSize = null;
    Double maxSubsetSize = null;
    Double minSubsetSize = null;
    Double averageSubsetDifferentation = null;
    Double averageStateValue = null; //ttu
    Double varianceStateValue = null;
    Double maxClassificationByTestData = null;
    Double maxStateValueByTestData = null;

    Double averageClassificationByTestData = null;
    Double varianceClassificationByTestData = null;
    Double averageStateValueByTestData = null;
    Double varianceStateValueByTestData = null;

    Double averageTestAndTrainStateValueDifference = null;
    Double varianceTestAndTrainStateValueDifference = null;
    Double averageTestAndTrainClassificationDifference = null;
    Double varianceTestAndTrainClassificationDifference = null;

    Double maxTestAndTrainStateValueDifference = null;
    Double maxTestAndTrainClassificationDifference = null;

    Double minWholeFeaturesSetClassificationDifference = null;
    Double maxWholeFeaturesSetClassificationDifference = null;
    Double averageWholeFeaturesSetClassificationDifference = null;
    Double varianceWholeFeaturesSetClassificationDifference = null;

    Double maxWholeFeaturesSetValueDifference = null;
    Double minWholeFeaturesSetValueDifference = null;
    Double averageWholeFeaturesSetValueDifference = null;
    Double varianceWholeFeaturesSetValueDifference = null;

    Double averageStateVisit = null;
    Double varianceStateVisit = null;
    Double firstStateVisit = null;
    Double averageFirstStateVisit = null;
    Double varianceFirstStateVisit = null;

    Double averageStateDiscovery = null;
    Double varianceStateDiscovery = null;
    Double firstStateDiscovery = null;
    Double averageFirstStateDiscovery = null;
    Double varianceFirstStateDiscovery = null;

    Double averageFoundedStates = null;

    Double minStateVisit = null;
    Double maxStateVisit = null;
    Double minStateDiscovery = null;
    Double maxStateDiscovery = null;

    Double lastStateVisit = null;
    Double averageLastStateVisit = null;
    Double varianceLastStateVisit = null;
    Double lastStateDiscovery = null;
    Double averageLastStateDiscovery = null;
    Double varianceLastStateDiscovery = null;

    Double maxWholeFeaturesSetClassificationByLearningSetDifference = null;
    Double minWholeFeaturesSetClassificationByLearningSetDifference = null;
    Double averageWholeFeaturesSetClassificationByLearningSetDifference = null;
    Double varianceWholeFeaturesSetClassificationByLearningSetDifference = null;

    Double maxWholeFeaturesSetValueByLearningSetDifference = null;
    Double minWholeFeaturesSetValueByLearningSetDifference = null;
    Double averageWholeFeaturesSetValueByLearningSetDifference = null;
    Double varianceWholeFeaturesSetValueByLearningSetDifference = null;

    Double maxValueByLearningSet = null;
    Double minValueByLearningSet = null;
    Double varianceValueByLearningSet = null;
    Double averageValueByLearningSet = null;

    Double maxClassificationEvaluationByLearningSet = null;
    Double minClassificationEvaluationByLearningSet = null;
    Double averageClassificationEvaluationByLearningSet = null;
    Double varianceClassificationEvaluationByLearningSet = null;

    Double maxLearnAndTestClassificationDifference = null;
    Double minLearnAndTestClassificationDifference = null;
    Double averageLearnAndTestClassificationDifference = null;
    Double varianceLearnAndTestClassificationDifference = null;

    Double maxLearnAndTestValueDifference = null;
    Double minLearnAndTestValueDifference = null;
    Double averageLearnAndTestValueDifference = null;
    Double varianceLearnAndTestValueDifference = null;

    public boolean add(EntitiesStatisticHolder statisticHolder) {
        return statisticList.add(statisticHolder);
    }

    public void computeAll() {
        computeAverageStatistics();
        computeTotalFeatureOccurenceIndexOrdered();
        computeTotalStateActionEntities();
        computeUniqueStatistics();
    }

    public void computeAverageStatistics() {

        setMaxWholeFeaturesSetClassificationByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxWholeFeaturesSetClassificationByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setMinWholeFeaturesSetClassificationByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinWholeFeaturesSetClassificationByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setAverageWholeFeaturesSetClassificationByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageWholeFeaturesSetClassificationByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setVarianceWholeFeaturesSetClassificationByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceWholeFeaturesSetClassificationByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxWholeFeaturesSetValueByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxWholeFeaturesSetValueByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setMinWholeFeaturesSetValueByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinWholeFeaturesSetValueByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setAverageWholeFeaturesSetValueByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageWholeFeaturesSetValueByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setVarianceWholeFeaturesSetValueByLearningSetDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceWholeFeaturesSetValueByLearningSetDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxValueByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxValueByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setMinValueByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinValueByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setAverageValueByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageValueByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setVarianceValueByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceValueByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxClassificationEvaluationByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxClassificationEvaluationByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setMinClassificationEvaluationByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinClassificationEvaluationByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setAverageClassificationEvaluationByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageClassificationEvaluationByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setVarianceClassificationEvaluationByLearningSet(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceClassificationEvaluationByLearningSet)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxLearnAndTestClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setMinLearnAndTestClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setAverageLearnAndTestClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setVarianceLearnAndTestClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceLearnAndTestClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxLearnAndTestValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setMinLearnAndTestValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setAverageLearnAndTestValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setVarianceLearnAndTestValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceLearnAndTestValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxWholeFeaturesSetClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxWholeFeaturesSetClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinWholeFeaturesSetClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinWholeFeaturesSetClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageWholeFeaturesSetClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageWholeFeaturesSetClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceWholeFeaturesSetClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceWholeFeaturesSetClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxWholeFeaturesSetValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxWholeFeaturesSetValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinWholeFeaturesSetValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinWholeFeaturesSetValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageWholeFeaturesSetValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageWholeFeaturesSetValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceWholeFeaturesSetValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceWholeFeaturesSetValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxClassificationByTestData(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxClassificationByTestData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxStateValueByTestData(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxStateValueByTestData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxClassification(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxClassification)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxStateValue(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxStateValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxTestAndTrainClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxTestAndTrainClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxTestAndTrainStateValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxTestAndTrainStateValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageStateValue(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageStateValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceStateValue(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceStateValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageClassificationByTestData(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageClassificationByTestData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceClassificationByTestData(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceClassification)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageStateValueByTestData(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageStateValueByTestData)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceStateValueByTestData(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceStateValue)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageTestAndTrainClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageTestAndTrainClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceTestAndTrainClassificationDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceTestAndTrainClassificationDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageTestAndTrainStateValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageTestAndTrainStateValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceTestAndTrainStateValueDifference(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceTestAndTrainStateValueDifference)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageClassification(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageClassification)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceClassification(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceClassification)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageSubsetSize(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageSubsetSize)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceSubsetSize(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceSubsetSize)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMaxSubsetSize(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMaxSubsetSize)
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setMinSubsetSize(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getMinSubsetSize)
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setAverageSubsetDifferentation(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageSubsetDifferentation)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageStateVisit(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageStateVisit)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceStateVisit(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceStateVisit)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setFirstStateVisit(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getFirstStateVisit)
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setAverageFirstStateVisit(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageFirstStateVisit)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceFirstStateVisit(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceFirstStateVisit)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageStateDiscovery(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageStateDiscovery)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceStateDiscovery(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceStateDiscovery)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setFirstStateDiscovery(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getFirstStateDiscovery)
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setAverageFirstStateDiscovery(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getAverageFirstStateDiscovery)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceFirstStateDiscovery(average(statisticList.stream()
                .map(EntitiesStatisticHolder::getVarianceFirstStateDiscovery)
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageFoundedStates(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getEntities().size();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setMinStateValue(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateValue();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinClassification(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinClassification();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinStateValueByTestData(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateValueByTestData();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinClassificationByTestData(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinClassificationByTestData();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinTestAndTrainStateValueDifference(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinTestAndTrainStateValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setMinTestAndTrainClassificationDifference(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinTestAndTrainClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        ////
        setMinStateVisit(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setMaxStateVisit(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setMinStateDiscovery(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setMaxStateDiscovery(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setLastStateVisit(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getLastStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setLastStateDiscovery(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getLastStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setAverageLastStateVisit(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getAverageLastStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setAverageLastStateDiscovery(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getAverageLastStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceLastStateVisit(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getVarianceLastStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setVarianceLastStateDiscovery(average(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getVarianceLastStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

    }

    public void computeTotalFeatureOccurenceIndexOrdered() {
        totalFeatureOccurenceIndexOrdered = new TreeMap<Feature, Integer>();
        for (EntitiesStatisticHolder statistic : statisticList) {
            TreeMap<Feature, Integer> featureOccurance = statistic.getFeatureOccurenceIndexOrdered();
            for (Entry<Feature, Integer> entry : featureOccurance.entrySet()) {
                Feature key = entry.getKey();
                if (totalFeatureOccurenceIndexOrdered.containsKey(key)) {
                    totalFeatureOccurenceIndexOrdered.put(key, totalFeatureOccurenceIndexOrdered.get(key) + entry.getValue());
                } else {
                    totalFeatureOccurenceIndexOrdered.put(key, entry.getValue());
                }
            }
        }
    }

    public void computeTotalStateActionEntities() {
        setTotalStateActionEntities(sum(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getEntities().size();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));
    }

    public void computeUniqueStatistics() {

        setUniqueMaxWholeFeaturesSetClassificationByLearningSetDifference(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxWholeFeaturesSetClassificationByLearningSetDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMinWholeFeaturesSetClassificationByLearningSetDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinWholeFeaturesSetClassificationByLearningSetDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxWholeFeaturesSetValueByLearningSetDifference(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxWholeFeaturesSetValueByLearningSetDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMinWholeFeaturesSetValueByLearningSetDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinWholeFeaturesSetValueByLearningSetDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxValueByLearningSet(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxValueByLearningSet();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMinValueByLearningSet(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinValueByLearningSet();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMaxClassificationEvaluationByLearningSet(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxClassificationEvaluationByLearningSet();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMinClassificationEvaluationByLearningSet(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinClassificationEvaluationByLearningSet();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxLearnAndTestClassificationDifference(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxLearnAndTestClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMinLearnAndTestClassificationDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinLearnAndTestClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMaxLearnAndTestValueDifference(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxLearnAndTestValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));
        setUniqueMinLearnAndTestValueDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinLearnAndTestValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxSubsetSize(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxSubsetSize();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueMaxWholeFeaturesSetClassificationDifference(findMax(statisticList.stream() /////// TUTAJ TO OBCZAIC
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxWholeFeaturesSetClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinWholeFeaturesSetClassificationDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinWholeFeaturesSetClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxWholeFeaturesSetValueDifference(findMax(statisticList.stream() /// I JAK COS TUTAJ POPRAWIC CHODZI O MIN I MAX
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxWholeFeaturesSetValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinWholeFeaturesSetValueDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinWholeFeaturesSetValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueBestClassifiedByTestSetState(findBestEntity(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getBestClassifiedByTestSetState();
                })
                .filter(Objects::nonNull)
                .toArray(StateActionEntity[]::new), compareByClassificationEvaluationByTestSet));

        setUniqueBestValuedByTestSetState(findBestEntity(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getBestValuedByTestSetState();
                })
                .filter(Objects::nonNull)
                .toArray(StateActionEntity[]::new), compareByStateValueByTestSet));

        setUniqueBestClassifiedState(findBestEntity(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getBestClassifiedState();
                })
                .filter(Objects::nonNull)
                .toArray(StateActionEntity[]::new), compareByClassificationEvaluation));

        setUniqueBestValuedState(findBestEntity(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getBestValuedState();
                })
                .filter(Objects::nonNull)
                .toArray(StateActionEntity[]::new), compareByStateValue));
        
        
     
                setUniqueBestNotOverfittedStateByClassification(findBestEntity(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getBestNotOverfittedStateByClassification();
                })
                .filter(Objects::nonNull)
                .toArray(StateActionEntity[]::new), compareByLearnAndTestClassificationDifference));
                
                setUniqueBestNotOverfittedStateByValue(findBestEntity(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getBestNotOverfittedStateByValue();
                })
                .filter(Objects::nonNull)
                .toArray(StateActionEntity[]::new), compareByLearnAndTestValueDifference));
                

        setUniqueMaxClassification(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxClassification();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxStateValue(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxStateValue();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxClassificationByTestData(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxClassificationByTestData();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxStateValueByTestData(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxStateValueByTestData();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxTestAndTrainClassificationDifference(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxTestAndTrainClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMaxTestAndTrainStateValueDifference(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxTestAndTrainStateValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinClassification(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinClassification();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinStateValue(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateValue();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinClassificationByTestData(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinClassificationByTestData();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinStateValueByTestData(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateValueByTestData();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinTestAndTrainClassificationDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinTestAndTrainClassificationDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinTestAndTrainStateValueDifference(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinTestAndTrainStateValueDifference();
                })
                .filter(Objects::nonNull)
                .toArray(Double[]::new)));

        setUniqueMinStateVisit(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueMinStateDiscovery(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMinStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueMaxStateVisit(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueMaxStateDiscovery(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getMaxStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueLastStateDiscovery(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getLastStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueLastStateVisit(findMax(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getLastStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueFirstStateDiscovery(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getFirstStateDiscovery();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        setUniqueFirstStateVisit(findMin(statisticList.stream()
                .map((EntitiesStatisticHolder statistics) -> {
                    return statistics.getFirstStateVisit();
                })
                .filter(Objects::nonNull)
                .toArray(Integer[]::new)));

        

        /**
         * GENERACJA RÓŻNICY W WYGENEROWANYCH ZESTWACH ZBIORÓW NAJLEPSZYCH
         */
        if (!(statisticList.size() < 2)) {

            ArrayList<EntitiesStatisticHolder> tmp = new ArrayList<EntitiesStatisticHolder>(statisticList);
            Integer result = 0;

            for (EntitiesStatisticHolder firstStats : statisticList) {
                for (EntitiesStatisticHolder secondStats : tmp) {
                    if (!firstStats.equals(secondStats)) {
                        Integer common = 0;
                        for (StateActionEntity firstEntity : firstStats.getEntities()) {
                            for (StateActionEntity secondEntity : secondStats.getEntities()) {
                                if (firstEntity.getState().equals(secondEntity.getState())) {
                                    common++;
                                    break;
                                }
                            }

                        }
                        result += (firstStats.getEntities().size() - common) + (secondStats.getEntities().size() - common);
                    }
                }
                tmp.remove(firstStats);
            }

            setUniqueBestGeneratedSubsetsDifference(result);

        } else {
            setUniqueBestGeneratedSubsetsDifference(0);
        }

    }

    public Double getUniqueMinTestAndTrainStateValueDifference() {
        return uniqueMinTestAndTrainStateValueDifference;
    }

    public void setUniqueMinTestAndTrainStateValueDifference(Double uniqueMinTestAndTrainStateValueDifference) {
        this.uniqueMinTestAndTrainStateValueDifference = uniqueMinTestAndTrainStateValueDifference;
    }

    public Double getUniqueMinTestAndTrainClassificationDifference() {
        return uniqueMinTestAndTrainClassificationDifference;
    }

    public void setUniqueMinTestAndTrainClassificationDifference(Double uniqueMinTestAndTrainClassificationDifference) {
        this.uniqueMinTestAndTrainClassificationDifference = uniqueMinTestAndTrainClassificationDifference;
    }

    public Double getMinTestAndTrainStateValueDifference() {
        return minTestAndTrainStateValueDifference;
    }

    public void setMinTestAndTrainStateValueDifference(Double minTestAndTrainStateValueDifference) {
        this.minTestAndTrainStateValueDifference = minTestAndTrainStateValueDifference;
    }

    public Double getMinTestAndTrainClassificationDifference() {
        return minTestAndTrainClassificationDifference;
    }

    public void setMinTestAndTrainClassificationDifference(Double minTestAndTrainClassificationDifference) {
        this.minTestAndTrainClassificationDifference = minTestAndTrainClassificationDifference;
    }

    public Double getUniqueMinClassification() {
        return uniqueMinClassification;
    }

    public void setUniqueMinClassification(Double uniqueMinClassification) {
        this.uniqueMinClassification = uniqueMinClassification;
    }

    public Double getUniqueMinStateValue() {
        return uniqueMinStateValue;
    }

    public void setUniqueMinStateValue(Double uniqueMinStateValue) {
        this.uniqueMinStateValue = uniqueMinStateValue;
    }

    public Double getMinClassification() {
        return minClassification;
    }

    public void setMinClassification(Double minClassification) {
        this.minClassification = minClassification;
    }

    public Double getMinStateValue() {
        return minStateValue;
    }

    public void setMinStateValue(Double minStateValue) {
        this.minStateValue = minStateValue;
    }

    public Double getUniqueMaxClassification() {
        return uniqueMaxClassification;
    }

    public void setUniqueMaxClassification(Double uniqueMaxClassification) {
        this.uniqueMaxClassification = uniqueMaxClassification;
    }

    public Double getUniqueMaxStateValue() {
        return uniqueMaxStateValue;
    }

    public void setUniqueMaxStateValue(Double uniqueMaxStateValue) {
        this.uniqueMaxStateValue = uniqueMaxStateValue;
    }

    public Double getUniqueMaxClassificationByTestData() {
        return uniqueMaxClassificationByTestData;
    }

    public void setUniqueMaxClassificationByTestData(Double uniqueMaxClassificationByTestData) {
        this.uniqueMaxClassificationByTestData = uniqueMaxClassificationByTestData;
    }

    public Double getUniqueMaxStateValueByTestData() {
        return uniqueMaxStateValueByTestData;
    }

    public void setUniqueMaxStateValueByTestData(Double uniqueMaxStateValueByTestData) {
        this.uniqueMaxStateValueByTestData = uniqueMaxStateValueByTestData;
    }

    public Double getAverageStateValue() {
        return averageStateValue;
    }

    public void setAverageStateValue(Double averageStateValue) {
        this.averageStateValue = averageStateValue;
    }

    public Double getVarianceStateValue() {
        return varianceStateValue;
    }

    public void setVarianceStateValue(Double varianceStateValue) {
        this.varianceStateValue = varianceStateValue;
    }

    public Double getMaxClassificationByTestData() {
        return maxClassificationByTestData;
    }

    public void setMaxClassificationByTestData(Double maxClassificationByTestData) {
        this.maxClassificationByTestData = maxClassificationByTestData;
    }

    public Double getMaxStateValueByTestData() {
        return maxStateValueByTestData;
    }

    public void setMaxStateValueByTestData(Double maxStateValueByTestData) {
        this.maxStateValueByTestData = maxStateValueByTestData;
    }

    public Double getAverageClassificationByTestData() {
        return averageClassificationByTestData;
    }

    public void setAverageClassificationByTestData(Double averageClassificationByTestData) {
        this.averageClassificationByTestData = averageClassificationByTestData;
    }

    public Double getVarianceClassificationByTestData() {
        return varianceClassificationByTestData;
    }

    public void setVarianceClassificationByTestData(Double varianceClassificationByTestData) {
        this.varianceClassificationByTestData = varianceClassificationByTestData;
    }

    public Double getAverageStateValueByTestData() {
        return averageStateValueByTestData;
    }

    public void setAverageStateValueByTestData(Double averageStateValueByTestData) {
        this.averageStateValueByTestData = averageStateValueByTestData;
    }

    public Double getVarianceStateValueByTestData() {
        return varianceStateValueByTestData;
    }

    public void setVarianceStateValueByTestData(Double varianceStateValueByTestData) {
        this.varianceStateValueByTestData = varianceStateValueByTestData;
    }

    public Double getAverageFoundedStates() {
        return averageFoundedStates;
    }

    public void setAverageFoundedStates(Double averageFoundedStates) {
        this.averageFoundedStates = averageFoundedStates;
    }

    public ArrayList<EntitiesStatisticHolder> getStatisticList() {
        return statisticList;
    }

    public void setStatisticList(ArrayList<EntitiesStatisticHolder> statisticList) {
        this.statisticList = statisticList;
    }

    public Double getMaxClassification() {
        return maxClassification;
    }

    public void setMaxClassification(Double maxClassification) {
        this.maxClassification = maxClassification;
    }

    public Double getMaxStateValue() {
        return maxStateValue;
    }

    public void setMaxStateValue(Double maxStateValue) {
        this.maxStateValue = maxStateValue;
    }

    public Double getAverageClassification() {
        return averageClassification;
    }

    public void setAverageClassification(Double averageClassification) {
        this.averageClassification = averageClassification;
    }

    public Double getVarianceClassification() {
        return varianceClassification;
    }

    public void setVarianceClassification(Double varianceClassification) {
        this.varianceClassification = varianceClassification;
    }

    public Double getAverageSubsetSize() {
        return averageSubsetSize;
    }

    public void setAverageSubsetSize(Double averageSubsetSize) {
        this.averageSubsetSize = averageSubsetSize;
    }

    public Double getVarianceSubsetSize() {
        return varianceSubsetSize;
    }

    public void setVarianceSubsetSize(Double varianceSubsetSize) {
        this.varianceSubsetSize = varianceSubsetSize;
    }

    public Double getMaxSubsetSize() {
        return maxSubsetSize;
    }

    public void setMaxSubsetSize(Double maxSubsetSize) {
        this.maxSubsetSize = maxSubsetSize;
    }

    public Double getMinSubsetSize() {
        return minSubsetSize;
    }

    public void setMinSubsetSize(Double minSubsetSize) {
        this.minSubsetSize = minSubsetSize;
    }

    public Double getAverageSubsetDifferentation() {
        return averageSubsetDifferentation;
    }

    public void setAverageSubsetDifferentation(Double averageSubsetDifferentation) {
        this.averageSubsetDifferentation = averageSubsetDifferentation;
    }

    public Integer getTotalStateActionEntities() {
        return totalStateActionEntities;
    }

    public void setTotalStateActionEntities(Integer totalStateActionEntities) {
        this.totalStateActionEntities = totalStateActionEntities;
    }

    public TreeMap<Feature, Integer> getTotalFeatureOccurenceIndexOrdered() {
        return totalFeatureOccurenceIndexOrdered;
    }

    public void setTotalFeatureOccurenceIndexOrdered(TreeMap<Feature, Integer> totalFeatureOccurenceIndexOrdered) {
        this.totalFeatureOccurenceIndexOrdered = totalFeatureOccurenceIndexOrdered;
    }

    public Double getAverageStateVisit() {
        return averageStateVisit;
    }

    public void setAverageStateVisit(Double averageStateVisit) {
        this.averageStateVisit = averageStateVisit;
    }

    public Double getVarianceStateVisit() {
        return varianceStateVisit;
    }

    public void setVarianceStateVisit(Double varianceStateVisit) {
        this.varianceStateVisit = varianceStateVisit;
    }

    public Double getFirstStateVisit() {
        return firstStateVisit;
    }

    public void setFirstStateVisit(Double firstStateVisit) {
        this.firstStateVisit = firstStateVisit;
    }

    public Double getAverageFirstStateVisit() {
        return averageFirstStateVisit;
    }

    public void setAverageFirstStateVisit(Double averageFirstStateVisit) {
        this.averageFirstStateVisit = averageFirstStateVisit;
    }

    public Double getVarianceFirstStateVisit() {
        return varianceFirstStateVisit;
    }

    public void setVarianceFirstStateVisit(Double varianceFirstStateVisit) {
        this.varianceFirstStateVisit = varianceFirstStateVisit;
    }

    public Double getAverageStateDiscovery() {
        return averageStateDiscovery;
    }

    public void setAverageStateDiscovery(Double averageStateDiscovery) {
        this.averageStateDiscovery = averageStateDiscovery;
    }

    public Double getVarianceStateDiscovery() {
        return varianceStateDiscovery;
    }

    public void setVarianceStateDiscovery(Double varianceStateDiscovery) {
        this.varianceStateDiscovery = varianceStateDiscovery;
    }

    public Double getFirstStateDiscovery() {
        return firstStateDiscovery;
    }

    public void setFirstStateDiscovery(Double firstStateDiscovery) {
        this.firstStateDiscovery = firstStateDiscovery;
    }

    public Double getAverageFirstStateDiscovery() {
        return averageFirstStateDiscovery;
    }

    public void setAverageFirstStateDiscovery(Double averageFirstStateDiscovery) {
        this.averageFirstStateDiscovery = averageFirstStateDiscovery;
    }

    public Double getVarianceFirstStateDiscovery() {
        return varianceFirstStateDiscovery;
    }

    public void setVarianceFirstStateDiscovery(Double varianceFirstStateDiscovery) {
        this.varianceFirstStateDiscovery = varianceFirstStateDiscovery;
    }

    public Double getAverageTestAndTrainStateValueDifference() {
        return averageTestAndTrainStateValueDifference;
    }

    public void setAverageTestAndTrainStateValueDifference(Double averageTestAndTrainStateValueDifference) {
        this.averageTestAndTrainStateValueDifference = averageTestAndTrainStateValueDifference;
    }

    public Double getVarianceTestAndTrainStateValueDifference() {
        return varianceTestAndTrainStateValueDifference;
    }

    public void setVarianceTestAndTrainStateValueDifference(Double varianceTestAndTrainStateValueDifference) {
        this.varianceTestAndTrainStateValueDifference = varianceTestAndTrainStateValueDifference;
    }

    public Double getAverageTestAndTrainClassificationDifference() {
        return averageTestAndTrainClassificationDifference;
    }

    public void setAverageTestAndTrainClassificationDifference(Double averageTestAndTrainClassificationDifference) {
        this.averageTestAndTrainClassificationDifference = averageTestAndTrainClassificationDifference;
    }

    public Double getVarianceTestAndTrainClassificationDifference() {
        return varianceTestAndTrainClassificationDifference;
    }

    public void setVarianceTestAndTrainClassificationDifference(Double varianceTestAndTrainClassificationDifference) {
        this.varianceTestAndTrainClassificationDifference = varianceTestAndTrainClassificationDifference;
    }

    public Double getUniqueMaxTestAndTrainStateValueDifference() {
        return uniqueMaxTestAndTrainStateValueDifference;
    }

    public void setUniqueMaxTestAndTrainStateValueDifference(Double uniqueMaxTestAndTrainStateValueDifference) {
        this.uniqueMaxTestAndTrainStateValueDifference = uniqueMaxTestAndTrainStateValueDifference;
    }

    public Double getUniqueMaxTestAndTrainClassificationDifference() {
        return uniqueMaxTestAndTrainClassificationDifference;
    }

    public void setUniqueMaxTestAndTrainClassificationDifference(Double uniqueMaxTestAndTrainClassificationDifference) {
        this.uniqueMaxTestAndTrainClassificationDifference = uniqueMaxTestAndTrainClassificationDifference;
    }

    public Double getMaxTestAndTrainStateValueDifference() {
        return maxTestAndTrainStateValueDifference;
    }

    public void setMaxTestAndTrainStateValueDifference(Double maxTestAndTrainStateValueDifference) {
        this.maxTestAndTrainStateValueDifference = maxTestAndTrainStateValueDifference;
    }

    public Double getMaxTestAndTrainClassificationDifference() {
        return maxTestAndTrainClassificationDifference;
    }

    public void setMaxTestAndTrainClassificationDifference(Double maxTestAndTrainClassificationDifference) {
        this.maxTestAndTrainClassificationDifference = maxTestAndTrainClassificationDifference;
    }

    public Double getUniqueMinClassificationByTestData() {
        return uniqueMinClassificationByTestData;
    }

    public void setUniqueMinClassificationByTestData(Double uniqueMinClassificationByTestData) {
        this.uniqueMinClassificationByTestData = uniqueMinClassificationByTestData;
    }

    public Double getUniqueMinStateValueByTestData() {
        return uniqueMinStateValueByTestData;
    }

    public void setUniqueMinStateValueByTestData(Double uniqueMinStateValueByTestData) {
        this.uniqueMinStateValueByTestData = uniqueMinStateValueByTestData;
    }

    public Double getMinClassificationByTestData() {
        return minClassificationByTestData;
    }

    public void setMinClassificationByTestData(Double minClassificationByTestData) {
        this.minClassificationByTestData = minClassificationByTestData;
    }

    public Double getMinStateValueByTestData() {
        return minStateValueByTestData;
    }

    public void setMinStateValueByTestData(Double minStateValueByTestData) {
        this.minStateValueByTestData = minStateValueByTestData;
    }

    public Integer getUniqueBestGeneratedSubsetsDifference() {
        return uniqueBestGeneratedSubsetsDifference;
    }

    public void setUniqueBestGeneratedSubsetsDifference(Integer uniqueBestGeneratedSubsetsDifference) {
        this.uniqueBestGeneratedSubsetsDifference = uniqueBestGeneratedSubsetsDifference;
    }

    public Double getUniqueMinWholeFeaturesSetClassificationDifference() {
        return uniqueMinWholeFeaturesSetClassificationDifference;
    }

    public void setUniqueMinWholeFeaturesSetClassificationDifference(Double uniqueMinWholeFeaturesSetClassificationDifference) {
        this.uniqueMinWholeFeaturesSetClassificationDifference = uniqueMinWholeFeaturesSetClassificationDifference;
    }

    public Double getUniqueMaxWholeFeaturesSetClassificationDifference() {
        return uniqueMaxWholeFeaturesSetClassificationDifference;
    }

    public void setUniqueMaxWholeFeaturesSetClassificationDifference(Double uniqueMaxWholeFeaturesSetClassificationDifference) {
        this.uniqueMaxWholeFeaturesSetClassificationDifference = uniqueMaxWholeFeaturesSetClassificationDifference;
    }

    public Double getMinWholeFeaturesSetClassificationDifference() {
        return minWholeFeaturesSetClassificationDifference;
    }

    public void setMinWholeFeaturesSetClassificationDifference(Double minWholeFeaturesSetClassificationDifference) {
        this.minWholeFeaturesSetClassificationDifference = minWholeFeaturesSetClassificationDifference;
    }

    public Double getMaxWholeFeaturesSetClassificationDifference() {
        return maxWholeFeaturesSetClassificationDifference;
    }

    public void setMaxWholeFeaturesSetClassificationDifference(Double maxWholeFeaturesSetClassificationDifference) {
        this.maxWholeFeaturesSetClassificationDifference = maxWholeFeaturesSetClassificationDifference;
    }

    public Double getAverageWholeFeaturesSetClassificationDifference() {
        return averageWholeFeaturesSetClassificationDifference;
    }

    public void setAverageWholeFeaturesSetClassificationDifference(Double averageWholeFeaturesSetClassificationDifference) {
        this.averageWholeFeaturesSetClassificationDifference = averageWholeFeaturesSetClassificationDifference;
    }

    public Double getVarianceWholeFeaturesSetClassificationDifference() {
        return varianceWholeFeaturesSetClassificationDifference;
    }

    public void setVarianceWholeFeaturesSetClassificationDifference(Double varianceWholeFeaturesSetClassificationDifference) {
        this.varianceWholeFeaturesSetClassificationDifference = varianceWholeFeaturesSetClassificationDifference;
    }

    public Double getMinStateVisit() {
        return minStateVisit;
    }

    public void setMinStateVisit(Double minStateVisit) {
        this.minStateVisit = minStateVisit;
    }

    public Double getMaxStateVisit() {
        return maxStateVisit;
    }

    public void setMaxStateVisit(Double maxStateVisit) {
        this.maxStateVisit = maxStateVisit;
    }

    public Double getMinStateDiscovery() {
        return minStateDiscovery;
    }

    public void setMinStateDiscovery(Double minStateDiscovery) {
        this.minStateDiscovery = minStateDiscovery;
    }

    public Double getMaxStateDiscovery() {
        return maxStateDiscovery;
    }

    public void setMaxStateDiscovery(Double maxStateDiscovery) {
        this.maxStateDiscovery = maxStateDiscovery;
    }

    public Double getLastStateVisit() {
        return lastStateVisit;
    }

    public void setLastStateVisit(Double lastStateVisit) {
        this.lastStateVisit = lastStateVisit;
    }

    public Double getAverageLastStateVisit() {
        return averageLastStateVisit;
    }

    public void setAverageLastStateVisit(Double averageLastStateVisit) {
        this.averageLastStateVisit = averageLastStateVisit;
    }

    public Double getVarianceLastStateVisit() {
        return varianceLastStateVisit;
    }

    public void setVarianceLastStateVisit(Double varianceLastStateVisit) {
        this.varianceLastStateVisit = varianceLastStateVisit;
    }

    public Double getLastStateDiscovery() {
        return lastStateDiscovery;
    }

    public void setLastStateDiscovery(Double lastStateDiscovery) {
        this.lastStateDiscovery = lastStateDiscovery;
    }

    public Double getAverageLastStateDiscovery() {
        return averageLastStateDiscovery;
    }

    public void setAverageLastStateDiscovery(Double averageLastStateDiscovery) {
        this.averageLastStateDiscovery = averageLastStateDiscovery;
    }

    public Double getVarianceLastStateDiscovery() {
        return varianceLastStateDiscovery;
    }

    public void setVarianceLastStateDiscovery(Double varianceLastStateDiscovery) {
        this.varianceLastStateDiscovery = varianceLastStateDiscovery;
    }

    public Integer getUniqueMinStateVisit() {
        return uniqueMinStateVisit;
    }

    public void setUniqueMinStateVisit(Integer uniqueMinStateVisit) {
        this.uniqueMinStateVisit = uniqueMinStateVisit;
    }

    public Integer getUniqueMaxStateVisit() {
        return uniqueMaxStateVisit;
    }

    public void setUniqueMaxStateVisit(Integer uniqueMaxStateVisit) {
        this.uniqueMaxStateVisit = uniqueMaxStateVisit;
    }

    public Integer getUniqueMinStateDiscovery() {
        return uniqueMinStateDiscovery;
    }

    public void setUniqueMinStateDiscovery(Integer uniqueMinStateDiscovery) {
        this.uniqueMinStateDiscovery = uniqueMinStateDiscovery;
    }

    public Integer getUniqueMaxStateDiscovery() {
        return uniqueMaxStateDiscovery;
    }

    public void setUniqueMaxStateDiscovery(Integer uniqueMaxStateDiscovery) {
        this.uniqueMaxStateDiscovery = uniqueMaxStateDiscovery;
    }

    public Integer getUniqueLastStateVisit() {
        return uniqueLastStateVisit;
    }

    public void setUniqueLastStateVisit(Integer uniqueLastStateVisit) {
        this.uniqueLastStateVisit = uniqueLastStateVisit;
    }

    public Integer getUniqueLastStateDiscovery() {
        return uniqueLastStateDiscovery;
    }

    public void setUniqueLastStateDiscovery(Integer uniqueLastStateDiscovery) {
        this.uniqueLastStateDiscovery = uniqueLastStateDiscovery;
    }

    public Integer getUniqueFirstStateVisit() {
        return uniqueFirstStateVisit;
    }

    public void setUniqueFirstStateVisit(Integer uniqueFirstStateVisit) {
        this.uniqueFirstStateVisit = uniqueFirstStateVisit;
    }

    public Integer getUniqueFirstStateDiscovery() {
        return uniqueFirstStateDiscovery;
    }

    public void setUniqueFirstStateDiscovery(Integer uniqueFirstStateDiscovery) {
        this.uniqueFirstStateDiscovery = uniqueFirstStateDiscovery;
    }

    public Integer getUniqueMaxSubsetSize() {
        return uniqueMaxSubsetSize;
    }

    public void setUniqueMaxSubsetSize(Integer uniqueMaxSubsetSize) {
        this.uniqueMaxSubsetSize = uniqueMaxSubsetSize;
    }

    public Double getUniqueMaxWholeFeaturesSetValueDifference() {
        return uniqueMaxWholeFeaturesSetValueDifference;
    }

    public void setUniqueMaxWholeFeaturesSetValueDifference(Double uniqueMaxWholeFeaturesSetValueDifference) {
        this.uniqueMaxWholeFeaturesSetValueDifference = uniqueMaxWholeFeaturesSetValueDifference;
    }

    public Double getUniqueMinWholeFeaturesSetValueDifference() {
        return uniqueMinWholeFeaturesSetValueDifference;
    }

    public void setUniqueMinWholeFeaturesSetValueDifference(Double uniqueMinWholeFeaturesSetValueDifference) {
        this.uniqueMinWholeFeaturesSetValueDifference = uniqueMinWholeFeaturesSetValueDifference;
    }

    public Double getMaxWholeFeaturesSetValueDifference() {
        return maxWholeFeaturesSetValueDifference;
    }

    public void setMaxWholeFeaturesSetValueDifference(Double maxWholeFeaturesSetValueDifference) {
        this.maxWholeFeaturesSetValueDifference = maxWholeFeaturesSetValueDifference;
    }

    public Double getMinWholeFeaturesSetValueDifference() {
        return minWholeFeaturesSetValueDifference;
    }

    public void setMinWholeFeaturesSetValueDifference(Double minWholeFeaturesSetValueDifference) {
        this.minWholeFeaturesSetValueDifference = minWholeFeaturesSetValueDifference;
    }

    public Double getAverageWholeFeaturesSetValueDifference() {
        return averageWholeFeaturesSetValueDifference;
    }

    public void setAverageWholeFeaturesSetValueDifference(Double averageWholeFeaturesSetValueDifference) {
        this.averageWholeFeaturesSetValueDifference = averageWholeFeaturesSetValueDifference;
    }

    public Double getVarianceWholeFeaturesSetValueDifference() {
        return varianceWholeFeaturesSetValueDifference;
    }

    public void setVarianceWholeFeaturesSetValueDifference(Double varianceWholeFeaturesSetValueDifference) {
        this.varianceWholeFeaturesSetValueDifference = varianceWholeFeaturesSetValueDifference;
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

    public StateActionEntity getUniqueBestValuedState() {
        return uniqueBestValuedState;
    }

    public void setUniqueBestValuedState(StateActionEntity uniqueBestValuedState) {
        this.uniqueBestValuedState = uniqueBestValuedState;
    }

    public StateActionEntity getUniqueBestClassifiedState() {
        return uniqueBestClassifiedState;
    }

    public void setUniqueBestClassifiedState(StateActionEntity uniqueBestClassifiedState) {
        this.uniqueBestClassifiedState = uniqueBestClassifiedState;
    }

    public StateActionEntity getUniqueBestValuedByTestSetState() {
        return uniqueBestValuedByTestSetState;
    }

    public void setUniqueBestValuedByTestSetState(StateActionEntity uniqueBestValuedByTestSetState) {
        this.uniqueBestValuedByTestSetState = uniqueBestValuedByTestSetState;
    }

    public StateActionEntity getUniqueBestClassifiedByTestSetState() {
        return uniqueBestClassifiedByTestSetState;
    }

    public void setUniqueBestClassifiedByTestSetState(StateActionEntity uniqueBestClassifiedByTestSetState) {
        this.uniqueBestClassifiedByTestSetState = uniqueBestClassifiedByTestSetState;
    }

    public Double getUniqueMaxWholeFeaturesSetClassificationByLearningSetDifference() {
        return uniqueMaxWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setUniqueMaxWholeFeaturesSetClassificationByLearningSetDifference(Double uniqueMaxWholeFeaturesSetClassificationByLearningSetDifference) {
        this.uniqueMaxWholeFeaturesSetClassificationByLearningSetDifference = uniqueMaxWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getUniqueMinWholeFeaturesSetClassificationByLearningSetDifference() {
        return uniqueMinWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setUniqueMinWholeFeaturesSetClassificationByLearningSetDifference(Double uniqueMinWholeFeaturesSetClassificationByLearningSetDifference) {
        this.uniqueMinWholeFeaturesSetClassificationByLearningSetDifference = uniqueMinWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getUniqueMaxWholeFeaturesSetValueByLearningSetDifference() {
        return uniqueMaxWholeFeaturesSetValueByLearningSetDifference;
    }

    public void setUniqueMaxWholeFeaturesSetValueByLearningSetDifference(Double uniqueMaxWholeFeaturesSetValueByLearningSetDifference) {
        this.uniqueMaxWholeFeaturesSetValueByLearningSetDifference = uniqueMaxWholeFeaturesSetValueByLearningSetDifference;
    }

    public Double getUniqueMinWholeFeaturesSetValueByLearningSetDifference() {
        return uniqueMinWholeFeaturesSetValueByLearningSetDifference;
    }

    public void setUniqueMinWholeFeaturesSetValueByLearningSetDifference(Double uniqueMinWholeFeaturesSetValueByLearningSetDifference) {
        this.uniqueMinWholeFeaturesSetValueByLearningSetDifference = uniqueMinWholeFeaturesSetValueByLearningSetDifference;
    }

    public Double getUniqueMaxValueByLearningSet() {
        return uniqueMaxValueByLearningSet;
    }

    public void setUniqueMaxValueByLearningSet(Double uniqueMaxValueByLearningSet) {
        this.uniqueMaxValueByLearningSet = uniqueMaxValueByLearningSet;
    }

    public Double getUniqueMinValueByLearningSet() {
        return uniqueMinValueByLearningSet;
    }

    public void setUniqueMinValueByLearningSet(Double uniqueMinValueByLearningSet) {
        this.uniqueMinValueByLearningSet = uniqueMinValueByLearningSet;
    }

    public Double getUniqueMaxClassificationEvaluationByLearningSet() {
        return uniqueMaxClassificationEvaluationByLearningSet;
    }

    public void setUniqueMaxClassificationEvaluationByLearningSet(Double uniqueMaxClassificationEvaluationByLearningSet) {
        this.uniqueMaxClassificationEvaluationByLearningSet = uniqueMaxClassificationEvaluationByLearningSet;
    }

    public Double getUniqueMinClassificationEvaluationByLearningSet() {
        return uniqueMinClassificationEvaluationByLearningSet;
    }

    public void setUniqueMinClassificationEvaluationByLearningSet(Double uniqueMinClassificationEvaluationByLearningSet) {
        this.uniqueMinClassificationEvaluationByLearningSet = uniqueMinClassificationEvaluationByLearningSet;
    }

    public Double getUniqueMaxLearnAndTestClassificationDifference() {
        return uniqueMaxLearnAndTestClassificationDifference;
    }

    public void setUniqueMaxLearnAndTestClassificationDifference(Double uniqueMaxLearnAndTestClassificationDifference) {
        this.uniqueMaxLearnAndTestClassificationDifference = uniqueMaxLearnAndTestClassificationDifference;
    }

    public Double getUniqueMinLearnAndTestClassificationDifference() {
        return uniqueMinLearnAndTestClassificationDifference;
    }

    public void setUniqueMinLearnAndTestClassificationDifference(Double uniqueMinLearnAndTestClassificationDifference) {
        this.uniqueMinLearnAndTestClassificationDifference = uniqueMinLearnAndTestClassificationDifference;
    }

    public Double getUniqueMaxLearnAndTestValueDifference() {
        return uniqueMaxLearnAndTestValueDifference;
    }

    public void setUniqueMaxLearnAndTestValueDifference(Double uniqueMaxLearnAndTestValueDifference) {
        this.uniqueMaxLearnAndTestValueDifference = uniqueMaxLearnAndTestValueDifference;
    }

    public Double getUniqueMinLearnAndTestValueDifference() {
        return uniqueMinLearnAndTestValueDifference;
    }

    public void setUniqueMinLearnAndTestValueDifference(Double uniqueMinLearnAndTestValueDifference) {
        this.uniqueMinLearnAndTestValueDifference = uniqueMinLearnAndTestValueDifference;
    }

    public Double getMaxWholeFeaturesSetClassificationByLearningSetDifference() {
        return maxWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setMaxWholeFeaturesSetClassificationByLearningSetDifference(Double maxWholeFeaturesSetClassificationByLearningSetDifference) {
        this.maxWholeFeaturesSetClassificationByLearningSetDifference = maxWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getMinWholeFeaturesSetClassificationByLearningSetDifference() {
        return minWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setMinWholeFeaturesSetClassificationByLearningSetDifference(Double minWholeFeaturesSetClassificationByLearningSetDifference) {
        this.minWholeFeaturesSetClassificationByLearningSetDifference = minWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getAverageWholeFeaturesSetClassificationByLearningSetDifference() {
        return averageWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setAverageWholeFeaturesSetClassificationByLearningSetDifference(Double averageWholeFeaturesSetClassificationByLearningSetDifference) {
        this.averageWholeFeaturesSetClassificationByLearningSetDifference = averageWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getVarianceWholeFeaturesSetClassificationByLearningSetDifference() {
        return varianceWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setVarianceWholeFeaturesSetClassificationByLearningSetDifference(Double varianceWholeFeaturesSetClassificationByLearningSetDifference) {
        this.varianceWholeFeaturesSetClassificationByLearningSetDifference = varianceWholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getMaxWholeFeaturesSetValueByLearningSetDifference() {
        return maxWholeFeaturesSetValueByLearningSetDifference;
    }

    public void setMaxWholeFeaturesSetValueByLearningSetDifference(Double maxWholeFeaturesSetValueByLearningSetDifference) {
        this.maxWholeFeaturesSetValueByLearningSetDifference = maxWholeFeaturesSetValueByLearningSetDifference;
    }

    public Double getMinWholeFeaturesSetValueByLearningSetDifference() {
        return minWholeFeaturesSetValueByLearningSetDifference;
    }

    public void setMinWholeFeaturesSetValueByLearningSetDifference(Double minWholeFeaturesSetValueByLearningSetDifference) {
        this.minWholeFeaturesSetValueByLearningSetDifference = minWholeFeaturesSetValueByLearningSetDifference;
    }

    public Double getAverageWholeFeaturesSetValueByLearningSetDifference() {
        return averageWholeFeaturesSetValueByLearningSetDifference;
    }

    public void setAverageWholeFeaturesSetValueByLearningSetDifference(Double averageWholeFeaturesSetValueByLearningSetDifference) {
        this.averageWholeFeaturesSetValueByLearningSetDifference = averageWholeFeaturesSetValueByLearningSetDifference;
    }

    public Double getVarianceWholeFeaturesSetValueByLearningSetDifference() {
        return varianceWholeFeaturesSetValueByLearningSetDifference;
    }

    public void setVarianceWholeFeaturesSetValueByLearningSetDifference(Double varianceWholeFeaturesSetValueByLearningSetDifference) {
        this.varianceWholeFeaturesSetValueByLearningSetDifference = varianceWholeFeaturesSetValueByLearningSetDifference;
    }

    public Double getMaxValueByLearningSet() {
        return maxValueByLearningSet;
    }

    public void setMaxValueByLearningSet(Double maxValueByLearningSet) {
        this.maxValueByLearningSet = maxValueByLearningSet;
    }

    public Double getMinValueByLearningSet() {
        return minValueByLearningSet;
    }

    public void setMinValueByLearningSet(Double minValueByLearningSet) {
        this.minValueByLearningSet = minValueByLearningSet;
    }

    public Double getVarianceValueByLearningSet() {
        return varianceValueByLearningSet;
    }

    public void setVarianceValueByLearningSet(Double varianceValueByLearningSet) {
        this.varianceValueByLearningSet = varianceValueByLearningSet;
    }

    public Double getAverageValueByLearningSet() {
        return averageValueByLearningSet;
    }

    public void setAverageValueByLearningSet(Double averageValueByLearningSet) {
        this.averageValueByLearningSet = averageValueByLearningSet;
    }

    public Double getMaxClassificationEvaluationByLearningSet() {
        return maxClassificationEvaluationByLearningSet;
    }

    public void setMaxClassificationEvaluationByLearningSet(Double maxClassificationEvaluationByLearningSet) {
        this.maxClassificationEvaluationByLearningSet = maxClassificationEvaluationByLearningSet;
    }

    public Double getMinClassificationEvaluationByLearningSet() {
        return minClassificationEvaluationByLearningSet;
    }

    public void setMinClassificationEvaluationByLearningSet(Double minClassificationEvaluationByLearningSet) {
        this.minClassificationEvaluationByLearningSet = minClassificationEvaluationByLearningSet;
    }

    public Double getAverageClassificationEvaluationByLearningSet() {
        return averageClassificationEvaluationByLearningSet;
    }

    public void setAverageClassificationEvaluationByLearningSet(Double averageClassificationEvaluationByLearningSet) {
        this.averageClassificationEvaluationByLearningSet = averageClassificationEvaluationByLearningSet;
    }

    public Double getVarianceClassificationEvaluationByLearningSet() {
        return varianceClassificationEvaluationByLearningSet;
    }

    public void setVarianceClassificationEvaluationByLearningSet(Double varianceClassificationEvaluationByLearningSet) {
        this.varianceClassificationEvaluationByLearningSet = varianceClassificationEvaluationByLearningSet;
    }

    public Double getMaxLearnAndTestClassificationDifference() {
        return maxLearnAndTestClassificationDifference;
    }

    public void setMaxLearnAndTestClassificationDifference(Double maxLearnAndTestClassificationDifference) {
        this.maxLearnAndTestClassificationDifference = maxLearnAndTestClassificationDifference;
    }

    public Double getMinLearnAndTestClassificationDifference() {
        return minLearnAndTestClassificationDifference;
    }

    public void setMinLearnAndTestClassificationDifference(Double minLearnAndTestClassificationDifference) {
        this.minLearnAndTestClassificationDifference = minLearnAndTestClassificationDifference;
    }

    public Double getAverageLearnAndTestClassificationDifference() {
        return averageLearnAndTestClassificationDifference;
    }

    public void setAverageLearnAndTestClassificationDifference(Double averageLearnAndTestClassificationDifference) {
        this.averageLearnAndTestClassificationDifference = averageLearnAndTestClassificationDifference;
    }

    public Double getVarianceLearnAndTestClassificationDifference() {
        return varianceLearnAndTestClassificationDifference;
    }

    public void setVarianceLearnAndTestClassificationDifference(Double varianceLearnAndTestClassificationDifference) {
        this.varianceLearnAndTestClassificationDifference = varianceLearnAndTestClassificationDifference;
    }

    public Double getMaxLearnAndTestValueDifference() {
        return maxLearnAndTestValueDifference;
    }

    public void setMaxLearnAndTestValueDifference(Double maxLearnAndTestValueDifference) {
        this.maxLearnAndTestValueDifference = maxLearnAndTestValueDifference;
    }

    public Double getMinLearnAndTestValueDifference() {
        return minLearnAndTestValueDifference;
    }

    public void setMinLearnAndTestValueDifference(Double minLearnAndTestValueDifference) {
        this.minLearnAndTestValueDifference = minLearnAndTestValueDifference;
    }

    public Double getAverageLearnAndTestValueDifference() {
        return averageLearnAndTestValueDifference;
    }

    public void setAverageLearnAndTestValueDifference(Double averageLearnAndTestValueDifference) {
        this.averageLearnAndTestValueDifference = averageLearnAndTestValueDifference;
    }

    public Double getVarianceLearnAndTestValueDifference() {
        return varianceLearnAndTestValueDifference;
    }

    public void setVarianceLearnAndTestValueDifference(Double varianceLearnAndTestValueDifference) {
        this.varianceLearnAndTestValueDifference = varianceLearnAndTestValueDifference;
    }

    public StateActionEntity getUniqueBestNotOverfittedStateByValue() {
        return uniqueBestNotOverfittedStateByValue;
    }

    public void setUniqueBestNotOverfittedStateByValue(StateActionEntity uniqueBestNotOverfittedStateByValue) {
        this.uniqueBestNotOverfittedStateByValue = uniqueBestNotOverfittedStateByValue;
    }

    public StateActionEntity getUniqueBestNotOverfittedStateByClassification() {
        return uniqueBestNotOverfittedStateByClassification;
    }

    public void setUniqueBestNotOverfittedStateByClassification(StateActionEntity uniqueBestNotOverfittedStateByClassification) {
        this.uniqueBestNotOverfittedStateByClassification = uniqueBestNotOverfittedStateByClassification;
    }

}
