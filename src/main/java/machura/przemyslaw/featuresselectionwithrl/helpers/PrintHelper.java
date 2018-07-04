/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.helpers;

import java.text.DecimalFormat;
import java.util.TreeSet;

/**
 *
 * @author Przemek
 */
public class PrintHelper {
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00000");
    public static String lineSeparator = System.getProperty("line.separator");


    
    public static String firstElement(TreeSet<Integer> set) {
        return set.isEmpty() ? "-" : numberPrint(set.first());
    }

    public static String booleanPrint(boolean value) {
        return value == true ? "tak" : "nie";
    }

    public static String numberPrint(Double value) {
        return value == null ? "-" : decimalFormat.format(value);
    }

    public static String numberPrint(Integer value) {
        return value == null ? "-" : String.valueOf(value);
    }
    
    public static String numberPrint(Long value) {
        return value == null ? "-" : String.valueOf(value);
    }

    public static String separator() {
        return ",";
    }

    public static String empty() {
        return "-";
    }

    public static String lineSeparator() {
        return lineSeparator;
    }

    public static String space() {
        return " ";
    }

    public static String tabulator() {
        return "\t";
    }

    public static String colon() {
        return ":";
    }

    public static StringBuilder generateStringBuilder() {
        return new StringBuilder();
    }
}
