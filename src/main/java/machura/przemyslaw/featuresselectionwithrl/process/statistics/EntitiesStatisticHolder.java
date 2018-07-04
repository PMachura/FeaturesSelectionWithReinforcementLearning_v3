/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class EntitiesStatisticHolder{
    TreeSet<StateActionEntity> entities = new TreeSet<StateActionEntity>();
    
    StateActionEntity bestValuedState = null;
    StateActionEntity bestClassifiedState = null;
    StateActionEntity bestValuedByTestSetState = null;
    StateActionEntity bestClassifiedByTestSetState = null;
    StateActionEntity bestNotOverfittedStateByValue  = null;
    StateActionEntity bestNotOverfittedStateByClassification = null;
    
    Double maxClassification = null;
    Double maxStateValue = null;
    
    Double minClassification = null;
    Double minStateValue = null;
    Double averageClassification = null;
    Double varianceClassification = null;
    Double averageStateValue = null; //ttu
    Double varianceStateValue = null;
    
    Double maxClassificationByTestData = null;
    Double maxStateValueByTestData = null;
    Double minClassificationByTestData = null;
    Double minStateValueByTestData = null;
            
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
    
    Double minTestAndTrainStateValueDifference = null;
    Double minTestAndTrainClassificationDifference = null;
    
    //////
    Double maxWholeFeaturesSetClassificationDifference=null;
    Double minWholeFeaturesSetClassificationDifference=null;
    Double averageWholeFeaturesSetClassificationDifference=null;
    Double varianceWholeFeaturesSetClassificationDifference=null;
    
    
    //tutaj xD
    Double maxWholeFeaturesSetValueDifference=null;
    Double minWholeFeaturesSetValueDifference=null;
    Double averageWholeFeaturesSetValueDifference=null;
    Double varianceWholeFeaturesSetValueDifference=null;
    
    Double maxWholeFeaturesSetClassificationByLearningSetDifference=null;
    Double minWholeFeaturesSetClassificationByLearningSetDifference=null;
    Double averageWholeFeaturesSetClassificationByLearningSetDifference=null;
    Double varianceWholeFeaturesSetClassificationByLearningSetDifference=null;
    
    Double maxWholeFeaturesSetValueByLearningSetDifference=null;
    Double minWholeFeaturesSetValueByLearningSetDifference=null;
    Double averageWholeFeaturesSetValueByLearningSetDifference=null;
    Double varianceWholeFeaturesSetValueByLearningSetDifference=null;
    
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
    
    Double averageSubsetSize = null;
    Double varianceSubsetSize = null;
    Integer maxSubsetSize = null;
    Integer minSubsetSize = null;
    Double averageSubsetDifferentation = null;
    TreeMap<Feature, Integer> featureOccurenceIndexOrdered = new TreeMap<Feature, Integer>();
    //LinkedHashMap<Feature, Integer> featureOccurenceOrderedByValue = null;
    
    Integer maxStateVisit = null;
    Integer minStateVisit= null;
    Double averageStateVisit = null;
    Double varianceStateVisit = null;      
    Integer firstStateVisit = null;
    Double averageFirstStateVisit = null;
    Double varianceFirstStateVisit = null;
    Integer lastStateVisit = null;
    Double averageLastStateVisit = null;
    Double varianceLastStateVisit = null;
    
    
    
    Integer maxStateDiscovery= null;
    Integer minStateDiscovery= null;
    Double averageStateDiscovery = null;
    Double varianceStateDiscovery = null;   
    Integer firstStateDiscovery = null;
    Double averageFirstStateDiscovery = null;
    Double varianceFirstStateDiscovery = null;
    Integer lastStateDiscovery = null;
    Double averageLastStateDiscovery = null;
    Double varianceLastStateDiscovery= null;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.entities);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntitiesStatisticHolder other = (EntitiesStatisticHolder) obj;
        if (!Objects.equals(this.entities, other.entities)) {
            return false;
        }
        return true;
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

    
    
    
    public TreeSet<StateActionEntity> getEntities() {
        return entities;
    }

    public void setEntities(TreeSet<StateActionEntity> entities) {
        this.entities = entities;
    }

    public Double getMaxClassification() {
        return maxClassification;
    }

    public void setMaxClassification(Double maxClassification) {
        this.maxClassification = maxClassification;
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

    public Integer getMaxSubsetSize() {
        return maxSubsetSize;
    }

    public void setMaxSubsetSize(Integer maxSubsetSize) {
        this.maxSubsetSize = maxSubsetSize;
    }

    public Integer getMinSubsetSize() {
        return minSubsetSize;
    }

    public void setMinSubsetSize(Integer minSubsetSize) {
        this.minSubsetSize = minSubsetSize;
    }

    public Double getMaxStateValue() {
        return maxStateValue;
    }

    public void setMaxStateValue(Double maxStateValue) {
        this.maxStateValue = maxStateValue;
    }

    public Double getAverageSubsetDifferentation() {
        return averageSubsetDifferentation;
    }

    public void setAverageSubsetDifferentation(Double averageSubsetDifferentation) {
        this.averageSubsetDifferentation = averageSubsetDifferentation;
    }

    public TreeMap<Feature, Integer> getFeatureOccurenceIndexOrdered() {
        return featureOccurenceIndexOrdered;
    }

    public void setFeatureOccurenceIndexOrdered(TreeMap<Feature, Integer> featureOccurenceIndexOrdered) {
        this.featureOccurenceIndexOrdered = featureOccurenceIndexOrdered;
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

    public Integer getFirstStateVisit() {
        return firstStateVisit;
    }

    public void setFirstStateVisit(Integer firstStateVisit) {
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

    public Integer getFirstStateDiscovery() {
        return firstStateDiscovery;
    }

    public void setFirstStateDiscovery(Integer firstStateDiscovery) {
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

    public Double getMaxWholeFeaturesSetClassificationDifference() {
        return maxWholeFeaturesSetClassificationDifference;
    }

    public void setMaxWholeFeaturesSetClassificationDifference(Double maxWholeFeaturesSetClassificationDifference) {
        this.maxWholeFeaturesSetClassificationDifference = maxWholeFeaturesSetClassificationDifference;
    }

    public Double getMinWholeFeaturesSetClassificationDifference() {
        return minWholeFeaturesSetClassificationDifference;
    }

    public void setMinWholeFeaturesSetClassificationDifference(Double minWholeFeaturesSetClassificationDifference) {
        this.minWholeFeaturesSetClassificationDifference = minWholeFeaturesSetClassificationDifference;
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

    public Integer getMaxStateVisit() {
        return maxStateVisit;
    }

    public void setMaxStateVisit(Integer maxStateVisit) {
        this.maxStateVisit = maxStateVisit;
    }

    public Integer getMinStateVisit() {
        return minStateVisit;
    }

    public void setMinStateVisit(Integer minStateVisit) {
        this.minStateVisit = minStateVisit;
    }

    public Integer getMaxStateDiscovery() {
        return maxStateDiscovery;
    }

    public void setMaxStateDiscovery(Integer maxStateDiscovery) {
        this.maxStateDiscovery = maxStateDiscovery;
    }

    public Integer getMinStateDiscovery() {
        return minStateDiscovery;
    }

    public void setMinStateDiscovery(Integer minStateDiscovery) {
        this.minStateDiscovery = minStateDiscovery;
    }

    public Integer getLastStateVisit() {
        return lastStateVisit;
    }

    public void setLastStateVisit(Integer lastStateVisit) {
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

    public Integer getLastStateDiscovery() {
        return lastStateDiscovery;
    }

    public void setLastStateDiscovery(Integer lastStateDiscovery) {
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

    public StateActionEntity getBestValuedState() {
        return bestValuedState;
    }

    public void setBestValuedState(StateActionEntity bestValuedState) {
        this.bestValuedState = bestValuedState;
    }

    public StateActionEntity getBestClassifiedState() {
        return bestClassifiedState;
    }

    public void setBestClassifiedState(StateActionEntity bestClassifiedState) {
        this.bestClassifiedState = bestClassifiedState;
    }

    public StateActionEntity getBestValuedByTestSetState() {
        return bestValuedByTestSetState;
    }

    public void setBestValuedByTestSetState(StateActionEntity bestValuedByTestSetState) {
        this.bestValuedByTestSetState = bestValuedByTestSetState;
    }

    public StateActionEntity getBestClassifiedByTestSetState() {
        return bestClassifiedByTestSetState;
    }

    public void setBestClassifiedByTestSetState(StateActionEntity bestClassifiedByTestSetState) {
        this.bestClassifiedByTestSetState = bestClassifiedByTestSetState;
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

    public StateActionEntity getBestNotOverfittedStateByValue() {
        return bestNotOverfittedStateByValue;
    }

    public void setBestNotOverfittedStateByValue(StateActionEntity bestNotOverfittedStateByValue) {
        this.bestNotOverfittedStateByValue = bestNotOverfittedStateByValue;
    }

    public StateActionEntity getBestNotOverfittedStateByClassification() {
        return bestNotOverfittedStateByClassification;
    }

    public void setBestNotOverfittedStateByClassification(StateActionEntity bestNotOverfittedStateByClassification) {
        this.bestNotOverfittedStateByClassification = bestNotOverfittedStateByClassification;
    }

   

    
    
    
    
    
    
    
    
    
}
