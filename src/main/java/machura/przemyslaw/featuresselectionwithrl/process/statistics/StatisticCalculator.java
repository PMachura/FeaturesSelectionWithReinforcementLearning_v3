/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.statistics;

import java.util.TreeSet;

/**
 *
 * @author Przemek
 */
public class StatisticCalculator {

    public static Double average(Double[] values) {
        if(values.length == 0)
            return null;
        
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / (double) values.length;
    }

    public static Double average(Integer[] values) {
        if(values.length == 0)
            return null;
        
        double sum = 0.0;
        for (int value : values) {
            sum += value;
        }
        return sum / (double) values.length;
    }

    public static Double variance(Double[] values, Double average) {
        if(values.length == 0 || average == null)
            return null;
        
        double sum = 0.0;
        for (Double value : values) {
            sum += Math.pow(value - average, 2);
        }
        return sum / (double) values.length;
    }

    static Double variance(Double[] values) {     
        return variance(values, average(values));
    }

    public static Double variance(Integer[] values, Double average) {
        if(values.length == 0 || average == null)
            return null;
        
        double sum = 0.0;
        for (int value : values) {
            sum += Math.pow(value - average, 2);
        }
        return sum / (double) values.length;
    }

    public static Double variance(Integer[] values) {
        return variance(values, average(values));
    }

    public static Integer setDifferentation(TreeSet<Integer> first, TreeSet<Integer> second) {
        int difference = 0;
        int common = 0;
        for (Integer element : first) {
            if (second.contains(element)) {
                common++;
            }
        }
        difference = (first.size() - common) + (second.size() - common);
        return difference;
    }
    
    public static Double findMax(Double[] values){
        if(values.length == 0)
            return null;
        
        Double max = -Double.MAX_VALUE;
        for(Double value : values){
            if(value > max){
                max = value;
            }
        }
        return max;
    }
    
    public static Double findMin(Double [] values){
        if(values.length == 0)
            return null;
        
        Double min = Double.MAX_VALUE;
        for(Double value : values){
            if(value < min){
                min = value;
            }
        }
        return min;
    }
    
    public static Integer findMax(Integer[] values){
        if(values.length == 0)
            return null;
        
        Integer max = Integer.MIN_VALUE;
        for(Integer value : values){
            if(value > max){
                max = value;
            }
        }
        return max;
    }
    
    public static Integer findMin(Integer [] values){
        if(values.length == 0)
            return null;
        
        Integer min = Integer.MAX_VALUE;
        for(Integer value : values){
            if(value < min){
                min = value;
            }
        }
        return min;
    }
    
    public static Double sum(Double [] values){
        if(values.length == 0)
            return null;
        
        Double sum = 0.0;
        for(Double value : values){
            sum += value;
        }
        
        return sum;
    }
    
    public static Integer sum(Integer [] values){
        if(values.length == 0)
            return null;
        
        Integer sum = 0;
        for(Integer value : values){
            sum += value;
        }
        
        return sum;
    }
    
    
}
