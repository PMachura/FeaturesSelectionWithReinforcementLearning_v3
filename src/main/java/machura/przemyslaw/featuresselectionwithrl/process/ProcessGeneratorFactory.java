/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process;

import java.util.ArrayList;
import machura.przemyslaw.featuresselectionwithrl.filehandlers.InstanceFileHandler;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationTool;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationToolFactory;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerBackToBestNoImprovement;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerBackToBestRegular;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerSimulatedActions;
import machura.przemyslaw.featuresselectionwithrl.returns.RewardGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedyManyFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedySingleFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.ConstantProbabilityGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.PowerFunctionProbabilityGenerator;
import machura.przemyslaw.featuresselectionwithrl.updaters.QUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.SarsaNStepUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.SarsaUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.StateValueEntityUpdater;
import weka.core.Instances;

/**
 *
 * @author Przemek
 */
public class ProcessGeneratorFactory {

    private static final String soybeanDataSet = "groups/uci/nominal/soybean.arff";
    private static final String optdigitsDataSet = "groups/uci/nominal/optdigits.arff";
    private static final String arrhyyhmiaDataSet = "groups/uci/nominal/arrhythmia.arff";
    private static final String spambaseDataSet = "groups/uci/nominal/spambase.arff";
    private static final String mfeatpixelDataSet = "groups/uci/nominal/mfeat-pixel.arff";


    /**
     * Ekpseryment 1 ma na celu pokazanie różnicy w generowanych rozmiarach
     * zbiorów na bazie zmiany sposobu obliczania prawdoposobieństwa dodania
     * atrybutu
     */
    private static class ProcessGenerator_1_1 extends ProcessGenerator {

        ProcessGenerator_1_1(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_1(String name) {
            super(name);
        }

        ProcessGenerator_1_1(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_1_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {
            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }


            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.3);

            ConstantProbabilityGenerator constantProbabilityGenerator = new ConstantProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(constantProbabilityGenerator);

            RewardGenerator rewardGenerator = new RewardGenerator();

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            process.setDescription("Proces ze stałym prawdopodobieństwem dadania atrybutu, bez zmiany trendu");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_1_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_1_1("Prosty proces_1_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_1_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_1_1("Prosty proces_1_1", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_1_1() {
        return new ProcessGenerator_1_1("Prosty proces_1_1");
    }

    private static class ProcessGenerator_1_2 extends ProcessGenerator {

        ProcessGenerator_1_2(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_2(String name) {
            super(name);
        }

        ProcessGenerator_1_2(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_1_2(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.3);

            ConstantProbabilityGenerator constantProbabilityGenerator = new ConstantProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(constantProbabilityGenerator);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            process.setDescription("Proces ze stałym prawdopodobieństwem dadania atrybutu, ze zmianą trendu prawdopodobieństwa");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_1_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_1_2("Prosty proces_1_2", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_1_2() {
        return new ProcessGenerator_1_2("Prosty proces_1_2");
    }

    public static ProcessGenerator ProcessGenerator_1_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_1_2("Prosty proces_1_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_1_3 extends ProcessGenerator {

        ProcessGenerator_1_3(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_3(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_3(String name) {
            super(name);
        }

        ProcessGenerator_1_3(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_1_3(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {
            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }
  
            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.3);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);

            RewardGenerator rewardGenerator = new RewardGenerator();

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);


            process.setDescription("Proces z potęgową funkcją generującą prawdopodobieństwo dodania atrybutu, bez zmiany trendu prawdopodobieństwa");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_1_3(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_1_3("Prosty proces_1_3", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_1_3() {
        return new ProcessGenerator_1_3("Prosty proces_1_3");
    }

    public static ProcessGenerator ProcessGenerator_1_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_1_3("Prosty proces_1_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_1_4 extends ProcessGenerator {

        ProcessGenerator_1_4(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_4(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_1_4(String name) {
            super(name);
        }

        ProcessGenerator_1_4(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_1_4(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {
            
            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }
            
            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.3);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
 
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            process.setDescription("Proces z potęgową funkcją generującą prawdopodobieństwo dodania atrybutu, ze zmianą trendu prawdopodobieństwa");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_1_4(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_1_4("Prosty proces_1_4", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_1_4() {
        return new ProcessGenerator_1_4("Prosty proces_1_4");
    }

    public static ProcessGenerator ProcessGenerator_1_4(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_1_4("Prosty proces_1_4", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    /**
     * Ekpseryment 1 ma na celu pokazanie różnicy w generowanych rozmiarach
     * zbiorów na bazie zmiany sposobu obliczania prawdoposobieństwa dodania
     * atrybutu
     */
    public static ArrayList<ProcessGenerator> ProcessGenerators_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_1_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces ze stałym prawdopodobieństwem dadania atrybutu, bez zmiany trendu 0.7
        processGenerators.add(ProcessGenerator_1_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces ze stałym prawdopodobieństwem dadania atrybutu, ze zmianą trendu prawdopodobieństwa 
        processGenerators.add(ProcessGenerator_1_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces z potęgową funkcją generującą prawdopodobieństwo dodania atrybutu, bez zmiany trendu prawdopodobieństwa 3
        processGenerators.add(ProcessGenerator_1_4(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces z potęgową funkcją generującą prawdopodobieństwo dodania atrybutu, ze zmianą trendu prawdopodobieństwa
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_1_1(stepsNumber, classificationTool, dataFile));
        processGenerators.add(ProcessGenerator_1_2(stepsNumber, classificationTool, dataFile));
        processGenerators.add(ProcessGenerator_1_3(stepsNumber, classificationTool, dataFile));
        processGenerators.add(ProcessGenerator_1_4(stepsNumber, classificationTool, dataFile));
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_1_1() {
        Integer stepsNumber = 3000;
        Integer maxFeaturesSubsetSize = 60;
        ClassificationTool classificationTool = ClassificationToolFactory.createJ48ClassificationTool();
        StateValueEntityUpdater entityUpdater = new QUpdater();
        String dataFile = arrhyyhmiaDataSet;
        return ProcessGenerators_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);

    }

    /**
     * Ekpseryment 2 ma na celu pokazanie różnicy w generowanych rozmiarach
     * zbiorów w zależności od współczynnika istotności rozmiaru zbioru, którym
     * posługuje się generator nagród
     */
    private static class ProcessGenerator_2_1 extends ProcessGenerator {

        ProcessGenerator_2_1(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_2_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_2_1(String name) {
            super(name);
        }

        ProcessGenerator_2_1(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_2_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.8);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.3);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(50);
            process.addPlanner(plannerBackToBestRegular);

            process.setDescription("Proces z istotnością rozmiaru zbioru 0.3");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_2_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_2_1("Prosty proces_2_1", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_2_1() {
        return new ProcessGenerator_2_1("Prosty proces_2_1");
    }

    public static ProcessGenerator ProcessGenerator_2_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_2_1("Prosty proces_2_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_2_2 extends ProcessGenerator {

        ProcessGenerator_2_2(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_2_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_2_2(String name) {
            super(name);
        }

        ProcessGenerator_2_2(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_2_2(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {
            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.8);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
 
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.5);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(50);

            process.setDescription("Proces z istotnością rozmiaru zbioru 0.5");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_2_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_2_2("Prosty proces_2_2", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_2_2() {
        return new ProcessGenerator_2_2("Prosty proces_2_2");
    }

    public static ProcessGenerator ProcessGenerator_2_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_2_2("Prosty proces_2_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_2_3 extends ProcessGenerator {

        ProcessGenerator_2_3(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_2_3(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_2_3(String name) {
            super(name);
        }

        ProcessGenerator_2_3(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_2_3(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {
            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.8);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.8);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(50);

            process.setDescription("Proces z istotnością rozmiaru zbioru 0.8");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_2_3(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_2_3("Prosty proces_2_3", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_2_3() {
        return new ProcessGenerator_2_3("Prosty proces_2_3");
    }

    public static ProcessGenerator ProcessGenerator_2_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_2_3("Prosty proces_2_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    /**
     * Ekpseryment 2 ma na celu pokazanie różnicy w generowanych rozmiarach
     * zbiorów w zależności od współczynnika istotności rozmiaru zbioru, którym
     * posługuje się generator nagród. Duże prwd. wyboru akcji zachłannie
     * Wszystkie mają powrót do stanów najlepszych co 50 kroków i stałe
     * prawdopodobieństwo zachłannej akcji 0.8
     */
    public static ArrayList<ProcessGenerator> ProcessGenerators_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_2_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //0.3
        processGenerators.add(ProcessGenerator_2_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //0.5
        processGenerators.add(ProcessGenerator_2_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //0.8
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_2_1(stepsNumber, classificationTool, dataFile));
        processGenerators.add(ProcessGenerator_2_2(stepsNumber, classificationTool, dataFile));
        processGenerators.add(ProcessGenerator_2_3(stepsNumber, classificationTool, dataFile));
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_2_1() {
        Integer stepsNumber = 3000;
        Integer maxFeaturesSubsetSize = 60;
        ClassificationTool classificationTool = ClassificationToolFactory.createJ48ClassificationTool();
        StateValueEntityUpdater entityUpdater = new QUpdater();
        String dataFile = arrhyyhmiaDataSet;
        return ProcessGenerators_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);

    }


    /**
     * Ekpseryment 3 ma na celu pokazanie różnic w przypadku użycia bądź nie
     * algorytmów planujących powrotu do stanu najlepszego
     */
    private static class ProcessGenerator_3_1 extends ProcessGenerator {

        ProcessGenerator_3_1(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_1(String name) {
            super(name);
        }

        ProcessGenerator_3_1(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_3_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            process.setDescription("Proces bez planowania");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_3_1(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_3_1("Prosty proces_3_1", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_3_1() {
        return new ProcessGenerator_3_1("Prosty proces_3_1");
    }

    public static ProcessGenerator ProcessGenerator_3_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_3_1("Prosty proces_3_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_3_2 extends ProcessGenerator {

        ProcessGenerator_3_2(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_2(String name) {
            super(name);
        }

        ProcessGenerator_3_2(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_3_2(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(100);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych");
            return process;

        }
    }

    public static ProcessGenerator ProcessGenerator_3_2(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_3_2("Prosty proces_3_2", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_3_2() {
        return new ProcessGenerator_3_2("Prosty proces_3_2");
    }

    public static ProcessGenerator ProcessGenerator_3_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_3_2("Prosty proces_3_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_3_3 extends ProcessGenerator {

        ProcessGenerator_3_3(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_3(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_3_3(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(50);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych");
            return process;

        }
    }

    public static ProcessGenerator ProcessGenerator_3_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_3_3("Prosty proces_3_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_3_3(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_3_3("Prosty proces_3_3", stepsNumber, classificationTool, dataFile);
    }

    private static class ProcessGenerator_3_4 extends ProcessGenerator {

        ProcessGenerator_3_4(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_4(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_3_4(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych");
            return process;

        }
    }

    public static ProcessGenerator ProcessGenerator_3_4(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_3_4("Prosty proces_3_4", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_3_4(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_3_4("Prosty proces_3_4", stepsNumber, classificationTool, dataFile);
    }

    private static class ProcessGenerator_3_5 extends ProcessGenerator {

        ProcessGenerator_3_5(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        
        ProcessGenerator_3_5(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_3_5(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(15);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych");
            return process;

        }
    }

    public static ProcessGenerator ProcessGenerator_3_5(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_3_5("Prosty proces_3_5", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_3_5(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_3_5("Prosty proces_3_5", stepsNumber, classificationTool, dataFile);
    }

    private static class ProcessGenerator_3_10 extends ProcessGenerator {

        ProcessGenerator_3_10(String name, Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(name, stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_10(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
            super(stepsNumber, classificationTool, dataFile);
        }

        ProcessGenerator_3_10(String name) {
            super(name);
        }

        ProcessGenerator_3_10(String name, Integer stepsNumber, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, classificationTool, entityUpdater, dataFile);
        }

        ProcessGenerator_3_10(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {
            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            ClassificationTool classificationTool = ClassificationToolFactory.createJ48ClassificationTool();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestNoImprovement plannerBackToBestNoImprovement = new PlannerBackToBestNoImprovement(process);
            plannerBackToBestNoImprovement.setNoImprovementStepsBeforeBackAsFractionOfProcessTimeSteps(0.01);

            process.addPlanner(plannerBackToBestNoImprovement);
            process.setDescription("Proces z powrotem do stanów najlepszych, w przypadku nie osiągnięcia poprawy");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_3_10(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        return new ProcessGenerator_3_10("Prosty proces_3_10", stepsNumber, classificationTool, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_3_10() {
        return new ProcessGenerator_3_10("Prosty proces_3_10");
    }

    public static ProcessGenerator ProcessGenerator_3_10(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_3_10("Prosty proces_3_10", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    /**
     * Ekpseryment 3 ma na celu pokazanie różnic w przypadku użycia bądź nie
     * algorytmów planujących powrotu do stanu najlepszego
     */
    public static ArrayList<ProcessGenerator> ProcessGenerators_3(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_3_1(stepsNumber, classificationTool, dataFile)); //Proces bez planowania
        processGenerators.add(ProcessGenerator_3_2(stepsNumber, classificationTool, dataFile)); //Proces z regularnym powrotem do stanów najlepszych co 100 kroków
        processGenerators.add(ProcessGenerator_3_3(stepsNumber, classificationTool, dataFile)); //Proces z regularnym powrotem do stanów najlepszych co 50 kroków
        processGenerators.add(ProcessGenerator_3_4(stepsNumber, classificationTool, dataFile)); //Proces z regularnym powrotem do stanów najlepszych co 25 kroków
        processGenerators.add(ProcessGenerator_3_5(stepsNumber, classificationTool, dataFile)); //Proces z regularnym powrotem do stanów najlepszych co 15 kroków
        processGenerators.add(ProcessGenerator_3_10(stepsNumber, classificationTool, dataFile)); //Proces z powrotem do stanów najlepszych, w przypadku nie osiągnięcia poprawy 0.01 * kroki czasu
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_3_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_3_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_3_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_3_4(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_3_5(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_3_10(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_3_1() {
        Integer stepsNumber = 30000;
        Integer maxFeaturesSubsetSize = null;
        ClassificationTool classificationTool = ClassificationToolFactory.createJ48ClassificationTool();
        StateValueEntityUpdater entityUpdater = new SarsaUpdater();
        String dataFile = soybeanDataSet;
        return ProcessGenerators_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);

    }
    
    /**
     * Ekpseryment 4 ma na celu pokazanie różnic w przypadku użycia bądź nie
     * algorytmów planujących symulowanie akcji. Dodatkowo można użyć, żeby
     * pokazać różnice między planowanymi powrotami , a bez planowanych powrotów
     */
    
    private static class ProcessGenerator_4_1 extends ProcessGenerator {

        ProcessGenerator_4_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }
            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            process.setDescription("Proces bez powrotów do stanów najlepszych, bez symulowania akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_4_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_4_1("Prosty proces_4_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_4_2 extends ProcessGenerator {

        ProcessGenerator_4_2(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, bez symulowania akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_4_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_4_2("Prosty proces_4_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_4_3 extends ProcessGenerator {

        ProcessGenerator_4_3(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);

            process.addPlanner(plannerSimulatedActions);
            process.setDescription("Proces bez powrotów do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_4_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_4_3("Prosty proces_4_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    private static class ProcessGenerator_4_4 extends ProcessGenerator {

        ProcessGenerator_4_4(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_4_4(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_4_4("Prosty proces_4_4", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    
    private static class ProcessGenerator_4_41 extends ProcessGenerator {

        ProcessGenerator_4_41(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            plannerSimulatedActions.setSimulatedActionNumber(5);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_4_41(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_4_41("Prosty proces_4_41", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    /**
     * Ekpseryment 4 ma na celu pokazanie różnic w przypadku użycia bądź nie
     * algorytmów planujących symulowanie akcji. Dodatkowo można użyć, żeby
     * pokazać różnice między planowanymi powrotami , a bez planowanych powrotów
     */
    public static ArrayList<ProcessGenerator> ProcessGenerators_4(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_4_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces bez powrotów do stanów najlepszych, bez symulowania akcji
        processGenerators.add(ProcessGenerator_4_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, bez symulowania akcji"
        processGenerators.add(ProcessGenerator_4_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces bez powrotów do stanów najlepszych, z symulowaniem akcji 2
        processGenerators.add(ProcessGenerator_4_4(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces z regularnym powrotem do stanów najlepszych co 25, z symulowaniem akcji 2
        processGenerators.add(ProcessGenerator_4_41(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //Proces z regularnym powrotem do stanów najlepszych co 25, z symulowaniem akcji 5
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_4_1() {

        Integer stepsNumberForSimulated_5 = 5000;
        Integer stepsNumberForNoSimulated = 6 * stepsNumberForSimulated_5;
        Integer stepsNumberForSimulated_2 = 2 * stepsNumberForSimulated_5;
        Integer maxFeaturesSubsetSize = 25;
        ClassificationTool classificationTool = ClassificationToolFactory.createSVMClassificationTool();
        StateValueEntityUpdater entityUpdater = new QUpdater();
        String dataFile = spambaseDataSet;

        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_4_1(stepsNumberForNoSimulated, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_4_2(stepsNumberForNoSimulated, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_4_3(stepsNumberForSimulated_2, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_4_4(stepsNumberForSimulated_2, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));
        processGenerators.add(ProcessGenerator_4_41(stepsNumberForSimulated_5, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile));

        return processGenerators;

    }


    /**
     * Ekpseryment 5 ma na celu pokazanie różnic w przypadku użycia różnych
     * aktualizatorów funkcji
     */
    
    private static class ProcessGenerator_5_1 extends ProcessGenerator {

        ProcessGenerator_5_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.2);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(100);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_5_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_5_1("Prosty proces_5_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_5_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_5_1(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    
    public static ArrayList<ProcessGenerator> ProcessGenerators_5_1() {

        Integer stepsNumber = 25000;
        Integer maxFeaturesSubsetSize = 150;
        ClassificationTool classificationTool = ClassificationToolFactory.createNaiveBayesnClassificationTool();
        StateValueEntityUpdater qUpdater = new QUpdater();
        StateValueEntityUpdater sarsaUpdater = new SarsaUpdater();
        StateValueEntityUpdater sarsaNStepUpdater = new SarsaNStepUpdater();
        String dataFile = arrhyyhmiaDataSet;

        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_5_1("Prosty proces_5_1_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, sarsaNStepUpdater, dataFile));
        processGenerators.add(ProcessGenerator_5_1("Prosty proces_5_1_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, sarsaUpdater, dataFile));
        processGenerators.add(ProcessGenerator_5_1("Prosty proces_5_1_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, qUpdater, dataFile));

        return processGenerators;

    }
    

    /**
     * Ekpseryment 6 ma na celu pokazanie różnic w przypadku użycia różnego współczynnika dyskontowania
     * aktualizatorów funkcji
     */
    private static class ProcessGenerator_6_1 extends ProcessGenerator {

        ProcessGenerator_6_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            entityUpdater.setDiscountingCoefficient(0.2);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    
    
    public static ProcessGenerator ProcessGenerator_6_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_6_1("Prosty proces_6_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }
    

    private static class ProcessGenerator_6_2 extends ProcessGenerator {

        ProcessGenerator_6_2(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            entityUpdater.setDiscountingCoefficient(0.5);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    
    
    public static ProcessGenerator ProcessGenerator_6_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_6_2("Prosty proces_6_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }
    
 
    private static class ProcessGenerator_6_3 extends ProcessGenerator {

        ProcessGenerator_6_3(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            entityUpdater.setDiscountingCoefficient(0.8);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    
    
    public static ProcessGenerator ProcessGenerator_6_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_6_3("Prosty proces_6_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    /**
     * Ekpseryment 6 ma na celu pokazanie różnic w przypadku użycia różnego współczynnika dyskontowania
     * aktualizatorów funkcji
     */
    
    public static ArrayList<ProcessGenerator> ProcessGenerators_6(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_6_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, z symulowaniem akcji 2 Dyskontowanie 0.2
        processGenerators.add(ProcessGenerator_6_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, z symulowaniem akcji 2 Dyskontowanie 0.5
        processGenerators.add(ProcessGenerator_6_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, z symulowaniem akcji 2 Dyskontowanie 0.8
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_6_1() {

        Integer stepsNumber = 12000;
        Integer maxFeaturesSubsetSize = 30;
        ClassificationTool classificationTool = ClassificationToolFactory.createNaiveBayesnClassificationTool();
        StateValueEntityUpdater entityUpdater = new SarsaNStepUpdater();
        String dataFile = optdigitsDataSet;

        return ProcessGenerators_6(stepsNumber,  maxFeaturesSubsetSize,  classificationTool,  entityUpdater,  dataFile);

    }



/**
     * Ekpseryment 7 ma na celu pokazanie różnic w liczbie akcji
     */

     private static class ProcessGenerator_7_1 extends ProcessGenerator {

        ProcessGenerator_7_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych");
            return process;
        }
    }

    
    
    public static ProcessGenerator ProcessGenerator_7_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_7_1("Prosty proces_7_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }
    
    private static class ProcessGenerator_7_2 extends ProcessGenerator {

        ProcessGenerator_7_2(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }
            
            EGreedyManyFeatureStrategy strategy = new EGreedyManyFeatureStrategy();
            strategy.setNumberOfFeaturesToSelect(3);
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych");
            return process;
        }
    }

    
    
    public static ProcessGenerator ProcessGenerator_7_2(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_7_2("Prosty proces_7_2", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }
    
    private static class ProcessGenerator_7_3 extends ProcessGenerator {

        ProcessGenerator_7_3(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedyManyFeatureStrategy strategy = new EGreedyManyFeatureStrategy();
            strategy.setNumberOfFeaturesToSelect(5);
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.1);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(25);

            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    
    
    public static ProcessGenerator ProcessGenerator_7_3(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_7_3("Prosty proces_7_3", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }
    
    /**
     * Ekpseryment 7 ma na celu pokazanie różnic w przypadku użycia różnej liczby modyfikacji w ramach pojedynczej akcji
     */
    
    public static ArrayList<ProcessGenerator> ProcessGenerators_7(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_7_1(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, ackja 1
        processGenerators.add(ProcessGenerator_7_2(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, akcja 3
        processGenerators.add(ProcessGenerator_7_3(stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile)); //"Proces z regularnym powrotem do stanów najlepszych co 25 krokrów, akcja 5
        return processGenerators;
    }

    public static ArrayList<ProcessGenerator> ProcessGenerators_7_1() {

        Integer stepsNumber = 50000;
        Integer maxFeaturesSubsetSize = null;
        ClassificationTool classificationTool = ClassificationToolFactory.createJ48ClassificationTool();
        StateValueEntityUpdater entityUpdater = new QUpdater();
        String dataFile = soybeanDataSet;

        return ProcessGenerators_7(stepsNumber,  maxFeaturesSubsetSize,  classificationTool,  entityUpdater,  dataFile);

    }
    
    
    


    /**
     * Ekpseryment 8 ma na celu pokazanie możliwości opracowanej metody na dużych zestawach danych
     */
    
    private static class ProcessGenerator_8_1 extends ProcessGenerator {

        ProcessGenerator_8_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
            super(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
        }

        @Override
        public FSProcess generateProcess() throws Exception {

            Instances data = InstanceFileHandler.getDatasetInstances(dataFile);

            Integer featuresInDataSetNumber = data.numAttributes() - 1;
            if (maxFeaturesSubsetSize == null) {
                maxFeaturesSubsetSize = (int) Math.round(1.0 * (double) featuresInDataSetNumber);
            }

            EGreedySingleFeatureStrategy strategy = new EGreedySingleFeatureStrategy();
            strategy.setMaxFeaturesInSubset(maxFeaturesSubsetSize);
            strategy.setGreedyActionProbability(0.2);

            PowerFunctionProbabilityGenerator powerFunctionProbabilityGenerator = new PowerFunctionProbabilityGenerator();
            strategy.setAdditionProbabilityGenerator(powerFunctionProbabilityGenerator);
            strategy.setOnGreedyActionProbabilityUpdate(stepsNumber);
            strategy.setOnAdditionProbabilityGeneratorSwitch();

            RewardGenerator rewardGenerator = new RewardGenerator();
            rewardGenerator.setFeatureNumberImportanceCoefficient(0.15);

            FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
            process.setClassificationTool(classificationTool);
            process.setStrategy(strategy);
            process.setRewardGenerator(rewardGenerator);
            process.setEntityUpdater(entityUpdater);
            process.setTimeStepsNumber(stepsNumber);

            PlannerSimulatedActions plannerSimulatedActions = new PlannerSimulatedActions(process);
            PlannerBackToBestRegular plannerBackToBestRegular = new PlannerBackToBestRegular(process);
            plannerBackToBestRegular.setTimeStepsBetweenBackToBest(50);

            process.addPlanner(plannerSimulatedActions);
            process.addPlanner(plannerBackToBestRegular);
            process.setDescription("Proces z regularnym powrotem do stanów najlepszych, z symulowaniem akcji");
            return process;
        }
    }

    public static ProcessGenerator ProcessGenerator_8_1(Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_8_1("Prosty proces_8_1", stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    public static ProcessGenerator ProcessGenerator_8_1(String name, Integer stepsNumber, Integer maxFeaturesSubsetSize, ClassificationTool classificationTool, StateValueEntityUpdater entityUpdater, String dataFile) {
        return new ProcessGenerator_8_1(name, stepsNumber, maxFeaturesSubsetSize, classificationTool, entityUpdater, dataFile);
    }

    
    public static ArrayList<ProcessGenerator> ProcessGenerators_8_1() {

        Integer stepsNumber = 250000;
        Integer stepsNumber2 = 60000;
        Integer maxFeaturesSubsetSize = null;
        ClassificationTool j48 = ClassificationToolFactory.createJ48ClassificationTool();
        ClassificationTool bayes = ClassificationToolFactory.createNaiveBayesnClassificationTool();
        StateValueEntityUpdater qUpdater = new QUpdater();
        String dataFile_1 = arrhyyhmiaDataSet;
        String dataFile_2 = mfeatpixelDataSet;
        String dataFile_3 = optdigitsDataSet;

        ArrayList<ProcessGenerator> processGenerators = new ArrayList<ProcessGenerator>();
        processGenerators.add(ProcessGenerator_8_1("Prosty proces_8_1_1", stepsNumber, maxFeaturesSubsetSize, j48, qUpdater, dataFile_1));
        processGenerators.add(ProcessGenerator_8_1("Prosty proces_8_1_2", stepsNumber2, maxFeaturesSubsetSize, bayes, qUpdater, dataFile_2));
        processGenerators.add(ProcessGenerator_8_1("Prosty proces_8_1_3", stepsNumber2, maxFeaturesSubsetSize, bayes, qUpdater, dataFile_3));

        return processGenerators;

    }
}
