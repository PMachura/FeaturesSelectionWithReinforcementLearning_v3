/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process;

import machura.przemyslaw.featuresselectionwithrl.filehandlers.InstanceFileHandler;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationTool;
import machura.przemyslaw.featuresselectionwithrl.classifiers.ClassificationToolFactory;
import machura.przemyslaw.featuresselectionwithrl.returns.RewardGenerator;
import machura.przemyslaw.featuresselectionwithrl.strategy.EGreedySingleFeatureStrategy;
import machura.przemyslaw.featuresselectionwithrl.updaters.QUpdater;
import machura.przemyslaw.featuresselectionwithrl.updaters.StateValueEntityUpdater;
import weka.core.Instances;

/**
 *
 * @author Przemek
 */
public class ProcessGenerator {

   
    protected Integer stepsNumber;
    protected ClassificationTool classificationTool;
    protected String dataFile;
    protected StateValueEntityUpdater entityUpdater;
    protected Integer maxFeaturesSubsetSize = 50;
    public String name = "Process";

    public ProcessGenerator(){
        stepsNumber = 1000;
        classificationTool = ClassificationToolFactory.createNaiveBayesnClassificationTool();
        entityUpdater = new QUpdater();
        dataFile = "groups/uci/nominal/soybean.arff";
    }
    
    public ProcessGenerator(String name){
        this();
        this.name = name;
    }
    
    public ProcessGenerator(Integer stepsNumber, ClassificationTool classificationTool ,  String dataFile){
        this();
        this.stepsNumber = stepsNumber;
        this.classificationTool = classificationTool;
        this.dataFile = dataFile;
        
    }
    
    public ProcessGenerator(Integer stepsNumber, ClassificationTool classificationTool , StateValueEntityUpdater entityUpdater,  String dataFile){
        this();
        this.stepsNumber = stepsNumber;
        this.classificationTool = classificationTool;
        this.dataFile = dataFile;
        this.entityUpdater = entityUpdater;
    }
    
    public ProcessGenerator(String name, Integer stepsNumber, ClassificationTool classificationTool ,  String dataFile){
        this();
        this.stepsNumber = stepsNumber;
        this.classificationTool = classificationTool;
        this.dataFile = dataFile;
        this.name = name;
    }
    
    public ProcessGenerator(String name, Integer stepsNumber, ClassificationTool classificationTool , StateValueEntityUpdater entityUpdater, String dataFile){
        this();
        this.stepsNumber = stepsNumber;
        this.classificationTool = classificationTool;
        this.dataFile = dataFile;
        this.name = name;
        this.entityUpdater = entityUpdater;
    }
    
    public ProcessGenerator(String name, Integer stepsNumber, Integer maxFeatures, ClassificationTool classificationTool , StateValueEntityUpdater entityUpdater, String dataFile){
        this();
        this.stepsNumber = stepsNumber;
        this.classificationTool = classificationTool;
        this.dataFile = dataFile;
        this.name = name;
        this.entityUpdater = entityUpdater;
        this.maxFeaturesSubsetSize = maxFeatures;
    }
      
    public FSProcess generateProcess() throws Exception {
        Instances data = InstanceFileHandler.getDatasetInstances(dataFile);
        FSProcessWithPlanners process = new FSProcessWithPlanners(data, dataFile);
        return process;
    };
    
    public FSProcess generateProcess(Integer stepsNumber, ClassificationTool classificationTool, String dataFile) throws Exception {
        return generateProcess();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
