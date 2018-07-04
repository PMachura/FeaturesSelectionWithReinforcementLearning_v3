/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.experiment;

import machura.przemyslaw.featuresselectionwithrl.experiment.Experiment;

/**
 *
 * @author Przemek
 */
public class ExperimentExecutor {

    public static void executeExperiment_1_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_1_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_2_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_2_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_3_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_3_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_4_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_4_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_5_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_5_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_6_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_6_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_7_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_7_1();
        experiment.executeExperiment();
    }

    public static void executeExperiment_8_1() throws Exception {
        Experiment experiment = ExperimentFactory.experiment_8_1();
        experiment.executeExperiment();
    }


    public static void main(String[] args) throws Exception {

        executeExperiment_1_1();
        executeExperiment_2_1();
        executeExperiment_3_1();
        executeExperiment_4_1();
        executeExperiment_5_1();
        executeExperiment_6_1();
        executeExperiment_7_1();
        executeExperiment_8_1();
    }
}
