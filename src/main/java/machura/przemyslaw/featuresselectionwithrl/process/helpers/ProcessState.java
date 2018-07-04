/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.helpers;

import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import java.util.BitSet;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.states.State;

/**
 * @param state stan złożony z liczb całkowitych- reprezentuje wybrany podzbiór atrybutów (wartości wskazują na pozycję atrybutów w zbiorze wejściowym)
 * @param bitState stan reprezentowany poprzez zapis biotowy- wartości 1 ustawiane na podstawie zmiennej state
 * @param bitStateSize określa rozmiar słowa (ilość bitów) dla bitState- odpowiada liczbie atrybutów z wejściowego zbioru danych
 * @author Przemek
 */
public class ProcessState {

    TreeSet<Feature> state;
    BitSet bitState;
    Integer bitStateSize;

    public ProcessState(Integer numberOfAttributesInBasicDataSet) {
        this.state = new TreeSet<Feature>();
        this.bitStateSize = numberOfAttributesInBasicDataSet;
        this.bitState = new BitSet(this.bitStateSize);
    }

    
    public ProcessState copy(){
        ProcessState copy = new ProcessState(bitStateSize);
        copy.state = new TreeSet<Feature>(this.state);
        copy.bitStateSize = this.bitStateSize;
        copy.bitState = (BitSet)this.bitState.clone();
        return copy;
    }
    /**
     * Funkcja zmienia zawartość pola state oraz bitState na podstawie przekazanej akcji reprezentującej atrybuty do dodania lub usunięcia
     * @param featuresToAddOrRemove zbior cech, jakie maja być dodane lub usunięte z wskazywanego przez obiekt klasy podzbioru (przez pola state i bitState)
     * @throws Exception 
     */
    public void updateBySelectedAction(SelectedAction selectedAction) throws Exception {
        updateByFeaturesToAdd(selectedAction.getFeaturesToAdd());
        updateByFeaturesToRemove(selectedAction.getFeaturesToRemove());
    }
    
    
    public void setByState(State state){
        this.state = new TreeSet<Feature>(state.getState());
        this.bitState.clear();
        for(Feature feature : state.getState()){
            this.bitState.flip(feature.getIndex());
        }     
    }
    
   
    
    /**
     * Sprawdzenie, czy reprezentacja stanu poprzez bitState jest spójna, zgodna z reprezentacją poprzez state
     * @param feature
     * @return
     * @throws Exception 
     */
    public boolean checkConsistency(Feature feature) throws Exception{
        if(state.contains(feature) == (bitState.get(feature.getIndex())))                
            return true;
        
        throw new Exception("Wartość stanu nie zgadza się z reprezentacją bitową");
        
    }
    
    public boolean contains(Feature feature) throws Exception{
        checkConsistency(feature);
        return state.contains(feature);
    }
    
    public void updateByFeaturesToAdd(TreeSet<Feature> featuresToAdd) throws Exception{
              
        for(Feature feature : featuresToAdd){           
            if(this.contains(feature)){
              System.out.println("Wybrano cechę do dodania, która już znajduje się w wybranym podzbiorze");  
            }else{
               state.add(feature); 
               bitState.flip(feature.getIndex());
            }            
        }
    }
    
    public void updateByFeaturesToRemove(TreeSet<Feature> featuresToRemove) throws Exception{
        for(Feature feature : featuresToRemove){
            if(!this.contains(feature)){
                System.out.println("Wybrano cechę do usunięcia, która nie znajduje się w wybranym podzbiorze");
            }else{
                state.remove(feature);
                bitState.flip(feature.getIndex());
            }
        }
    }

    public TreeSet<Feature> getState() {
        return state;
    }

    public BitSet getBitState() {
        return bitState;
    }

    public Integer getBitStateSize() {
        return bitStateSize;
    }

    public void setBitStateSize(Integer bitStateSize) {
        this.bitStateSize = bitStateSize;
    }
    
    
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(state);
        return sb.toString();
    }
}
