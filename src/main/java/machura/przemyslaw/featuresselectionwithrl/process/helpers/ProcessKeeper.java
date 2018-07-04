/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.helpers;

import java.util.Iterator;
import java.util.LinkedList;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;

/**
 *
 * @author Przemek
 */
public class ProcessKeeper {


    private Integer keepingStepsLimit;
    private LinkedList<ProcessVariables> processVariablesList;
    

    public ProcessKeeper() {
        this.processVariablesList = new LinkedList<ProcessVariables>();
        this.keepingStepsLimit = 5;
    }

    public ProcessKeeper copy(){
        ProcessKeeper copy = new ProcessKeeper();
        copy.processVariablesList = new LinkedList<ProcessVariables>(this.processVariablesList);
        copy.keepingStepsLimit = this.keepingStepsLimit;
        return copy;
    }
    
    
    public void clear(){
        this.processVariablesList.clear();
    }
    
    public Boolean add(ProcessVariables processVariable) {
        if (processVariablesList.size() >= keepingStepsLimit) {
            processVariablesList.remove();
            return processVariablesList.add(processVariable);
        }else{
            return processVariablesList.add(processVariable);
        }

    }
    
    public ProcessVariables removeLast(){
        return processVariablesList.removeLast();
        
    }
    
    public Action getActionDescending (int descendingActionPosition){
        return processVariablesList.get(processVariablesList.size() - descendingActionPosition).getAction();
    }
    
    

    /**
     * Ilość kroków >= 1
     * @param lastStepsNumber
     * @return
     * @throws Exception 
     */
    public Double getDescendingDiscountedReturn(Integer lastStepsNumber, Double discountingCoefficient) throws Exception {
        if (lastStepsNumber > keepingStepsLimit) {
            throw new Exception("Zażądano obliczenia zwrotu z liczby kroków większej, niż zapamiętywane");
        }
        Iterator<ProcessVariables> iter = processVariablesList.descendingIterator();
        ProcessVariables processVariables;
        int count = 0;
        Double ret = 0.0;
        while (iter.hasNext() && (count < lastStepsNumber)) {
            processVariables = iter.next();
            ret += processVariables.getReward().getValue() * Math.pow(discountingCoefficient, lastStepsNumber - count - 1);
            count++;
        }
        if (count < lastStepsNumber) {
            throw new Exception("Zbyt mało zapamiętanych kroków, by obliczyć żądany zwrot");
        }
        return ret;
    }
    
    public ProcessVariables getLastProcessVariables(){
        return processVariablesList.getLast();
    }
    
    /**
     * Parametr powinien być >=1;
     * @param number
     * @return 
     */
    public ProcessVariables getLastProcessVariables(Integer indexFromEnd){
        if(this.keepingStepsLimit < indexFromEnd){
            throw new RuntimeException("Process Keeper: próba pobrania ProcessVariables z listy o indeksie większym, niż przewidywany rozmiar listy");
        }
        return processVariablesList.get(processVariablesList.size() - indexFromEnd);
    }


    public int keepedStepsSize(){
        return processVariablesList.size();
    }
    
    public int getKeepingStepsLimit() {
        return keepingStepsLimit;
    }
    public void setKeepingStepsLimit(int keepingStepsLimit) {
        this.keepingStepsLimit = keepingStepsLimit;
    }
    
    public LinkedList<ProcessVariables> getProcessVariablesList() {
        return processVariablesList;
    }
    public void setProcessVariablesList(LinkedList<ProcessVariables> processVariablesSequence) {
        this.processVariablesList = processVariablesSequence;
    }

    
}
