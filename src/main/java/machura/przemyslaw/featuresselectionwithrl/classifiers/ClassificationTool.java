/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.classifiers;

import java.util.BitSet;
import java.util.Random;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import machura.przemyslaw.featuresselectionwithrl.helpers.DataSetHelper;
import weka.attributeSelection.ClassifierSubsetEval;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 *
 * @author Przemek
 */
public class ClassificationTool {

    Integer folds = 5;
    Classifier classifier;
    ClassifierSubsetEval classifierSubsetEval = new ClassifierSubsetEval();
    WrapperSubsetEval wrapperSubsetEval = new WrapperSubsetEval();

    public ClassificationTool(Classifier classifier) {
        this.classifier = classifier;
        classifierSubsetEval.setClassifier(classifier);
        wrapperSubsetEval.setClassifier(classifier);
        wrapperSubsetEval.setFolds(folds);

    }

    public ClassificationTool(Classifier classifier, Instances data) throws Exception {
        this(classifier);
        classifier.buildClassifier(data);
        classifierSubsetEval.setClassifier(classifier);
        wrapperSubsetEval.setClassifier(classifier);
        classifierSubsetEval.buildEvaluator(data);
        wrapperSubsetEval.buildEvaluator(data);
    }

    public void buildClassifierSubsetEvaluator(Instances data) throws Exception {
        classifierSubsetEval.buildEvaluator(data);
    }

    public void buildWrapperSubsetEvaluator(Instances data) throws Exception {
        wrapperSubsetEval.buildEvaluator(data);
    }

    public void buildSubsetEvaluators(Instances data) throws Exception {
        buildClassifierSubsetEvaluator(data);
        buildWrapperSubsetEvaluator(data);
    }

    public void buildClassifier(Instances data) throws Exception {
        classifier.buildClassifier(data);
    }

    public void buildClassifierByFeatureSubset(Instances data, TreeSet<Integer> featuresToKeep) throws Exception {
        Instances newData = DataSetHelper.removeOtherAttributes(data, featuresToKeep);
        classifier.buildClassifier(newData);

    }

    public Double evaluateSubsetByWrapperSubsetEvaluator(BitSet features) throws Exception {
        return wrapperSubsetEval.evaluateSubset(features);

    }

    public Double evaluateSubsetByClassifierSubsetEvaluator(BitSet features) throws Exception {
        return classifierSubsetEval.evaluateSubset(features);
    }

    public Double evaluateSubsetByTestDataWithClassifierSubsetEvaluator(BitSet features, Instances testData) throws Exception {
        return classifierSubsetEval.evaluateSubset(features, testData);

    }

    public Double evaluateClassifierByTestData(Instances train, Instances test) throws Exception {
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(classifier, test);
        return eval.pctCorrect();
    }

    public Double trainAndEvaluateClassifierByTestData(Instances train, Instances test) throws Exception {
        Evaluation eval = new Evaluation(train);
        classifier.buildClassifier(train);
        eval.evaluateModel(classifier, test);
        return eval.pctCorrect();

    }

    public Double evaluateClassifierByCrossValidation(Instances data) throws Exception {
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(classifier, data, folds, new Random(1));
        return eval.pctCorrect();

    }
    
    public Double trainAndEvaluateClassifierByCrossValidation(Instances data) throws Exception {
        Evaluation eval = new Evaluation(data);
        classifier.buildClassifier(data);
        eval.crossValidateModel(classifier, data, folds, new Random(1));
        return eval.pctCorrect();

    }

    public void setClassifierInEvaluators() {
        this.classifierSubsetEval.setClassifier(classifier);
        this.wrapperSubsetEval.setClassifier(classifier);
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public Integer getFolds() {
        return folds;
    }

    public void setFolds(Integer folds) {
        this.folds = folds;
    }
    
    
    
    
    
}
