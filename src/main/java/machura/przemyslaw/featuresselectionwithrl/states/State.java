/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.states;

import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author Przemek
 */
public class State {

    TreeSet<Feature> state;

    TreeSet<Integer> timeSteps;
    Integer visitingCount;
    TreeSet<Integer> discoveryTimeSteps;
    Integer discoveryCount;

    Double value;
    Double classificationEvaluation;
    Double valueByTestSet;
    Double classificationEvaluationByTestSet;
    Double valueByLearningSet;
    Double classificationEvaluationByLearningSet;
    Double trainAndTestClassificationDifference; 
    Double trainAndTestValueDifference;
    Double learnAndTestClassificationDifference;
    Double learnAndTestValueDifference;
    Double wholeFeaturesSetClassificationByTestSetDiffrence;
    Double wholeFeaturesSetValueByTestSetDiffrence;
    Double wholeFeaturesSetClassificationByLearningSetDifference;
    Double wholeFeaturesSetValueByLearningSetDifference;

    public State() {
        this.state = new TreeSet<Feature>();
        this.value = 0.0;
        this.classificationEvaluation = null;
        this.timeSteps = new TreeSet<Integer>();
        this.visitingCount = 0;
        this.discoveryTimeSteps = new TreeSet<Integer>();
        this.discoveryCount = 0;
        this.classificationEvaluationByTestSet = null;
        this.valueByTestSet = null;
        this.trainAndTestClassificationDifference = null;
        this.trainAndTestValueDifference = null;
        this.wholeFeaturesSetClassificationByTestSetDiffrence = null;
        this.wholeFeaturesSetClassificationByLearningSetDifference = null;//
        this.wholeFeaturesSetValueByLearningSetDifference = null;//
        this.learnAndTestClassificationDifference = null;//
        this.learnAndTestValueDifference = null;//
        this.valueByLearningSet = null;//
        this.classificationEvaluationByLearningSet = null;//
    }

    public State(TreeSet<Feature> state) {
        this();
        this.state = new TreeSet<Feature>(state);
    }

    public void computeTrainAndTestEvaluationDifference() {
        this.trainAndTestClassificationDifference = this.classificationEvaluation - this.classificationEvaluationByTestSet;
        this.trainAndTestValueDifference = this.value - this.valueByTestSet;
    }

    public void computeLearnAndTestEvaluationDifference() {
        this.learnAndTestClassificationDifference = this.classificationEvaluationByLearningSet - this.classificationEvaluationByTestSet;
        this.learnAndTestValueDifference = this.valueByLearningSet - this.valueByTestSet;
    }

    public void computeWholeFeaturesClassificationDifference(Double classificationAccuracyForWholeFeatures) {
        this.wholeFeaturesSetClassificationByTestSetDiffrence = this.classificationEvaluationByTestSet - classificationAccuracyForWholeFeatures;
    }

    public void computeWholeFeaturesValueDifference(Double valueForWholeFeatures) {
        this.wholeFeaturesSetValueByTestSetDiffrence = this.valueByTestSet - valueForWholeFeatures;
    }

    public void computeWholeFeaturesSetClassificationByLearningSetDifference(Double classificationAccuracyForWholeFeaturesByLearningSet) {
        this.wholeFeaturesSetClassificationByLearningSetDifference = this.classificationEvaluationByLearningSet - classificationAccuracyForWholeFeaturesByLearningSet;
    }

    public void computeWholeFeaturesSetValueByLearningSetDifference(Double wholeFeaturesValueByLearningSet) {
        this.wholeFeaturesSetValueByLearningSetDifference = this.valueByLearningSet - wholeFeaturesValueByLearningSet;
    }

    public boolean addTimeStep(Integer timeStep) {
        boolean added = timeSteps.add(timeStep);
        if (added) {
            visitingCount++;
        }

        return added;
    }

    public boolean addDiscoveryTimeStep(Integer timeStep) {
        boolean added = discoveryTimeSteps.add(timeStep);
        if (added) {
            discoveryCount++;
        }

        return added;
    }

    public void increasVisitingCoung() {
        visitingCount++;
    }

    public Integer getFeaturesNumber() {
        return state.size();
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isEvaluatedByClassifier() {
        return classificationEvaluation == null ? false : true;
    }

    public void setclassificationEvaluation(Double classificationEvaluation) {
        this.classificationEvaluation = classificationEvaluation;
    }

    public boolean isState(TreeSet<Feature> state) {
        return this.state.equals(state);
    }

    public TreeSet<Integer> getFeaturesIndexes() {
        TreeSet<Integer> indexes = new TreeSet<Integer>();
        for (Feature feature : state) {
            indexes.add(feature.getIndex());
        }
        return indexes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.state);
        hash = 73 * hash + Objects.hashCode(this.timeSteps);
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
        final State other = (State) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder description = new StringBuilder("STAN: [");
        state.forEach((Feature feature) -> {
            description.append(feature + " ");
        });
        description.append("]");
        description.append("  Liczba cech: [" + state.size() + "]");
        description.append("\n");

        description.append("Był wyceniany przez klasyfikator ?: " + isEvaluatedByClassifier());
        description.append("\n");
        description.append("Ocena klasyfikacji: " + classificationEvaluation);
        description.append("\n");
        description.append("Ilość wizyt: " + visitingCount);
        description.append("\n");

        return description.toString();
    }

    public Double getClassificationEvaluation() {
        return classificationEvaluation;
    }

    public Double getValue() {
        return value;
    }

    public TreeSet<Feature> getState() {
        return state;
    }

    public Integer getVisitingCount() {
        return visitingCount;
    }

    public TreeSet<Integer> getTimeSteps() {
        return timeSteps;
    }

    public TreeSet<Integer> getDiscoveryTimeSteps() {
        return discoveryTimeSteps;
    }

    public void setDiscoveryTimeSteps(TreeSet<Integer> discoveryTimeSteps) {
        this.discoveryTimeSteps = discoveryTimeSteps;
    }

    public Integer getDiscoveryCount() {
        return discoveryCount;
    }

    public void setDiscoveryCount(Integer discoveryCount) {
        this.discoveryCount = discoveryCount;
    }

    public Double getClassificationEvaluationByTestSet() {
        return classificationEvaluationByTestSet;
    }

    public void setClassificationEvaluationByTestSet(Double classificationEvaluationByTestSet) {
        this.classificationEvaluationByTestSet = classificationEvaluationByTestSet;
    }

    public Double getValueByTestSet() {
        return valueByTestSet;
    }

    public void setValueByTestSet(Double valueByTestSet) {
        this.valueByTestSet = valueByTestSet;
    }

    public Double getTrainAndTestClassificationDifference() {
        return trainAndTestClassificationDifference;
    }

    public void setTrainAndTestClassificationDifference(Double trainAndTestClassificationDifference) {
        this.trainAndTestClassificationDifference = trainAndTestClassificationDifference;
    }

    public Double getTrainAndTestValueDifference() {
        return trainAndTestValueDifference;
    }

    public void setTrainAndTestValueDifference(Double trainAndTestValueDifference) {
        this.trainAndTestValueDifference = trainAndTestValueDifference;
    }

    public Double getWholeFeaturesSetClassificationByTestSetDiffrence() {
        return wholeFeaturesSetClassificationByTestSetDiffrence;
    }

    public void setWholeFeaturesSetClassificationByTestSetDiffrence(Double wholeFeaturesSetClassificationByTestSetDiffrence) {
        this.wholeFeaturesSetClassificationByTestSetDiffrence = wholeFeaturesSetClassificationByTestSetDiffrence;
    }

    public Double getWholeFeaturesSetValueByTestSetDiffrence() {
        return wholeFeaturesSetValueByTestSetDiffrence;
    }

    public void setWholeFeaturesSetValueByTestSetDiffrence(Double wholeFeaturesSetValueByTestSetDiffrence) {
        this.wholeFeaturesSetValueByTestSetDiffrence = wholeFeaturesSetValueByTestSetDiffrence;
    }

    public Double getValueByLearningSet() {
        return valueByLearningSet;
    }

    public void setValueByLearningSet(Double valueByLearningSet) {
        this.valueByLearningSet = valueByLearningSet;
    }

    public Double getClassificationEvaluationByLearningSet() {
        return classificationEvaluationByLearningSet;
    }

    public void setClassificationEvaluationByLearningSet(Double classificationEvaluationByLearningSet) {
        this.classificationEvaluationByLearningSet = classificationEvaluationByLearningSet;
    }

    public Double getLearnAndTestClassificationDifference() {
        return learnAndTestClassificationDifference;
    }

    public void setLearnAndTestClassificationDifference(Double learnAndTestClassificationDifference) {
        this.learnAndTestClassificationDifference = learnAndTestClassificationDifference;
    }

    public Double getLearnAndTestValueDifference() {
        return learnAndTestValueDifference;
    }

    public void setLearnAndTestValueDifference(Double learnAndTestValueDifference) {
        this.learnAndTestValueDifference = learnAndTestValueDifference;
    }

    public Double getWholeFeaturesSetClassificationByLearningSetDifference() {
        return wholeFeaturesSetClassificationByLearningSetDifference;
    }

    public void setWholeFeaturesSetClassificationByLearningSetDifference(Double wholeFeaturesSetClassificationByLearningSetDifference) {
        this.wholeFeaturesSetClassificationByLearningSetDifference = wholeFeaturesSetClassificationByLearningSetDifference;
    }

    public Double getWholeFeaturesSetValueByLearningSetDifference() {
        return wholeFeaturesSetValueByLearningSetDifference;
    }

    public void setWholeFeaturesSetValueByLearningSetDifference(Double wholeFeaturesSetValueByLearningSetDifference) {
        this.wholeFeaturesSetValueByLearningSetDifference = wholeFeaturesSetValueByLearningSetDifference;
    }

}
