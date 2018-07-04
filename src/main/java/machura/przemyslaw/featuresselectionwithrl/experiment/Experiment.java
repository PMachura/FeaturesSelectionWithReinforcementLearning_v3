/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import machura.przemyslaw.featuresselectionwithrl.helpers.Debuger;
import machura.przemyslaw.featuresselectionwithrl.filehandlers.FileHandler;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcessWithPlanners;
import machura.przemyslaw.featuresselectionwithrl.process.ProcessGenerator;
import machura.przemyslaw.featuresselectionwithrl.process.ProcessGeneratorFactory;
import machura.przemyslaw.featuresselectionwithrl.helpers.ProcessOutputPrinter;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.StatisticHolderKind;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticGenerator;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticGeneratorForGivenStatesNumber;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticHolderCollection;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.ProcessStatisticGenerator;

/**
 *
 * @author Przemek
 */
public class Experiment {

    private static final String resultDirectory = "eksperymenty/";
    private static final String experimentDescriptionFileName = "opisEksperymentu.txt";

    private static final String maxNumberOfBestStates = "MaxLiczbaStanowNajlepszych";
    private static final String maxDifferenceOfBestStates = "MaxRoznicaWartosciStanowNajlepszych";
    private static final String singleProcessEntitiesStatisticsFileName = "pojedynczyProcesStatystykiEncji";
    private static final String singleKindProcessEntitiesSummaryStatistics = "podsumowanieProcesuStatystykiEncji";

    private static final String singleKindProcessSummaryStatistics = "podsumowanieProcesuStatystyki";

    private static final String singleProcessDescriptionFileName = "opisProcesu.txt";
    private static final String singleProcessAfterExecutionDescriptionFileName = "opisProcesuPoWykoananiu";

    String name = "Experiment";
    String description = "ExperimentDescription";
    ArrayList<ProcessGenerator> processesGeneratorList = new ArrayList<ProcessGenerator>();
    Integer numberOfEachProcessExecution = 3;
    HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> entitiesStatisticsCollectionsForMaxNumberOfBestStates = new HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection>();
    HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> entitiesStatisticCollectionsForMaxDifferenceForBestStates = new HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection>();

    EntitiesStatisticGenerator entitiesStatisticGeneratorWithMaxEvaluationDifference = new EntitiesStatisticGeneratorForGivenStatesNumber();
    EntitiesStatisticGenerator entitiesStatisticGeneratorForGivenStatesNumber = new EntitiesStatisticGeneratorForGivenStatesNumber();
    Boolean generateStatisticForWholeEntities = false;

    ProcessStatisticGenerator processStatisticGenerator = new ProcessStatisticGenerator();

    public Experiment() {
        resetStatisticsCollectionsForMaxNumberOfBestStates();
    }

    public void resetStatisticsCollectionsForMaxNumberOfBestStates() {
        entitiesStatisticsCollectionsForMaxNumberOfBestStates.put(StatisticHolderKind.BEST_CLASSIFIED, new EntitiesStatisticHolderCollection());
        entitiesStatisticsCollectionsForMaxNumberOfBestStates.put(StatisticHolderKind.BEST_VALUED, new EntitiesStatisticHolderCollection());
        entitiesStatisticsCollectionsForMaxNumberOfBestStates.put(StatisticHolderKind.WHOLE, new EntitiesStatisticHolderCollection());
    }

    public boolean addProcessGenerator(ProcessGenerator processGenerator) {
        return this.processesGeneratorList.add(processGenerator);
    }

    public void executeExperiment() throws IOException, Exception {
        String experimentFolderDirectory = resultDirectory + name + "/";
        FileHandler.save(experimentFolderDirectory, experimentDescriptionFileName, ProcessOutputPrinter.print(this));

        for (ProcessGenerator processGenerator : processesGeneratorList) {

            System.out.println("[EXPERYMENT] Rozpoczecie przetwarzania nowego procesu");
            FSProcess process = processGenerator.generateProcess();
            String processFolderDirectory = experimentFolderDirectory + processGenerator.getName() + "/"; //tu można zamienić na process.getName
            FileHandler.save(processFolderDirectory, singleProcessDescriptionFileName, ProcessOutputPrinter.print(process));
            processStatisticGenerator = new ProcessStatisticGenerator();
            resetStatisticsCollectionsForMaxNumberOfBestStates();

            for (int i = 0; i < numberOfEachProcessExecution; i++) {
                System.out.println("[EXPERYMENT] Przetwarzanie danego procesu: " + i);
                process = processGenerator.generateProcess();
                process.performEvaluationForSplitedInputDataWithWholeFeatures();
                process.performEvaluationForSplitedInputDataWithWholeFeaturesByLearnSet();
                process.run();

                System.out.println("[EXPERYMENT] Dodawanie statistyk do ProcessStatisticGenerator");
                processStatisticGenerator.createAndAddStatisticHolderToList((FSProcessWithPlanners) process);

                System.out.println("[EXPERYMENT] Generowanie statystyk przez EntitiesStatisticGenerator");
                if (generateStatisticForWholeEntities) {
                    entitiesStatisticGeneratorForGivenStatesNumber.generateStatistics(process);
                } else {
                    entitiesStatisticGeneratorForGivenStatesNumber.generateStatisticsForBestStates(process);
                }

                entitiesStatisticsCollectionsForMaxNumberOfBestStates.get(StatisticHolderKind.BEST_CLASSIFIED).add(entitiesStatisticGeneratorForGivenStatesNumber.getBestClassifiedStatisticHolder());
                entitiesStatisticsCollectionsForMaxNumberOfBestStates.get(StatisticHolderKind.BEST_VALUED).add(entitiesStatisticGeneratorForGivenStatesNumber.getBestValuedStatisticHolder());
                if (generateStatisticForWholeEntities) {
                    entitiesStatisticsCollectionsForMaxNumberOfBestStates.get(StatisticHolderKind.WHOLE).add(entitiesStatisticGeneratorForGivenStatesNumber.getWholeStatesStatisticHolder());
                }

                System.out.println("[EXPERYMENT] Zapis statystyk do pliku generowanych przez EntitiesStatisticGenerator");
                FileHandler.save(processFolderDirectory, singleProcessEntitiesStatisticsFileName + entitiesStatisticGeneratorForGivenStatesNumber.getName() + i + ".txt", ProcessOutputPrinter.print(entitiesStatisticGeneratorForGivenStatesNumber));

                System.out.println("[EXPERYMENT] Zapis opisu procesu po wykonaniu");
                FileHandler.save(processFolderDirectory, singleProcessAfterExecutionDescriptionFileName + i + ".txt", ProcessOutputPrinter.print(process));

            }
            System.out.println("[EXPERYMENT] Generowanie statystyk przez ProcessStatisticGenerator");
            processStatisticGenerator.generateStatistics();
            FileHandler.save(processFolderDirectory, singleKindProcessSummaryStatistics + ".txt", ProcessOutputPrinter.print(processStatisticGenerator));

            System.out.println("[EXPERYMENT] Generowanie statystyk przez EntitiesStatisticHolderCollection");
            entitiesStatisticsCollectionsForMaxNumberOfBestStates.get(StatisticHolderKind.BEST_CLASSIFIED).computeAll();
            entitiesStatisticsCollectionsForMaxNumberOfBestStates.get(StatisticHolderKind.BEST_VALUED).computeAll();
            if (generateStatisticForWholeEntities) {
                entitiesStatisticsCollectionsForMaxNumberOfBestStates.get(StatisticHolderKind.WHOLE).computeAll();
            }
            
            System.out.println("[EXPERYMENT] Zapis statystyk wygenerowanych przez EntitiesStatisticHolderCollection");
            FileHandler.save(processFolderDirectory, singleKindProcessEntitiesSummaryStatistics + maxNumberOfBestStates + ".txt", ProcessOutputPrinter.print(entitiesStatisticsCollectionsForMaxNumberOfBestStates));
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ProcessGenerator> getProcessesGeneratorList() {
        return processesGeneratorList;
    }

    public void setProcessesGeneratorList(ArrayList<ProcessGenerator> processesGeneratorList) {
        this.processesGeneratorList = processesGeneratorList;
    }

    public Integer getNumberOfEachProcessExecution() {
        return numberOfEachProcessExecution;
    }

    public void setNumberOfEachProcessExecution(Integer numberOfEachProcessExecution) {
        this.numberOfEachProcessExecution = numberOfEachProcessExecution;
    }

    public EntitiesStatisticGenerator getEntitiesStatisticGeneratorWithMaxEvaluationDifference() {
        return entitiesStatisticGeneratorWithMaxEvaluationDifference;
    }

    public void setEntitiesStatisticGeneratorWithMaxEvaluationDifference(EntitiesStatisticGenerator entitiesStatisticGeneratorWithMaxEvaluationDifference) {
        this.entitiesStatisticGeneratorWithMaxEvaluationDifference = entitiesStatisticGeneratorWithMaxEvaluationDifference;
    }

    public HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> getEntitiesStatisticsCollectionsForMaxNumberOfBestStates() {
        return entitiesStatisticsCollectionsForMaxNumberOfBestStates;
    }

    public void setEntitiesStatisticsCollectionsForMaxNumberOfBestStates(HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> entitiesStatisticsCollectionsForMaxNumberOfBestStates) {
        this.entitiesStatisticsCollectionsForMaxNumberOfBestStates = entitiesStatisticsCollectionsForMaxNumberOfBestStates;
    }

    public HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> getEntitiesStatisticCollectionsForMaxDifferenceForBestStates() {
        return entitiesStatisticCollectionsForMaxDifferenceForBestStates;
    }

    public void setEntitiesStatisticCollectionsForMaxDifferenceForBestStates(HashMap<StatisticHolderKind, EntitiesStatisticHolderCollection> entitiesStatisticCollectionsForMaxDifferenceForBestStates) {
        this.entitiesStatisticCollectionsForMaxDifferenceForBestStates = entitiesStatisticCollectionsForMaxDifferenceForBestStates;
    }

    public EntitiesStatisticGenerator getEntitiesStatisticGeneratorForGivenStatesNumber() {
        return entitiesStatisticGeneratorForGivenStatesNumber;
    }

    public void setEntitiesStatisticGeneratorForGivenStatesNumber(EntitiesStatisticGenerator entitiesStatisticGeneratorForGivenStatesNumber) {
        this.entitiesStatisticGeneratorForGivenStatesNumber = entitiesStatisticGeneratorForGivenStatesNumber;
    }

    public Boolean getGenerateStatisticForWholeEntities() {
        return generateStatisticForWholeEntities;
    }

    public void setGenerateStatisticForWholeEntities(Boolean generateStatisticForWholeEntities) {
        this.generateStatisticForWholeEntities = generateStatisticForWholeEntities;
    }

    public ProcessStatisticGenerator getProcessStatisticGenerator() {
        return processStatisticGenerator;
    }

    public void setProcessStatisticGenerator(ProcessStatisticGenerator processStatisticGenerator) {
        this.processStatisticGenerator = processStatisticGenerator;
    }

}
