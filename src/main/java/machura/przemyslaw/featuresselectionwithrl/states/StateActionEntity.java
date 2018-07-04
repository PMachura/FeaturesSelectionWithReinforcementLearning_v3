/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.states;

import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * @param state stan złożony z liczb całkowitych- reprezentuje wybrany podzbiór atrybutów (wartości wskazują na pozycję atrybutów w zbiorze wejściowym)
 * @param actionsValues akcje podjęte dla danego stanu oraz ich wartości, pojedynczą akcją jest zbiór liczb całkowitych reprezentujący atrybuty dodane lub usunięte
 * @param stateValue wartość stanu
 * @param evaluation ocena stanu uzyskana na posdtawie zbioru cech (zazwyczaj jest to dokładność klasyfikatora) @state
 * @param isEvaluated czy stan state był oceniany (zazwyczaj przez klasyfikator)
 * @author Przemek
 */
public class StateActionEntity implements Comparable<StateActionEntity>{
    State state;
    LinkedHashSet<Action> actions;

    /**
     * Konstruktor domyślny
     */
    public StateActionEntity() {
        this.state = new State();
        this.actions = new LinkedHashSet<Action>();
    }

    /**
     * Konstrutkor dla stanu, dla którego została wybrana akcja i otrzymano dla niej nagrodę
     * @param state
     * @param action
     * @param reward 
     */
    public StateActionEntity(TreeSet<Feature> state, SelectedAction selectedAction) {
        this.state = new State(state);
        this.actions = new LinkedHashSet<Action>();
        this.actions.add(new Action(selectedAction));
    }
    
    public StateActionEntity(TreeSet<Feature> state){
        this.state = new State(state);
        this.actions = new LinkedHashSet<Action>();
    }

    
    /**
     * Konstruktor dla stanu, dla którego została dokonana ocena zbioru atrybutów
     * @param state
     * @param classificationEvaluation 
     */
    public StateActionEntity(TreeSet<Feature> state, Double classificationEvaluation) {
        this.state = new State(state);
        this.state.setclassificationEvaluation(classificationEvaluation);
        this.actions = new LinkedHashSet<Action>();

    }
    
    public boolean createAction(SelectedAction selectedAction){
        Action action = getActionForGivenSelectedAction(selectedAction);
        if(action != null)
            return false;
        return actions.add(new Action(selectedAction));
    }
    
    public Action createOrGetAction(SelectedAction selectedAction){
        Action action = getActionForGivenSelectedAction(selectedAction);
        if(action != null)
            return action;
        
        action = new Action(selectedAction);
        actions.add(action);
        return action;
    }

    public ArrayList<Action> getMaximizingActions(){
        ArrayList<Action> maximizingActions = new ArrayList<Action>();
        Double maxActionValue = -10000.0;
        for(Action action : actions){
            if(action.getValue() > maxActionValue){
                maxActionValue = action.getValue();
                maximizingActions.clear();
                maximizingActions.add(action);
            }else if(action.getValue().equals(maxActionValue)){
                maximizingActions.add(action);
            }
        }
        return maximizingActions;
    }
    
    public Double getMaximizingActionsValue(){
        Double maxActionValue = 0.0;
        for(Action action : actions){
            if(action.getValue() > maxActionValue){
                maxActionValue = action.getValue();
            }
        }
        return maxActionValue;
    }
    
    /**
     * Sprawdzamy czy przekazany stan wskazuje na ten sam zapisany w obiekcie
     * @param state
     * @return 
     */
    public boolean isState(State state) {
        return this.state.equals(state);
    }
    
    public boolean isState(TreeSet<Feature> state) {
        return this.state.isState(state);
    }

    /**
     * Dodanie podjętych w stanie akcji oraz nagrody, jakiej otrzymano za jej podjęcie
     * Dodatkowo, jeżeli stan nie ma ustalonej wartości oceny przez klasyfikator, to przypisujemy do niej przkazaną nagrodę  
     * 
     * @param action
     * @param reward nagroda za podjęcie akcji, jest to ocena klasyfikacji
     */
    public void updateActionValue(SelectedAction selectedAction) {
        //Tutaj chyba nie trzeba kopiować
        this.actions.add(new Action(selectedAction));
        
    }
    
    public Double getValueForGivenAction(SelectedAction selectedAction){
        Action action = getActionForGivenSelectedAction(selectedAction);
        return action == null ? null : action.getValue();
    }
    
    public Action getActionForGivenSelectedAction(SelectedAction selectedAction){
        for(Action action : actions){
            if(action.isAction(selectedAction))
                return action;
        }
        return null;
    }
    
    public void setActionValue(SelectedAction selectedAction, Double actionValue){
        Action action = getActionForGivenSelectedAction(selectedAction);
        action.setValue(actionValue);
    }
    
    public void setClassificationEvaluation(Double classificationEvaluation) {
        this.state.setclassificationEvaluation(classificationEvaluation);
        
    }
    
    public Double getClassificationEvaluation(){
        return state.getClassificationEvaluation();
    }
    
    public boolean isEvaluatedByClassifier(){
        return state.isEvaluatedByClassifier();
    }
    
    public Integer getFeaturesNumber(){
        return state.getFeaturesNumber();
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Obiekty uznajemy jako równe, jeżeli równe są ich pola 'TreeSet<Integer> state'
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StateActionEntity other = (StateActionEntity) obj;
        if (!state.equals(other.getState())) {
            return false;
        }
        return true;
    }


    public State getState(){
        return state;
    }
    
    public LinkedHashSet<Action> getActions() {
        return actions;
    }

    public void setActions(LinkedHashSet<Action> actions) {
        this.actions = actions;
    } 

    @Override
    public String toString() {
        StringBuilder description = new StringBuilder(state.toString());       
                
        description.append("Wartości akcji");
        description.append("\n");
        for(Action action : actions){
            description.append(action.toString());
            description.append("\n");
        }

        return description.toString();
    }

    @Override
    public int compareTo(StateActionEntity o) {
        if(o.getState() == null || o.getState().getValue() == null)
            return 1;
        if(this.getState() == null || this.getState().getValue() == null)
            return -1;
        
        Double difference = this.getState().getValue() - o.getState().getValue();
        
        if(difference < 0)
            return -1;
        if(difference > 0)
            return 1;
        
        return -1;
    }
}
