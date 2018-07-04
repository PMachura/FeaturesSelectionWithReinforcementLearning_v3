/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.filehandlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import machura.przemyslaw.featuresselectionwithrl.process.FSProcess;
import machura.przemyslaw.featuresselectionwithrl.helpers.ProcessOutputPrinter;
import machura.przemyslaw.featuresselectionwithrl.process.statistics.EntitiesStatisticGenerator;

/**
 *
 * @author Przemek
 */
public class FileHandler {

    private static final String resultDirectory = "rezultatyTest/";
    private static NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    public static void createFile(String folderDirectory) {
        File file = new File(folderDirectory);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private static void writeValueToFile(String value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(value);
        }
    }

    private static void writeValueToFile(Double value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(decimalFormat.format(value));
        }
    }

    private static void writeValueToFile(Integer value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(value.toString());
        }
    }

    private static void writeSemicolonToFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(";");
    }

    private static void writeTabulatorToFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("\t");
    }


    public static void save(String folderDirectory, String fileName, String toSave) throws IOException {
        createFile(folderDirectory);
        File file = new File(folderDirectory + fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(toSave);
        
        bufferedWriter.close();
    }
    
    public static void save(String fileDirectory, String toSave) throws IOException {
        File file = new File(fileDirectory);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(toSave);
        
        bufferedWriter.close();
    }
}
