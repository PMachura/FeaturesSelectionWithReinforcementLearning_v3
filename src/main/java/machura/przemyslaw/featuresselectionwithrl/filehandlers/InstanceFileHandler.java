/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.filehandlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author Przemek
 */
public class InstanceFileHandler {
    
    static String datasetPath = "datasets/";
    
    public static DataSource getDatasetDataSource(String filePath){
        DataSource source = null;
        try {
            source = new DataSource(datasetPath+filePath);
        } catch (Exception ex) {
            Logger.getLogger(InstanceFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return source;
    }
    
     public static Instances getDatasetInstances(String filePath){
        try {
            return getDatasetDataSource(filePath).getDataSet();
        } catch (Exception ex) {
            Logger.getLogger(InstanceFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
     public static void saveDatasetInstances(Instances data, String filePath){
        try {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File(filePath));
            saver.writeBatch();
        } catch (IOException ex) {
            Logger.getLogger(InstanceFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
