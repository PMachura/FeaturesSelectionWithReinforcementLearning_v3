/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.helpers;

import machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticHolderKind;
import java.util.HashMap;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticHolder;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticGenerator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.actions.ActionsSpace;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationTool;
import machura.przemyslaw.featuresselectionwithrl.planers.BestStateActionsCollector;
import machura.przemyslaw.featuresselectionwithrl.planers.Planner;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerBackToBest;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerBackToBestNoImprovement;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerBackToBestRegular;
import machura.przemyslaw.featuresselectionwithrl.planers.PlannerSimulatedActions;
import machura.przemyslaw.featuresselectionwithrl.experiment.Experiment;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcessWithPlanners;
import machura.przemyslaw.featuresselectionwithrl.process.FSSimpleProcess;
import machura.przemyslaw.featuresselectionwithrl.process.helpers.ProcessState;
import machura.przemyslaw.featuresselectionwithrl.states.State;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import static machura.przemyslaw.featuresselectionwithrl.helpers.PrintHelper.*;
import machura.przemyslaw.featuresselectionwithrl.process.interruptors.ProcessInterruptor;
import machura.przemyslaw.featuresselectionwithrl.process.interruptors.ProcessInterruptorNoImprovement;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticGeneratorForGivenStatesNumber;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticGeneratorForMaxEvaluationDifference;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticHolderCollection;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.ProcessStatisticGenerator;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.ProcessStatisticHolder;
import machura.przemyslaw.featuresselectionwithrl.returns.RewardGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedyManyFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedySingleFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.Strategy;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.ActionProbabilityGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.ConstantProbabilityGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilitygenerators.PowerFunctionProbabilityGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters.ActionProbabilityUpdater;
import machura.przemyslaw.featuresselectionwithrl.strategy.probabilityupdaters.ConstantProductActionProbabilityUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.QUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.SarsaNStepUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.SarsaUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.StateValueEntityUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.stepsizegenerators.StepSizeGenerator;
import machura.przemyslaw.featuresselectionwithrl.updaters.stepsizegenerators.StepSizeGeneratorActionUpdateNumberDivider;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;

/**
 *
 * @author Przemek
 */
public class ProcessOutputPrinter {

    public static void test() {
        System.out.println(numberPrint(12451.22323));
    }

    public static String print(ProcessStatisticHolder statisticHolder) {
        StringBuilder result = generateStringBuilder();
        result.append("Wartość stanu dla pełnego zbioru atrybutów na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticHolder.getWholeFeaturesSetValueByLearnData()));
        result.append(lineSeparator());
        result.append("Dokładność klasyfikacji dla pełnego zbioru atrybutów na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticHolder.getWholeFeaturesSetClassificationEvaluationByLearnData()));
        result.append(lineSeparator());
        result.append("Wartość stanu dla pełnego zbioru atrybutów na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticHolder.getWholeFeaturesSetValue()));
        result.append(lineSeparator());
        result.append("Dokładność klasyfikacji dla pełnego zbioru atrybutów na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticHolder.getWholeFeaturesSetClassificationEvaluation()));
        result.append(lineSeparator());
        result.append("Różnica w wartości stanu między wyznaczoną na zbiorze uczącym a testowym" + colon() + tabulator() + numberPrint(statisticHolder.getWholeFeaturesSetLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Różnica w dokładności klasyfikacji między wyznaczoną na zbiorze uczącym a testowym" + colon() + tabulator() + numberPrint(statisticHolder.getWholeFeaturesSetLearnAndTestClassificationDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Liczba stanów znalezionych" + colon() + tabulator() + numberPrint(statisticHolder.getNumberOfFoundedStates()));
        result.append(lineSeparator());
        result.append("Liczba wykonanych kroków" + colon() + tabulator() + numberPrint(statisticHolder.getExecutedTimeSteps()));
        result.append(lineSeparator());
        result.append("Czas wykonania" + colon() + tabulator() + numberPrint(statisticHolder.getExecutionTime()));
        result.append(lineSeparator());
        result.append("Czas klasyfikacji dla selekcji atrybutów" + colon() + tabulator() + numberPrint(statisticHolder.getClassificationLearningTime()));
        result.append(lineSeparator());
        result.append("Czas klasyfikacji dla najlepszych podzbiorów na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticHolder.getClassificationTestingTime()));
        result.append(lineSeparator());
        result.append("Najdłuższa seria bez poprawy rozwiązania" + colon() + tabulator() + numberPrint(statisticHolder.getMaxNoImprovementSeries()));
        result.append(lineSeparator());
        result.append("Liczba akcji dodających atrybut" + colon() + tabulator() + numberPrint(statisticHolder.getAdditionAction()));
        result.append(lineSeparator());
        result.append("Liczba akcji odejmujących atrybut" + colon() + tabulator() + numberPrint(statisticHolder.getDeletionAction()));
        result.append(lineSeparator());
        result.append("Liczba symulowanych akcji dodających atrybut" + colon() + tabulator() + numberPrint(statisticHolder.getSimulatedAdditionAction()));
        result.append(lineSeparator());
        result.append("Liczba symulowanych akcji odejmujących atrybut" + colon() + tabulator() + numberPrint(statisticHolder.getSimulatedDeletionAction()));
        result.append(lineSeparator());
        result.append("Liczba akcji wykonanych zachłannie" + colon() + tabulator() + numberPrint(statisticHolder.getTotalGreedyAction()));
        result.append(lineSeparator());
        result.append("Liczba akcji wykonanych losowo" + colon() + tabulator() + numberPrint(statisticHolder.getTotalRandomAction()));
        result.append(lineSeparator());
        result.append("Liczba powrotów do stanów najlepszych" + colon() + tabulator() + numberPrint(statisticHolder.getNumberOfBackToBest()));
        result.append(lineSeparator());

        return result.toString();
    }

    public static String print(ProcessStatisticGenerator statisticGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append("Generator statystyk dla procesów");

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Wartości maksymalne statystyk");
        result.append(lineSeparator());
        result.append(print(statisticGenerator.getManyProcessStatistickHolderForMax()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Wartości minimalne statystyk");
        result.append(lineSeparator());
        result.append(print(statisticGenerator.getManyProcessStatistickHolderForMin()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Wartości średnie statystyk");
        result.append(lineSeparator());
        result.append(print(statisticGenerator.getManyProcessStatistickHolderForAverage()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Wartości wariancji statystyk");
        result.append(lineSeparator());
        result.append(print(statisticGenerator.getManyProcessStatistickHolderForVariance()));

        return result.toString();
    }

    public static String print(ActionsSpace actionSpace) {
        StringBuilder result = generateStringBuilder();
        result.append("Przestrzeń akcji" + colon());
        result.append(space());
        result.append(space());
        result.append("Liczba możliwych do dodania" + colon() + space() + numberPrint(actionSpace.getFeaturesAllowedToAdd().size()));
        result.append(space());
        result.append(space());
        result.append("Liczba możliwych do usunięcia" + colon() + space() + numberPrint(actionSpace.getFeaturesAllowedToRemove().size()));
        result.append(space());
        result.append(space());
        result.append("Aktualny podzbiór" + colon() + space() + "rozmiar" + colon() + space() + actionSpace.getFeaturesInSubsetAmount());
        result.append(space());
        result.append("cechy" + colon());
        result.append(space());

        Iterator<Feature> iterator = actionSpace.getFeaturesSubset().iterator();
        if (!iterator.hasNext()) {
            result.append(empty());
        } else {
            while (iterator.hasNext()) {
                result.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    result.append(separator());
                }
            }
        }
        return result.toString();
    }

    public static String print(ProcessState processState) {
        StringBuilder result = generateStringBuilder();
        result.append("Stan procesu" + colon() + space() + "rozmiar" + colon() + space() + processState.getState().size());
        result.append(space());
        result.append("cechy" + colon() + space());
        Iterator<Feature> iterator = processState.getState().iterator();
        if (!iterator.hasNext()) {
            result.append(empty());
        } else {
            while (iterator.hasNext()) {
                result.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    result.append(separator());
                }
            }
        }

        result.append(space());
        result.append(space());
        result.append("Bit state" + colon() + space() + processState.getBitState());

        return result.toString();
    }

    public static String print(SelectedAction selectedAction) {
        StringBuilder description = generateStringBuilder();
        description.append("Wybrana akcja" + colon());
        description.append(space());

        description.append("Cechy do dodania" + colon());
        description.append(space());
        Iterator<Feature> iterator = selectedAction.getFeaturesToAdd().iterator();
        if (!iterator.hasNext()) {
            description.append(empty());
        } else {
            while (iterator.hasNext()) {
                description.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    description.append(separator());
                }
            }
        }

        description.append(space());
        description.append(space());
        description.append("Cechy do usunięcia" + colon());
        description.append(space());
        iterator = selectedAction.getFeaturesToRemove().iterator();
        if (!iterator.hasNext()) {
            description.append(empty());
        } else {
            while (iterator.hasNext()) {
                description.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    description.append(separator());
                }
            }
        }

        return description.toString();
    }

    public static String print(Experiment experiment) {
        StringBuilder result = generateStringBuilder();
        result.append("Eksperyment" + colon() + tabulator() + experiment.getName());
        result.append(lineSeparator());
        result.append(experiment.getDescription());
        result.append(lineSeparator());
        result.append("Liczba wykonań każdego procesu" + colon() + tabulator() + experiment.getNumberOfEachProcessExecution());
        return result.toString();
    }

    public static String classifierName(ClassificationTool classificationTool) {
        if (classificationTool.getClassifier() instanceof J48) {
            return "Klasyfikator J48";
        } else if (classificationTool.getClassifier() instanceof NaiveBayes) {
            return "Klasyfikator Naiwny Bayes";
        } else if (classificationTool.getClassifier() instanceof SMO) {
            return "Klasyfikator Maszyna Wektorów Wspierających";
        }
        return "Klasyfikator nieznany";
    }

    public static String print(ClassificationTool classificationTool) {
        StringBuilder result = generateStringBuilder();
        result.append("Klasyfikator" + colon() + tabulator() + classifierName(classificationTool));
        result.append(lineSeparator());
        result.append("Krotność w przypadku walidacji krzyżowej" + colon() + tabulator() + numberPrint(classificationTool.getFolds()));
        return result.toString();
    }

    public static String interruptorName(ProcessInterruptor interruptor) {
        if (interruptor instanceof ProcessInterruptorNoImprovement) {
            return "Przerywacz procesu po braku poprawy najlepszej jak dotąd wartości podzbioru";
        }
        return "Przerywacz nieznany";
    }

    public static String interruptorName(ProcessInterruptorNoImprovement interruptor) {
        StringBuilder result = generateStringBuilder();
        result.append("Maksymalna liczba tworzonych podzbiorów niepoprawiających najlepszej jak dotąd wartości podzbioru" + colon() + tabulator() + numberPrint(interruptor.getMaxNoImprovementSeries()));
        return result.toString();
    }

    public static String print(ProcessInterruptor interruptor) {
        StringBuilder result = generateStringBuilder();
        result.append("Przerywacz" + colon() + tabulator() + interruptorName(interruptor));
        result.append("Czy przerwał proces" + colon() + tabulator() + booleanPrint(interruptor.getInterruptionDone()));
        if (interruptor instanceof ProcessInterruptorNoImprovement) {
            result.append(lineSeparator());
            result.append(print((ProcessInterruptorNoImprovement) interruptor));
        }
        return result.toString();
    }

    public static String processName(FSProcess process) {
        if (process instanceof FSSimpleProcess) {
            return "Prosty proces";
        } else if (process instanceof FSProcessWithPlanners) {
            return "Proces z algorytmami planującymi";
        }
        return "Proces nieznany";
    }

    public static String print(FSProcessWithPlanners process) {
        StringBuilder result = generateStringBuilder();
        Iterator<Planner> iterator = process.getPlanners().iterator();
        if (!iterator.hasNext()) {
            result.append(empty());
        } else {
            while (iterator.hasNext()) {
                result.append(print(iterator.next()));
                if (iterator.hasNext()) {
                    result.append(lineSeparator());
                    result.append(lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String print(FSProcess process) {
        StringBuilder result = generateStringBuilder();
        result.append("Proces" + colon() + tabulator() + processName(process));
        result.append(lineSeparator());
        result.append("Opis" + colon() + tabulator() + process.getDescription());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Zbiór danych" + colon() + tabulator() + process.getDataFile());
        result.append(lineSeparator());
        result.append("Liczba atrybutów w zbiorze wejściowym" + colon() + tabulator() + numberPrint(process.getData().numAttributes() - 1));
        result.append(lineSeparator());
        result.append("Liczba obiektów w zbiorze wejściowym" + colon() + tabulator() + numberPrint(process.getData().numInstances()));
        result.append(lineSeparator());
        result.append("Liczba klas w zbiorze danych wejściowych" + colon() + tabulator() + numberPrint(process.getData().classAttribute().numValues()));
        result.append(lineSeparator());
        result.append("Procentowy udział danych treningowych" + colon() + tabulator() + numberPrint(process.getTrainingDataPercentage()));
        result.append(lineSeparator());
        result.append("Liczba obiektów treningowych" + colon() + tabulator() + numberPrint(process.getTrainingData().numInstances()));
        result.append(lineSeparator());
        result.append("Procentowy udział danych testwowych" + colon() + tabulator() + numberPrint(1.0 - process.getTrainingDataPercentage()));
        result.append(lineSeparator());
        result.append("Liczba obiektów testowych" + colon() + tabulator() + numberPrint(process.getTestingData().numInstances()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Wartość stanu dla pełnego zbioru atrybutów na zbiorze uczącym" + colon() + tabulator() + numberPrint(process.getWholeFeaturesSetValueByLearnData()));
        result.append(lineSeparator());
        result.append("Dokładność klasyfikacji dla pełnego zbioru atrybutów na zbiorze uczącym" + colon() + tabulator() + numberPrint(process.getWholeFeaturesSetClassificationEvaluationByLearnData()));     
        result.append(lineSeparator());
        result.append("Wartość stanu dla pełnego zbioru atrybutów na zbiorze testowym" + colon() + tabulator() + numberPrint(process.getWholeFeaturesSetValue()));
        result.append(lineSeparator());
        result.append("Dokładność klasyfikacji dla pełnego zbioru atrybutów na zbiorze testowym" + colon() + tabulator() + numberPrint(process.getWholeFeaturesSetClassificationEvaluation()));
          
        if (process.getWholeFeaturesSetValueByLearnData() != null && process.getWholeFeaturesSetValue() != null) {
            result.append(lineSeparator());  
            result.append("Różnica w wartości stanu między wyznaczoną na zbiorze uczącym a testowym" + colon() + tabulator() + numberPrint(process.getWholeFeaturesSetValueByLearnData() - process.getWholeFeaturesSetValue()));
        }else{
            result.append(lineSeparator());  
            result.append("Różnica w wartości stanu między wyznaczoną na zbiorze uczącym a testowym" + colon() + empty());
        }
       
        if (process.getWholeFeaturesSetClassificationEvaluationByLearnData() != null && process.getWholeFeaturesSetClassificationEvaluation() != null) {
             result.append(lineSeparator());
            result.append("Różnica w dokładności klasyfikacji między wyznaczoną na zbiorze uczącym a testowym" + colon() + tabulator() + numberPrint(process.getWholeFeaturesSetClassificationEvaluationByLearnData() - process.getWholeFeaturesSetClassificationEvaluation()));
        }else{
             result.append(lineSeparator());
            result.append("Różnica w dokładności klasyfikacji między wyznaczoną na zbiorze uczącym a testowym" + colon() + empty());
        }
        
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Liczba stanów znalezionych" + colon() + tabulator() + numberPrint(process.getStateActionEntityCollection().getEntities().size()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna liczba kroków" + colon() + tabulator() + numberPrint(process.getTimeStepsNumber()));
        result.append(lineSeparator());
        result.append("Wykonana liczba kroków" + colon() + tabulator() + numberPrint(process.getTimeStep()));
        result.append(lineSeparator());
        result.append("Czas wykonania[s]" + colon() + tabulator() + numberPrint(process.getProcessTimeWatch().totalTimeInSeconds()));
        result.append(lineSeparator());
        result.append("Czas klasyfikacji podczas uczenia[s]" + colon() + tabulator() + numberPrint(process.getClassificationTimeWatch().totalTimeInSeconds()));
        result.append(lineSeparator());
        result.append("Czas klasyfikacji zbioru testowego[s]" + colon() + tabulator() + numberPrint(process.getClassificationByTestDataTimeWatch().totalTimeInSeconds()));
        result.append(lineSeparator());
        result.append("Czas klasyfikacji łączny[s]" + colon() + tabulator() + numberPrint(process.getClassificationByTestDataTimeWatch().totalTimeInSeconds() + process.getClassificationTimeWatch().totalTimeInSeconds()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(print(process.getBestStateActionsCollector()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(print(process.getRewardGenerator()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(print(process.getEntityUpdater()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(print(process.getClassificationTool()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(print(process.getStrategy()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Warunki przerwania procesu");
        Iterator<ProcessInterruptor> iterator = process.getProcessInterruptors().iterator();
        if (!iterator.hasNext()) {
            result.append(lineSeparator());
            result.append(empty());
        } else {
            while (iterator.hasNext()) {
                result.append(print(iterator.next()));
                if (iterator.hasNext()) {
                    result.append(separator());
                }
            }
        }

        if (process instanceof FSProcessWithPlanners) {
            result.append(lineSeparator());
            result.append(lineSeparator());
            result.append("Algorytmy planujące");
            result.append(lineSeparator());
            result.append(print((FSProcessWithPlanners) process));
        }

        return result.toString();
    }

    public static String stepSizeGeneratorName(StepSizeGenerator stepSizeGenerator) {
        if (stepSizeGenerator instanceof StepSizeGeneratorActionUpdateNumberDivider) {
            return "Generator oparty o podzielenie liczby 1 przez dzielnik będący numerem aktualizcaji podniesionym do potęgi o ustalonej wartości";
        }
        return "Generator rozmiaru kroku nieznany";
    }

    public static String print(StepSizeGenerator stepSizeGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append("Generator rozmiaru kroku" + colon() + tabulator() + stepSizeGeneratorName(stepSizeGenerator));
        result.append(lineSeparator());
        result.append("Wartość współczynnika" + colon() + tabulator() + numberPrint(stepSizeGenerator.getCoefiicent()));

        return result.toString();
    }

    public static String stateValueEntityUpdaterName(StateValueEntityUpdater entityUpdater) {
        if (entityUpdater instanceof QUpdater) {
            return "Q-learning";
        } else if (entityUpdater instanceof SarsaUpdater) {
            return "Sarsa";
        } else if (entityUpdater instanceof SarsaNStepUpdater) {
            return "N-step Sarsa";
        }

        return "aktualizator nieznany";
    }

    public static String print(SarsaNStepUpdater sarsaNStepUpdater) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba kroków" + colon() + tabulator() + numberPrint(sarsaNStepUpdater.getnStep()));

        return result.toString();
    }

    public static String print(StateValueEntityUpdater entityUpdater) {
        StringBuilder result = generateStringBuilder();
        result.append("Aktualizator stanów i akcji" + colon() + tabulator() + stateValueEntityUpdaterName(entityUpdater));
        result.append(lineSeparator());
        result.append("Współczynnik dyskontowania" + colon() + tabulator() + numberPrint(entityUpdater.getDiscountingCoefficient()));
        result.append(lineSeparator());
        result.append(print(entityUpdater.getStepSizeGenerator()));
        if (entityUpdater instanceof SarsaNStepUpdater) {
            result.append(lineSeparator());
            result.append(print((SarsaNStepUpdater) entityUpdater));
        }
        return result.toString();
    }

    public static String plannerName(Planner planner) {
        if (planner instanceof PlannerBackToBestRegular) {
            return "Regularne powroty do stanów najlepszych";
        } else if (planner instanceof PlannerBackToBestNoImprovement) {
            return "Powrót do stanu najlepszego po braku poprawy wyniku";
        } else if (planner instanceof PlannerSimulatedActions) {
            return "Algorytm planujący symulowanie akcji";
        } else {
            return "ALgorytm planujący symulator akcji";
        }
    }

    public static String print(BestStateActionsCollector actionCollector) {
        StringBuilder result = generateStringBuilder();
        result.append("Kolektor stanów najlepszych");
        result.append(lineSeparator());
        result.append("Liczba przechowywanych stanów" + colon() + tabulator() + numberPrint(actionCollector.getMaxStoredEntities()));
        result.append(lineSeparator());
        result.append("Najdłuższa seria bez poprawy" + colon() + tabulator() + numberPrint(actionCollector.getMaxNoImprovementSeries()));
        return result.toString();
    }

    public static String print(PlannerBackToBestRegular planner) {
        StringBuilder result = generateStringBuilder();
        result.append("Planowana liczba powrotów w procesie" + colon() + tabulator() + numberPrint(planner.getNumberOfBackToBestInProcess()));
        result.append(lineSeparator());
        result.append("Liczba kroków algorytmu przed powrotem" + colon() + tabulator() + numberPrint(planner.getTimeStepsBetweenBacktToBest()));
        return result.toString();

    }

    public static String print(PlannerBackToBestNoImprovement planner) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba kroków czasu bez poprawy, po których następuje powrót" + colon() + tabulator() + numberPrint(planner.getNoImprovementStepsBeforeBack()));
        return result.toString();

    }

    public static String print(PlannerBackToBest planner) {

        StringBuilder result = generateStringBuilder();
        result.append("Liczba wykonanych powrotów" + colon() + tabulator() + numberPrint(planner.getPerformedBacksToBest().size()));
        result.append(lineSeparator());
        result.append("Powroty wykonane w krokach" + colon() + tabulator());
        Iterator<Integer> iterator = planner.getPerformedBacksToBest().iterator();
        if (!iterator.hasNext()) {
            result.append(empty());
        } else {
            while (iterator.hasNext()) {
                result.append(numberPrint(iterator.next()));
                if (iterator.hasNext()) {
                    result.append(separator());
                }
            }
        }

        if (planner instanceof PlannerBackToBestRegular) {
            result.append(lineSeparator());
            result.append(print((PlannerBackToBestRegular) planner));
        } else if (planner instanceof PlannerBackToBestNoImprovement) {
            result.append(lineSeparator());
            result.append(print((PlannerBackToBestNoImprovement) planner));
        }

        return result.toString();
    }

    public static String print(PlannerSimulatedActions planner) {
        StringBuilder result = generateStringBuilder();
        result.append("Planowana liczba symulowanych akcji w jendym kroku" + colon() + tabulator() + numberPrint(planner.getSimulatedActionNumber()));

        return result.toString();
    }

    public static String print(Planner planner) {
        StringBuilder result = generateStringBuilder();
        result.append("Algorytm planujący" + colon() + tabulator() + plannerName(planner));
        if (planner instanceof PlannerBackToBest) {
            result.append(lineSeparator());
            result.append(print((PlannerBackToBest) planner));
        } else if (planner instanceof PlannerSimulatedActions) {
            result.append(lineSeparator());
            result.append(print((PlannerSimulatedActions) planner));
        }

        return result.toString();
    }

    public static String print(RewardGenerator rewardGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append("Generator nagród");
        result.append(lineSeparator());
        result.append("Współczynnik istotności rozmiaru zbioru" + colon() + tabulator() + numberPrint(rewardGenerator.getFeatureNumberImportanceCoefficient()));
        result.append(lineSeparator());
        result.append("Maksymalna generowana wartość" + colon() + tabulator() + numberPrint(rewardGenerator.getMaxGeneratedValue()));
        result.append(lineSeparator());
        result.append("Minimalna generowana wartość" + colon() + tabulator() + numberPrint(rewardGenerator.getMinGeneratedValue()));
        return result.toString();
    }

    public static String actionProbabilityUpdaterName(ActionProbabilityUpdater probabilityUpdater) {
        if (probabilityUpdater instanceof ConstantProductActionProbabilityUpdater) {
            return "Aktualizator oparty o stały iloczyn";
        }
        return "Aktualizator nieznany";
    }

    public static String print(ActionProbabilityUpdater probabilityUpdater) {
        StringBuilder result = generateStringBuilder();
        result.append("Aktualizator prawdopodobieństwa wyboru akcji zachłannie" + colon() + tabulator() + actionProbabilityUpdaterName(probabilityUpdater));
        result.append(lineSeparator());
        result.append("Wartość współczynnika" + colon() + tabulator() + numberPrint(probabilityUpdater.getCoefficient()));
        result.append(lineSeparator());
        result.append("Maksymalna generowana wartość" + colon() + tabulator() + numberPrint(probabilityUpdater.getMaxGeneratedValue()));
        result.append(lineSeparator());
        result.append("Minimalna generowana wartość" + colon() + tabulator() + numberPrint(probabilityUpdater.getMinGeneratedValue()));
        return result.toString();
    }

    public static String actionProbabilityGeneratorName(ActionProbabilityGenerator probabilityGenerator) {
        if (probabilityGenerator instanceof ConstantProbabilityGenerator) {
            return "Generator oparty o wartość stałą";
        } else if (probabilityGenerator instanceof PowerFunctionProbabilityGenerator) {
            return "Generator oparty o funkcję potęgową";
        }
        return "Generator nieznany";
    }

    public static String print(ActionProbabilityGenerator probabilityGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append("Generator prawdopodobieństwa" + colon() + tabulator() + actionProbabilityGeneratorName(probabilityGenerator));
        result.append(lineSeparator());
        result.append("Wartość współczynnika" + colon() + tabulator() + numberPrint(probabilityGenerator.getCoefficient()));
        return result.toString();

    }

    public static String strategyName(Strategy strategy) {
        if (strategy instanceof EGreedySingleFeatureStrategy) {
            return "Strategia E-zachłanna z wyborem pojedynczego atrybutu w ramach akcji";
        } else if (strategy instanceof EGreedyManyFeatureStrategy) {
            return "Strategia E-zachłanna z wyborem kilku atrybutów w ramach akcji";
        }
        return "Strategia nieznana";
    }

    public static String print(Strategy strategy) {
        StringBuilder result = generateStringBuilder();
        result.append("STRATEGIA" + colon() + tabulator() + strategyName(strategy));
        result.append(lineSeparator());
        result.append("Maksymalny rozmiar podzbioru" + colon() + tabulator() + numberPrint(strategy.getMaxFeaturesInSubset()));
        result.append(lineSeparator());
        result.append("Liczba atrybutów dodawanych lub usuwanych w ramach jednej akcji" + colon() + tabulator() + numberPrint(strategy.getNumberOfFeaturesToSelect()));
        result.append(lineSeparator());
        result.append("Liczba dodawań atrybutów do podzbioru" + colon() + tabulator() + numberPrint(strategy.getTotalAdditionAction()));
        result.append(lineSeparator());
        result.append("Liczba usunięć atrybutów z podzbioru" + colon() + tabulator() + numberPrint(strategy.getTotalDeletionAction()));
        result.append(lineSeparator());
        result.append("Liczba dodawań i usunięć" + colon() + tabulator() + numberPrint(strategy.getTotalAdditionAndDeletion()));
        result.append(lineSeparator());
        result.append("Liczba symulowanych dodawań atrybutów" + colon() + tabulator() + numberPrint(strategy.getSimulatedAddition()));
        result.append(lineSeparator());
        result.append("Liczba symulowanych usunięć atrybutów" + colon() + tabulator() + numberPrint(strategy.getSimulatedDeletion()));
        result.append(lineSeparator());
        if (strategy instanceof EGreedySingleFeatureStrategy) {
            result.append(print((EGreedySingleFeatureStrategy) strategy));
        } else if (strategy instanceof EGreedyManyFeatureStrategy) {
            result.append(print((EGreedyManyFeatureStrategy) strategy));
        }
        return result.toString();

    }

    public static String print(EGreedySingleFeatureStrategy strategy) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba akcji wykonanych zachłannie" + colon() + tabulator() + numberPrint(strategy.getTotalGreedyAction()));
        result.append(lineSeparator());
        result.append("Liczba akcji wykonanych losowo" + colon() + tabulator() + numberPrint(strategy.getTotalRandomAction()));
        result.append(lineSeparator());
        result.append("Zmiana trendu prawdopodobieństwa dodania atrybutu" + colon() + tabulator() + booleanPrint(strategy.getAdditionProbabilityGeneratorSwitch()));
        result.append(lineSeparator());
        if (strategy.getAdditionProbabilityGeneratorSwitch()) {
            result.append("Liczba akcji przed zmianą trendu prawdopodobieństwa dodania atrybutu" + colon() + tabulator() + numberPrint(strategy.getStepsBeforeAdditionProbabilitySwitch()));
            result.append(lineSeparator());
        }
        result.append(print(strategy.getAdditionProbabilityGenerator()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Aktualizowanie wartości prawdopodobieństwa wyboru akcji zachłannie" + colon() + tabulator() + booleanPrint(strategy.getGreedyActionProbabilityUpdate()));
        result.append(lineSeparator());
        if (strategy.getGreedyActionProbabilityUpdate()) {
            result.append(print(strategy.getGreedyActionProbabilityUpdater()));
            result.append(lineSeparator());
            result.append(lineSeparator());
        }

        result.append("Prawdopodobieństwo wyboru akcji zachłannie" + colon() + tabulator() + numberPrint(strategy.getGreedyActionProbability()));
        result.append(lineSeparator());
        result.append("Prawdopodobieństwo wyboru akcji losowo" + colon() + tabulator() + numberPrint(strategy.getRandomActionProbability()));
        return result.toString();
    }

    public static String print(EGreedyManyFeatureStrategy strategy) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba akcji wykonanych zachłannie" + colon() + tabulator() + numberPrint(strategy.getTotalGreedyAction()));
        result.append(lineSeparator());
        result.append("Liczba akcji wykonanych losowo" + colon() + tabulator() + numberPrint(strategy.getTotalRandomAction()));
        result.append(lineSeparator());
        result.append("Zmiana trendu prawdopodobieństwa dodania atrybutu" + colon() + tabulator() + booleanPrint(strategy.getAdditionProbabilityGeneratorSwitch()));
        result.append(lineSeparator());
        if (strategy.getAdditionProbabilityGeneratorSwitch()) {
            result.append("Liczba akcji przed zmianą trendu prawdopodobieństwa dodania atrybutu" + colon() + tabulator() + numberPrint(strategy.getStepsBeforeAdditionProbabilitySwitch()));
            result.append(lineSeparator());
        }
        result.append(print(strategy.getAdditionProbabilityGenerator()));
        result.append(lineSeparator());
        result.append("Aktualizowanie wartości prawdopodobieństwa wyboru akcji zachłannie" + colon() + tabulator() + booleanPrint(strategy.getGreedyActionProbabilityUpdate()));
        result.append(lineSeparator());
        if (strategy.getGreedyActionProbabilityUpdate()) {
            result.append(print(strategy.getGreedyActionProbabilityUpdater()));
        }
        result.append("Prawdopodobieństwo wyboru akcji zachłannie" + colon() + tabulator() + numberPrint(strategy.getGreedyActionProbability()));
        result.append(lineSeparator());
        result.append("Prawdopodobieństwo wyboru akcji losowo" + colon() + tabulator() + numberPrint(strategy.getRandomActionProbability()));
        return result.toString();
    }

    public static String print(EntitiesStatisticHolderCollection statisticsCollection) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba zestawów statystyk" + colon() + tabulator() + numberPrint(statisticsCollection.getStatisticList().size()));
        result.append(lineSeparator());
        result.append("Liczba wszystkich stanów znalezionych" + colon() + tabulator() + numberPrint(statisticsCollection.getTotalStateActionEntities()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("**Wartości unikalne uzyskane z zestawu statystyk");
        result.append(lineSeparator());
        result.append("Unikalna maksymalna wartość klasyfikacji" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxClassification()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna wartość klasyfikacji" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinClassification()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna wartość stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxStateValue()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna wartość stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinStateValue()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna wartość klasyfikacji na bazie zbioru testowego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna wartość klasyfikacji na bazie zbioru testowego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinClassificationByTestData()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna wartość stanu na bazie zbioru testowego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna wartość stanu na bazie zbioru testowego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinStateValueByTestData()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna wartość stanu na bazie zbioru uczącego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna wartość stanu na bazie zbioru uczącego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinValueByLearningSet()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna dokładność klasyfikacji na bazie zbioru uczącego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna dokładność klasyfikacji na bazie zbioru uczącego" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinClassificationEvaluationByLearningSet()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica klasyfikacji między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica klasyfikacji między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica klasyfikacji między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica klasyfikacji między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinLearnAndTestClassificationDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica wartości stanu między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica wartości stanu między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica wartości stanu między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica wartości stanu między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinLearnAndTestValueDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinWholeFeaturesSetClassificationByLearningSetDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica wartości stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica wartości stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Unikalna maksymalna różnica wartości stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna różnica wartości stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinWholeFeaturesSetValueByLearningSetDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna liczba wizyt" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxStateVisit()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna liczba wizyt" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinStateVisit()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalna maksymalna liczba okdryć" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxStateDiscovery()));
        result.append(lineSeparator());
        result.append("Unikalna minimalna liczba okdryć" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMinStateDiscovery()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalny czas pierwszej wizity" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueFirstStateVisit()));
        result.append(lineSeparator());
        result.append("Unikalny czas ostatniej wizity" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueLastStateVisit()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Unikalny czas pierwszego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append("Unikalny czas ostatniego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueLastStateDiscovery()));

        result.append(lineSeparator());
        result.append("Unikalny maksymalny rozmiar zbioru" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueMaxSubsetSize()));
        result.append(lineSeparator());

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Zróżnicowanie w wygenerowanych najlepszych podzbiorach między wszystkimi zestawami danych" + colon() + tabulator() + numberPrint(statisticsCollection.getUniqueBestGeneratedSubsetsDifference()));
        result.append(lineSeparator());

        if (statisticsCollection.getStatisticList() != null && statisticsCollection.getUniqueBestGeneratedSubsetsDifference() != null) {
            result.append("Średnie zróżnicowanie w wygenerowanych najlepszych podzbiorach między wszystkimi zestawami danych" + colon() + tabulator() + numberPrint((double) statisticsCollection.getUniqueBestGeneratedSubsetsDifference() / (double) statisticsCollection.getStatisticList().size()));
            result.append(lineSeparator());
        }
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej wartości w oparciu o zbiór treningowy");
        result.append(lineSeparator());
        result.append(print(statisticsCollection.getUniqueBestValuedState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej dokładności klasyfikacji w oparciu o zbiór treningowy");
        result.append(lineSeparator());
        result.append(print(statisticsCollection.getUniqueBestClassifiedState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej wartości w oparciu o zbiór testowy");
        result.append(lineSeparator());
        result.append(print(statisticsCollection.getUniqueBestValuedByTestSetState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej dokładności klasyfikacji w oparciu o zbiór testowy");
        result.append(lineSeparator());
        result.append(print(statisticsCollection.getUniqueBestClassifiedByTestSetState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan najmniej przetrenowany w oparciu o wartość");
        result.append(lineSeparator());
        result.append(print(statisticsCollection.getUniqueBestNotOverfittedStateByValue()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan najmniej przetrenowany w oparciu o dokładność klasyfikacji");
        result.append(lineSeparator());
        result.append(print(statisticsCollection.getUniqueBestNotOverfittedStateByClassification()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("**Wartości średnie uzyskane z zestawu statystyk");
        result.append(lineSeparator());
        result.append("Liczba najlepszych zbiorów" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageFoundedStates()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość klasyfikacji" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxClassification()));
        result.append(lineSeparator());
        result.append("Minimalna wartość klasyfikacji" + colon() + tabulator() + numberPrint(statisticsCollection.getMinClassification()));
        result.append(lineSeparator());
        result.append("Średnia wartość klasyfikacji" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageClassification()));
        result.append(lineSeparator());
        result.append("Wariancja klasyfikacji" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceClassification()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość klasyfikacji na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Minimalna wartość klasyfikacji na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Średnia wartość klasyfikacji na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Wariancja klasyfikacji na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceClassificationByTestData()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Minimalna wartość klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Średnia wartość klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Wariancja klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceClassificationEvaluationByLearningSet()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica klasyfikacji między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica klasyfikacji między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnica klasyfikacji między obliczonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy klasyfikacji między obliczonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceTestAndTrainClassificationDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica klasyfikacji między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica klasyfikacji między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnica klasyfikacji między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy klasyfikacji między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceLearnAndTestClassificationDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxStateValue()));
        result.append(lineSeparator());
        result.append("Minimalna wartość stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getMinStateValue()));
        result.append(lineSeparator());
        result.append("Średnia wartość stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageStateValue()));
        result.append(lineSeparator());
        result.append("Wariancja wartości stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceStateValue()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość stanu na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Minimalna wartość stanu na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Średnia wartość stanu na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Wariancja wartości stanu na zbiorze testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceStateValueByTestData()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Minimalna wartość stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Średnia wartość stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Wariancja stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceValueByLearningSet()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu między obliczoną na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnica wartości stanu między obliczonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu między obliczonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceTestAndTrainStateValueDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getMinLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnica wartości stanu między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceLearnAndTestValueDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMinWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy dokładności klasyfikacji stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy dokładności klasyfikacji stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceWholeFeaturesSetClassificationDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMinWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceWholeFeaturesSetClassificationByLearningSetDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMinWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy wartości stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu między obliczoną na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceWholeFeaturesSetValueDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getMinWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy wartości stanu stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceWholeFeaturesSetValueByLearningSetDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Średnie zróżnicowanie zbiorów" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageSubsetDifferentation()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Czas pierwszej wizyty" + colon() + tabulator() + numberPrint(statisticsCollection.getFirstStateVisit()));
        result.append(lineSeparator());
        result.append("Średnia pierwszej wizyty" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageFirstStateVisit()));
        result.append(lineSeparator());
        result.append("Wariancja pierwszej wizyty" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceFirstStateVisit()));
        result.append(lineSeparator());
        result.append("Czas ostatniej wizyty" + colon() + tabulator() + numberPrint(statisticsCollection.getLastStateVisit()));
        result.append(lineSeparator());
        result.append("Średnia ostatniej wizyty" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageLastStateVisit()));
        result.append(lineSeparator());
        result.append("Wariancja ostatniej wizyty" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceLastStateVisit()));
        result.append(lineSeparator());
        result.append("Maksymalna liczba odkryć stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxStateVisit()));
        result.append(lineSeparator());
        result.append("Minimalna liczba odkryć stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getMinStateVisit()));
        result.append(lineSeparator());
        result.append("Średnie liczba wizyt stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageStateVisit()));
        result.append(lineSeparator());
        result.append("Wariancja liczby wizyt stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceStateVisit()));
        result.append(lineSeparator());

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Czas pierwszego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append("Średnia pierwszego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append("Wariancja pierwszego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append("Czas ostatniego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getLastStateDiscovery()));
        result.append(lineSeparator());
        result.append("Średnia ostatniego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageLastStateDiscovery()));
        result.append(lineSeparator());
        result.append("Wariancja ostatniego odkrycia" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceLastStateDiscovery()));
        result.append(lineSeparator());
        result.append("Maksymalna liczba odkryć stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxStateDiscovery()));
        result.append(lineSeparator());
        result.append("Minimalna liczba odkryć stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getMinStateDiscovery()));
        result.append(lineSeparator());
        result.append("Średnie liczba odkryć stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageStateDiscovery()));
        result.append(lineSeparator());
        result.append("Wariancja liczba odkryć stanu" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceStateDiscovery()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalny rozmiar zbioru" + colon() + tabulator() + numberPrint(statisticsCollection.getMaxSubsetSize()));
        result.append(lineSeparator());
        result.append("Minimalny rozmiar zbioru" + colon() + tabulator() + numberPrint(statisticsCollection.getMinSubsetSize()));
        result.append(lineSeparator());
        result.append("Średni rozmiar zbioru" + colon() + tabulator() + numberPrint(statisticsCollection.getAverageSubsetSize()));
        result.append(lineSeparator());
        result.append("Wariancja rozmiaru zbioru" + colon() + tabulator() + numberPrint(statisticsCollection.getVarianceSubsetSize()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Liczba wystąpień atrybutu we wszystkich podzbiorach");
        result.append(lineSeparator());
        Iterator<Entry<Feature, Integer>> iter = statisticsCollection.getTotalFeatureOccurenceIndexOrdered().entrySet().iterator();
        if (!iter.hasNext()) {
            result.append(empty());
        } else {
            while (iter.hasNext()) {
                Entry<Feature, Integer> entry = iter.next();
                result.append("Atrybut" + colon() + tabulator() + numberPrint(entry.getKey().getIndex()) + tabulator() + "Liczba wystąpień" + colon() + tabulator() + numberPrint(entry.getValue()) + tabulator() + "Średnio" + colon() + tabulator() + numberPrint((double) entry.getValue() / (double) statisticsCollection.getTotalStateActionEntities()));
                if (iter.hasNext()) {
                    result.append(lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public static String print(EntitiesStatisticHolder entityStatisticHolder) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba stanów znalezionych" + colon() + tabulator() + numberPrint(entityStatisticHolder.getEntities().size()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość klasyfikacji" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxClassification()));
        result.append(lineSeparator());
        result.append("Minimalna wartość klasyfikacji" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinClassification()));
        result.append(lineSeparator());
        result.append("Średnia wartość klasyfikacji" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageClassification()));
        result.append(lineSeparator());
        result.append("Wariancja klasyfikacji" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceClassification()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość klasyfikacji na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Minimalna wartość klasyfikacji na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Średnia klasyfikacji na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageClassificationByTestData()));
        result.append(lineSeparator());
        result.append("Wariancja klasyfikacji na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceClassificationByTestData()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Minimalna wartość klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Średnia wartość klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageClassificationEvaluationByLearningSet()));
        result.append(lineSeparator());
        result.append("Wariancja klasyfikacji na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceClassificationEvaluationByLearningSet()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica klasyfikacji stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica klasyfikacji stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy klasyfikacji stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy klasyfikacji stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceTestAndTrainClassificationDifference()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica klasyfikacji między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica klasyfikacji między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnica klasyfikacji między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy klasyfikacji między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceLearnAndTestClassificationDifference()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica klasyfikacji stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica klasyfikacji stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy klasyfikacji stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy klasyfikacji stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceWholeFeaturesSetClassificationDifference()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageWholeFeaturesSetClassificationByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy dokładności klasyfikacji stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceWholeFeaturesSetClassificationByLearningSetDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxStateValue()));
        result.append(lineSeparator());
        result.append("Minimalna wartość stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinStateValue()));
        result.append(lineSeparator());
        result.append("Średnia wartość stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageStateValue()));
        result.append(lineSeparator());
        result.append("Wariancja wartości stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceStateValue()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość stanu na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Minimalna wartość stanu na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Średnia wartość stanu na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageStateValueByTestData()));
        result.append(lineSeparator());
        result.append("Wariancja wartości stanu na bazie zbioru testowego" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceStateValueByTestData()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna wartość stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Minimalna wartość stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Średnia wartość stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageValueByLearningSet()));
        result.append(lineSeparator());
        result.append("Wariancja stanu na zbiorze uczącym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceValueByLearningSet()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy wartości stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageTestAndTrainStateValueDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu między ustalonymi na zbiorze treningowym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceTestAndTrainStateValueDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu między obliczoną na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnica wartości stanu między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageLearnAndTestValueDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu między obliczonymi na zbiorze uczącym i testowym" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceLearnAndTestValueDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy wartości stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu między ustalonymi na zbiorze testowym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceWholeFeaturesSetValueDifference()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Minimalna różnica wartości stanu stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Średnia różnicy wartości stanu stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageWholeFeaturesSetValueByLearningSetDifference()));
        result.append(lineSeparator());
        result.append("Wariancja różnicy wartości stanu stanu między obliczoną na zbiorze uczącym, a wartością dla pełnego zestawu atrybutów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceWholeFeaturesSetValueByLearningSetDifference()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Średnie zróżnicowanie zbiorów" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageSubsetDifferentation()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna liczba wizyt stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxStateVisit()));
        result.append(lineSeparator());
        result.append("Minimalna liczba wizyt stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinStateVisit()));
        result.append(lineSeparator());
        result.append("Średnie liczba wizyt stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageStateVisit()));
        result.append(lineSeparator());
        result.append("Wariancja liczba wizyt stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceStateVisit()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalna liczba odkryć stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxStateDiscovery()));
        result.append(lineSeparator());
        result.append("Minimalna liczba odkryć stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinStateDiscovery()));
        result.append(lineSeparator());
        result.append("Średnia liczba odkryć stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageStateDiscovery()));
        result.append(lineSeparator());
        result.append("Wariancja liczba odkryć stanu" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceStateDiscovery()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Maksymalny rozmiar zbioru" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMaxSubsetSize()));
        result.append(lineSeparator());
        result.append("Minimalny rozmiar zbioru" + colon() + tabulator() + numberPrint(entityStatisticHolder.getMinSubsetSize()));
        result.append(lineSeparator());
        result.append("Średni rozmiar zbioru" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageSubsetSize()));
        result.append(lineSeparator());
        result.append("Wariancja rozmiaru zbioru" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceSubsetSize()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Czas pierwszej wizyty" + colon() + tabulator() + numberPrint(entityStatisticHolder.getFirstStateVisit()));
        result.append(lineSeparator());
        result.append("Średnia pierwszej wizyty" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageFirstStateVisit()));
        result.append(lineSeparator());
        result.append("Wariancja pierwszej wizyty" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceFirstStateVisit()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Czas ostatniej wizyty" + colon() + tabulator() + numberPrint(entityStatisticHolder.getLastStateVisit()));
        result.append(lineSeparator());
        result.append("Średnia ostatniej wizyty" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageLastStateVisit()));
        result.append(lineSeparator());
        result.append("Wariancja ostatniej wizyty" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceLastStateVisit()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Czas pierwszego odkrycia" + colon() + tabulator() + numberPrint(entityStatisticHolder.getFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append("Średnia pierwszego odkrycia" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append("Wariancja pierwszego odkrycia" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceFirstStateDiscovery()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Czas ostatniego odkrycia" + colon() + tabulator() + numberPrint(entityStatisticHolder.getLastStateDiscovery()));
        result.append(lineSeparator());
        result.append("Średnia ostatniego odkrycia" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageLastStateDiscovery()));
        result.append(lineSeparator());
        result.append("Wariancja ostatniego odkrycia" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceLastStateDiscovery()));

        result.append("Stan o najlepszych parametrach");
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej wartości w oparciu o zbiór treningowy");
        result.append(lineSeparator());
        result.append(print(entityStatisticHolder.getBestValuedState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej dokładności klasyfikacji w oparciu o zbiór treningowy");
        result.append(lineSeparator());
        result.append(print(entityStatisticHolder.getBestClassifiedState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej wartości w oparciu o zbiór testowy");
        result.append(lineSeparator());
        result.append(print(entityStatisticHolder.getBestValuedByTestSetState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan o najwięszkej dokładności klasyfikacji w oparciu o zbiór testowy");
        result.append(lineSeparator());
        result.append(print(entityStatisticHolder.getBestClassifiedByTestSetState()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan najmniej przetrenowany w oparciu o wartość");
        result.append(lineSeparator());
        result.append(print(entityStatisticHolder.getBestNotOverfittedStateByValue()));
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Stan najmniej przetrenowany w oparciu o dokładność klasyfikacji");
        result.append(lineSeparator());
        result.append(print(entityStatisticHolder.getBestNotOverfittedStateByClassification()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("Liczba wystąpień atrybutu w zbiorach");
        result.append(lineSeparator());
        for (Entry<Feature, Integer> entry : entityStatisticHolder.getFeatureOccurenceIndexOrdered().entrySet()) {
            result.append("Atrybut" + colon() + tabulator() + numberPrint(entry.getKey().getIndex()) + tabulator() + "Liczba wystąpień" + colon() + tabulator() + numberPrint(entry.getValue()) + tabulator() + "Średnio" + colon() + tabulator() + numberPrint((double) entry.getValue() / (double) entityStatisticHolder.getEntities().size()));
            result.append(lineSeparator());
        }
        return result.toString();
    }

    public static String printPartial(EntitiesStatisticHolder entityStatisticHolder) {
        StringBuilder result = generateStringBuilder();
        result.append("Liczba stanów znalezionych" + colon() + tabulator() + numberPrint(entityStatisticHolder.getEntities().size()));
        result.append(lineSeparator());
        result.append("Średni rozmiar zbioru" + colon() + tabulator() + numberPrint(entityStatisticHolder.getAverageSubsetSize()));
        result.append(lineSeparator());
        result.append("Wariancja rozmiaru zbioru" + colon() + tabulator() + numberPrint(entityStatisticHolder.getVarianceSubsetSize()));
        result.append(lineSeparator());
        return result.toString();
    }

    public static String print(State state) {
        StringBuilder result = generateStringBuilder();
        result.append("Stan" + colon() + space());
        Iterator<Feature> iterator = state.getState().iterator();
        if (!iterator.hasNext()) {
            result.append(empty());
        } else {
            while (iterator.hasNext()) {
                result.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    result.append(separator());
                }
            }
        }
        result.append("  Liczba cech" + colon() + space() + numberPrint(state.getState().size()));
        result.append(lineSeparator());

        result.append("Ocena klasyfikacji" + colon() + space() + numberPrint(state.getClassificationEvaluation()));
        result.append(lineSeparator());
        result.append("Wartość" + colon() + space() + numberPrint(state.getValue()));
        result.append(lineSeparator());

        if (state.getClassificationEvaluationByTestSet() != null) {
            result.append("Ocena klasyfikacji na zbiorze testowym" + colon() + space() + numberPrint(state.getClassificationEvaluationByTestSet()));
            result.append(lineSeparator());
            result.append("Wartość stanu na podstawie zbiru testowego" + colon() + space() + numberPrint(state.getValueByTestSet()));
            result.append(lineSeparator());
        }
        if (state.getClassificationEvaluationByTestSet() != null) {
            result.append("Ocena klasyfikacji na zbiorze uczącym" + colon() + space() + numberPrint(state.getClassificationEvaluationByLearningSet()));
            result.append(lineSeparator());
            result.append("Wartość stanu na podstawie zbiru uczącego" + colon() + space() + numberPrint(state.getValueByLearningSet()));
            result.append(lineSeparator());
        }

        result.append("Liczba wizyt" + colon() + space() + numberPrint(state.getVisitingCount()));
        result.append(space());
        result.append("Czas pierwszej wizyty" + colon() + space() + firstElement(state.getTimeSteps()));
        result.append(lineSeparator());
        result.append("Liczba odkryć" + colon() + space() + numberPrint(state.getDiscoveryCount()));
        result.append(space());
        result.append("Czas pierwszego odkrycia" + colon() + space() + firstElement(state.getDiscoveryTimeSteps()));

        return result.toString();
    }

    public static String print(Action action) {
        StringBuilder description = generateStringBuilder();
        description.append("Akcja" + colon());
        description.append(space());

        description.append("Cechy dodane" + colon());
        description.append(space());
        Iterator<Feature> iterator = action.getAddedFeatures().iterator();
        if (!iterator.hasNext()) {
            description.append(empty());
        } else {
            while (iterator.hasNext()) {
                description.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    description.append(separator());
                }
            }
        }

        description.append(space());
        description.append(space());
        description.append("Cechy usunięte" + colon());
        description.append(space());
        iterator = action.getRemovedFeatures().iterator();
        if (!iterator.hasNext()) {
            description.append(empty());
        } else {
            while (iterator.hasNext()) {
                description.append(numberPrint(iterator.next().getIndex()));
                if (iterator.hasNext()) {
                    description.append(separator());
                }
            }
        }
        description.append(space());
        description.append(space());
        description.append("Wartość" + colon() + space() + numberPrint(action.getValue()));
        description.append(space());
        description.append(space());
        description.append("Liczba wykonań" + colon() + space() + numberPrint(action.getExecutionCount()));
        description.append(space());
        description.append(space());
        description.append("Pierwsze wykonanie" + colon() + space() + firstElement(action.getTimeSteps()));
        description.append(space());
        description.append(space());
        description.append("Liczba symulacji" + colon() + space() + numberPrint(action.getSimulationCount()));
        description.append(space());
        description.append(space());
        description.append("Pierwsza symulacja" + colon() + space() + firstElement(action.getSimulationTimeSteps()));
        description.append(space());
        description.append(space());
        description.append("Liczba aktualizacji" + colon() + space() + numberPrint(action.getUpdateCount()));
        description.append(space());
        description.append(space());
        description.append("Pierwsza aktualizacja" + colon() + space() + firstElement(action.getUpdateTimeSteps()));

        return description.toString();
    }

    public static String print(StateActionEntity entity) {
        if (entity == null) {
            return empty();
        }
        StringBuilder description = new StringBuilder();
        description.append(ProcessOutputPrinter.print(entity.getState()));
        description.append(lineSeparator());
        Iterator<Action> iterator = entity.getActions().iterator();
        if (!iterator.hasNext()) {
            description.append(empty());
        } else {
            while (iterator.hasNext()) {
                description.append(ProcessOutputPrinter.print(iterator.next()));
                if (iterator.hasNext()) {
                    description.append(lineSeparator());
                }
            }
        }
        return description.toString();
    }

    public static String print(Set<StateActionEntity> entities) {
        StringBuilder description = new StringBuilder();
        Iterator<StateActionEntity> iterator = entities.iterator();
        if (!iterator.hasNext()) {
            description.append(empty());
        } else {
            while (iterator.hasNext()) {
                description.append(ProcessOutputPrinter.print(iterator.next()));
                if (iterator.hasNext()) {
                    description.append(lineSeparator());
                    description.append(lineSeparator());
                }
            }
        }
        return description.toString();
    }

    public static String processStatisticGeneratorName(EntitiesStatisticGenerator processStatisticGenerator) {
        if (processStatisticGenerator instanceof EntitiesStatisticGeneratorForGivenStatesNumber) {
            return "Generator statystyk dla wskazanej liczby najlepszych stanów";
        } else if (processStatisticGenerator instanceof EntitiesStatisticGeneratorForMaxEvaluationDifference) {
            return "Generator dla stanów, gdzie maksymalna różnica ich oceny jest nie większa od wartości podanej";
        }
        return "nieznany";
    }

    public static String print(EntitiesStatisticGeneratorForGivenStatesNumber statisticGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append(lineSeparator());
        result.append("**************************Statystki dla podzbiorów o najwyższej dokładności klasyfikacji**************************");
        result.append(lineSeparator());
        result.append("Maksymalna liczba stanów o najlepszej klasyfikacji" + colon() + tabulator() + numberPrint(statisticGenerator.getNumberOfBestClassifiedStates()));
        result.append(lineSeparator());
        result.append(ProcessOutputPrinter.print(statisticGenerator.getBestClassifiedStatisticHolder()));
        result.append(lineSeparator());
        result.append("Stany i akcje");
        result.append(lineSeparator());
        result.append(print(statisticGenerator.getBestClassifiedStatisticHolder().getEntities()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("**************************Statystki dla najlepiej ocenionych podzbiorów**************************");
        result.append(lineSeparator());
        result.append("Maksymalna liczba stanów o największej wartości" + colon() + tabulator() + numberPrint(statisticGenerator.getNumberOfBestValuedStates()));
        result.append(lineSeparator());
        result.append(ProcessOutputPrinter.print(statisticGenerator.getBestValuedStatisticHolder()));
        result.append(lineSeparator());
        result.append("Stany i akcje");
        result.append(lineSeparator());
        result.append(print(statisticGenerator.getBestValuedStatisticHolder().getEntities()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("**************************Statystki dla wszystkich podzbiorów**************************");
        result.append(lineSeparator());
        result.append(ProcessOutputPrinter.print(statisticGenerator.getWholeStatesStatisticHolder()));

        return result.toString();
    }

    public static String print(EntitiesStatisticGeneratorForMaxEvaluationDifference processStatisticGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append(lineSeparator());
        result.append("**************************Statystki dla podzbiorów o najwyższej dokładności klasyfikacji**************************");
        result.append(lineSeparator());
        result.append("Maksymalna różnica klasyfikacji" + colon() + tabulator() + numberPrint(processStatisticGenerator.getMaxBestStateClassificationDifference()));
        result.append(lineSeparator());
        result.append(ProcessOutputPrinter.print(processStatisticGenerator.getBestClassifiedStatisticHolder()));
        result.append(lineSeparator());
        result.append("Stany i akcje");
        result.append(lineSeparator());
        result.append(print(processStatisticGenerator.getBestClassifiedStatisticHolder().getEntities()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("**************************Statystki dla najlepiej ocenionych podzbiorów**************************");
        result.append(lineSeparator());
        result.append("Maksymalna różnica wartości" + colon() + tabulator() + numberPrint(processStatisticGenerator.getMaxBestStateValueDifference()));
        result.append(lineSeparator());
        result.append(ProcessOutputPrinter.print(processStatisticGenerator.getBestValuedStatisticHolder()));
        result.append(lineSeparator());
        result.append("Stany i akcje");
        result.append(lineSeparator());
        result.append(print(processStatisticGenerator.getBestValuedStatisticHolder().getEntities()));

        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("**************************Statystki dla wszystkich podzbiorów**************************");
        result.append(lineSeparator());
        result.append(ProcessOutputPrinter.print(processStatisticGenerator.getWholeStatesStatisticHolder()));

        return result.toString();
    }

    public static String print(EntitiesStatisticGenerator processStatisticGenerator) {
        StringBuilder result = generateStringBuilder();
        result.append("Generator statystyk" + colon() + tabulator() + processStatisticGeneratorName(processStatisticGenerator));
        result.append(lineSeparator());

        if (processStatisticGenerator instanceof EntitiesStatisticGeneratorForGivenStatesNumber) {
            result.append(print((EntitiesStatisticGeneratorForGivenStatesNumber) processStatisticGenerator));
        } else if (processStatisticGenerator instanceof EntitiesStatisticGeneratorForMaxEvaluationDifference) {
            result.append(print((EntitiesStatisticGeneratorForMaxEvaluationDifference) processStatisticGenerator));
        }

        return result.toString();
    }

    public static String print(HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> statisticsCollections) {
        StringBuilder result = generateStringBuilder();
        result.append("****************GRUPOWE STATYSTKI DLA STANÓW O NAJWIĘKSZEJ DOKŁADNOŚCI KLASYFIKACJI****************");
        result.append(lineSeparator());
        if (statisticsCollections.containsKey(StatisticHolderKind.BEST_CLASSIFIED)) {
            result.append(print(statisticsCollections.get(StatisticHolderKind.BEST_CLASSIFIED)));
        } else {
            result.append(empty());
        }
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("****************GRUPOWE STATYSTKI DLA STANÓW NAJEPIEJ OCENIONYCH****************");
        result.append(lineSeparator());
        if (statisticsCollections.containsKey(StatisticHolderKind.BEST_VALUED)) {
            result.append(print(statisticsCollections.get(StatisticHolderKind.BEST_VALUED)));
        } else {
            result.append(empty());
        }
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append(lineSeparator());
        result.append("****************GRUPOWE STATYSTKI DLA WSZYSTKICH STANOW****************");
        result.append(lineSeparator());
        if (statisticsCollections.containsKey(StatisticHolderKind.WHOLE)) {
            result.append(print(statisticsCollections.get(StatisticHolderKind.WHOLE)));
        } else {
            result.append(empty());
        }

        return result.toString();
    }
}
