/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.experiment;

import machura.przemyslaw.featuresselectionwithrl.experiment.Experiment;
import java.util.ArrayList;
import machura.przemyslaw.featuresselectionwithrl.process.ProcessGenerator;
import machura.przemyslaw.featuresselectionwithrl.process.ProcessGeneratorFactory;

/**
 *
 * @author Przemek
 */
public class ExperimentFactory {
    
    private static void addProcessGeneratorsToExperiment(ArrayList<ProcessGenerator> processGenerators, Experiment experiment){
        for(ProcessGenerator generator : processGenerators){
            experiment.addProcessGenerator(generator);
        }
    }
    
    public static Experiment experiment_1_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setName("Eksperyment_1_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setGenerateStatisticForWholeEntities(true);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnicy w generowanych rozmiarach zbiorów na bazie zmiany sposobu obliczania prawdoposobieństwa dodania atrybutu");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_1_1(),experiment);
        return experiment;
    }
    
    public static Experiment experiment_2_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setName("Eksperyment_2_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setGenerateStatisticForWholeEntities(true);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnicy w generowanych rozmiarach zbiorów w zależności od współczynnika istotności rozmiaru zbioru, którym posługuje się generator nagród");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_2_1(),experiment);
        return experiment;
    }
    
    public static Experiment experiment_3_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setName("Eksperyment_3_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnic w przypadku użycia bądź nie algorytmów planujących powrotu do stanu najlepszego");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_3_1(),experiment);
        return experiment;
    }
    
    public static Experiment experiment_4_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setName("Eksperyment_4_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnic w przypadku użycia bądź nie algorytmów planujących symulowanie akcji");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_4_1(),experiment);
        return experiment;
    }
    
    public static Experiment experiment_5_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setGenerateStatisticForWholeEntities(false);
        experiment.setName("Eksperyment_5_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnic w przypadku użycia różnych aktualizatorów akcji");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_5_1(),experiment);
        return experiment;
    }
    
     public static Experiment experiment_6_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setGenerateStatisticForWholeEntities(false);
        experiment.setName("Eksperyment_6_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnic w przypadku różnego współczynnika dyskontowania");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_6_1(),experiment);
        return experiment;
    }
     
     public static Experiment experiment_7_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setGenerateStatisticForWholeEntities(false);
        experiment.setName("Eksperyment_7_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setDescription("Ekpseryment ma na celu pokazanie różnic w przypadku różnej liczby operacji w ramach jednej akcji");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_7_1(),experiment);
        return experiment;
    }
     
      public static Experiment experiment_8_1() throws Exception{
        Experiment experiment = new Experiment();
        experiment.setGenerateStatisticForWholeEntities(false);
        experiment.setName("Eksperyment_8_1");
        experiment.setNumberOfEachProcessExecution(5);
        experiment.setDescription("Ekpseryment ma na celu pokazanie możliwości metody dla dużego rozmiaru danych");
        addProcessGeneratorsToExperiment(ProcessGeneratorFactory.ProcessGenerators_8_1(),experiment);
        return experiment;
    }
}
