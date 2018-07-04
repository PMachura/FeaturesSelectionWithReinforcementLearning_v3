/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.classifiers;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 *
 * @author Przemek
 */
public class ClassificationToolFactory {
    
    public static ClassificationTool createJ48ClassificationTool(){
        return new ClassificationTool(new J48()); 
    }
    public static ClassificationTool createJ48ClassificationTool(Instances data) throws Exception{
        return new ClassificationTool(new J48(), data); 
    }
    
    public static ClassificationTool createNaiveBayesnClassificationTool()  {
        return new ClassificationTool(new NaiveBayes());
    }
    public static ClassificationTool createNaiveBayesnClassificationTool(Instances data) throws Exception {
        return new ClassificationTool(new NaiveBayes(), data);
    }
    
    public static ClassificationTool createSVMClassificationTool()  {
        return new ClassificationTool(new SMO());
    }
    public static ClassificationTool createSVMClassificationTool(Instances data) throws Exception {
        return new ClassificationTool(new SMO(), data);
    }
}
