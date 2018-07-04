/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.helpers;

import machura.przemyslaw.featuresselectionwithrl.filehandlers.InstanceFileHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 *
 * @author Przemek
 */
public class DataSetHelper {

    public static Map<DataCategory, Instances> holdOutSplit(Instances instances, double percentOfTrainingData) {
        Map<DataCategory, Instances> splitedInstances = new HashMap<DataCategory, Instances>();
        try {
            Random random = new Random(1);

            Instances randomizedInstances = new Instances(instances);
            randomizedInstances.randomize(random);

            RemovePercentage removePercentage = new RemovePercentage();
            removePercentage.setPercentage(percentOfTrainingData);
            removePercentage.setInvertSelection(true);
            removePercentage.setInputFormat(randomizedInstances);

            Instances trainingInstances = Filter.useFilter(randomizedInstances, removePercentage);

            removePercentage.setInvertSelection(false);
            removePercentage.setInputFormat(randomizedInstances);
            Instances testingInstances = Filter.useFilter(randomizedInstances, removePercentage);

            splitedInstances.put(DataCategory.TRAINING, trainingInstances);
            splitedInstances.put(DataCategory.TEST, testingInstances);

        } catch (Exception ex) {
            Logger.getLogger(DataSetHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return splitedInstances;
    }

    
    public static Instances removeOtherAttributes(Instances data, TreeSet<Integer> attributesToKeep) throws Exception {

        String classAttributeName = data.classAttribute().name();
        TreeSet<Integer> attributesToKeepCpy = new TreeSet<Integer>(attributesToKeep);
        attributesToKeepCpy.add(data.classIndex());
        int[] arrayOfttributesToKeep = Arrays.stream(attributesToKeepCpy.toArray(new Integer[attributesToKeepCpy.size()])).mapToInt(Integer::intValue).toArray();
        
        Debuger.println("ATRYBUTY DO ZACHOWANIA ");
        for (int i : arrayOfttributesToKeep) {
            System.out.print(i + " ");
        }
        Debuger.println("");
        
        Remove remove = new Remove();
        remove.setInvertSelection(true);
        remove.setAttributeIndicesArray(arrayOfttributesToKeep);
        remove.setInputFormat(data);

        Instances resultInstances = Filter.useFilter(data, remove);
        resultInstances.setClass(resultInstances.attribute(classAttributeName));
        
        Debuger.println("Zbiór wejściowy klasa: " + data.classAttribute().name() + "  ");
        for (int j = 0; j < resultInstances.numAttributes(); j++) {
            System.out.print("  " + resultInstances.attribute(j));
        }
        Debuger.println("");
        
        Debuger.println("Zbiór wynikowy klasa: " + resultInstances.classAttribute().name()+ "  ");
        for (int j = 0; j < resultInstances.numAttributes(); j++) {
            System.out.print("  " + resultInstances.attribute(j));
        }
        Debuger.println("");
        
        return resultInstances;

    }

    public static Instances removeOtherAttributes_2(Instances data, TreeSet<Feature> attributesToKeep) throws Exception {

        int[] arrayOfttributesToKeep = Arrays.stream(attributesToKeep.stream().map((Feature f) -> f.getIndex()).toArray()).mapToInt(Integer::intValue).toArray();
        Debuger.println("ATRYBUTY DO ZACHOWANIA ");
        for (int i : arrayOfttributesToKeep) {
            System.out.print(i + " ");
        }
        System.out.println();
        Remove remove = new Remove();
        remove.setInvertSelection(true);
        remove.setInputFormat(data);

        remove.setAttributeIndicesArray(arrayOfttributesToKeep);
        Instances resultInstances = Filter.useFilter(data, remove);
        resultInstances.setClassIndex(resultInstances.numAttributes() - 1);
        return resultInstances;

    }

    public static void addAttributeWithValues(Instances data, Instances subData, Integer attributeIndexInData) {
        subData.insertAttributeAt((Attribute) data.attribute(attributeIndexInData).copy(), 0);

        Integer numInstances = data.numInstances();

        if (data.attribute(attributeIndexInData).isNumeric()) {
            for (int i = 0; i < numInstances; i++) {
                subData.instance(i).setValue(0, data.instance(i).value(attributeIndexInData));
            }
        } else {
            for (int i = 0; i < numInstances; i++) {
                subData.instance(i).setValue(0, data.instance(i).stringValue(attributeIndexInData));
            }
        }
    }

    public static Map<DataCategory, Instances> holdOutClassProportionalSplit(Instances instances, Double percentOfTrainingData) {
        if (percentOfTrainingData > 1.0 || percentOfTrainingData < 0.0 || percentOfTrainingData == null) {
            percentOfTrainingData = 0.8;
        }
        Integer classesNumber = instances.numClasses();
        HashMap<Double, Integer> classInstancesCounter = new HashMap<Double, Integer>();
        Instances training = new Instances(instances);
        Instances testing = new Instances(instances);

        Debuger.println("Wszystkie instancje: " + instances.size());

        for (int i = 0; i < instances.size(); i++) {
            Double classValue = instances.get(i).classValue();
            if (classInstancesCounter.containsKey(classValue)) {
                Integer currentCount = classInstancesCounter.get(classValue);
                classInstancesCounter.put(classValue, currentCount + 1);
            } else {
                classInstancesCounter.put(classValue, 1);
            }
        }
        Debuger.println("Rozkład poszczególnych klas");
        for (Entry<Double, Integer> entry : classInstancesCounter.entrySet()) {
            Debuger.println("Klasa: " + entry.getKey() + "  " + "Liczebność: " + entry.getValue());
        }

        HashMap<Double, Integer> classInstancesForTrainingCount = new HashMap<Double, Integer>();
        for (Entry<Double, Integer> entry : classInstancesCounter.entrySet()) {
            classInstancesForTrainingCount.put(entry.getKey(), (int) Math.round(percentOfTrainingData * entry.getValue()));
        }
        Debuger.println("Zakładany rozkład poszczególnych klas dla zbioru treningowego");
        for (Entry<Double, Integer> entry : classInstancesForTrainingCount.entrySet()) {
            Debuger.println("Klasa: " + entry.getKey() + "  " + "Liczebność: " + entry.getValue());
        }

        ArrayList<Instance> trainingList = new ArrayList<Instance>();
        ArrayList<Instance> testingList = new ArrayList<Instance>();
        
        for (int i = 0; i < instances.size(); i++) {
            Instance instance = instances.get(i);
            Double classValue = instance.classValue();
            if (classInstancesForTrainingCount.get(classValue) > 0) {
                trainingList.add((Instance) instance.copy());
                classInstancesForTrainingCount.put(classValue, classInstancesForTrainingCount.get(classValue) - 1);
            } else {
                testingList.add((Instance) instance.copy());
            }
        }
        training.clear();
        training.addAll(trainingList);
        testing.clear();
        testing.addAll(testingList);

        Debuger.println("Po obróbce");
        Debuger.println("Wszystkie instancje: " + instances.size());
        Debuger.println("Instancje treningowe: " + training.size());
        Debuger.println("Instancje testowe: " + testing.size());

        classInstancesCounter = new HashMap<Double, Integer>();
        for (int i = 0; i < testing.size(); i++) {
            Double classValue = testing.get(i).classValue();
            if (classInstancesCounter.containsKey(classValue)) {
                Integer currentCount = classInstancesCounter.get(classValue);
                classInstancesCounter.put(classValue, currentCount + 1);
            } else {
                classInstancesCounter.put(classValue, 1);
            }
        }
        Debuger.println("Rozkład poszczególnych klas zbioru testowego");
        for (Entry<Double, Integer> entry : classInstancesCounter.entrySet()) {
            Debuger.println("Klasa: " + entry.getKey() + "  " + "Liczebność: " + entry.getValue());
        }

        classInstancesCounter = new HashMap<Double, Integer>();
        for (int i = 0; i < training.size(); i++) {
            Double classValue = training.get(i).classValue();
            if (classInstancesCounter.containsKey(classValue)) {
                Integer currentCount = classInstancesCounter.get(classValue);
                classInstancesCounter.put(classValue, currentCount + 1);
            } else {
                classInstancesCounter.put(classValue, 1);
            }
        }
        Debuger.println("Rozkład poszczególnych klas zbioru trenignowego");
        for (Entry<Double, Integer> entry : classInstancesCounter.entrySet()) {
            Debuger.println("Klasa: " + entry.getKey() + "  " + "Liczebność: " + entry.getValue());
        }

        Map<DataCategory, Instances> splitedInstances = new HashMap<DataCategory, Instances>();
        splitedInstances.put(DataCategory.TEST, testing);
        splitedInstances.put(DataCategory.TRAINING, training);
        return splitedInstances;
    }

    private static final String irisDataSet = "groups/uci/nominal/iris.arff";
    private static final String waveformDataSet = "groups/uci/nominal/waveform-5000.arff";

}
