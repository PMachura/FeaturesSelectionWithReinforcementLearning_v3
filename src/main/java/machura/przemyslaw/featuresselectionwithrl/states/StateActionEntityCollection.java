/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.states;

import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;
import machura.przemyslaw.featuresselectionwithrl.actions.SelectedAction;
import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 *
 * @author Przemek
 */
public class StateActionEntityCollection {

    LinkedHashSet<StateActionEntity> entities;

    public StateActionEntityCollection() {
        entities = new LinkedHashSet<StateActionEntity>();
    }

    /**
     * Tworzy encję o podanym stanie, jeżeli ta jeszcze nie znajduje się w
     * zbiorze
     *
     * @param state
     * @return
     */
    public StateActionEntity createOrGetEntity(TreeSet<Feature> state) {
        StateActionEntity sae = getEntityForGivenState(state);
        if (sae != null) {
            return sae;
        }

        sae = new StateActionEntity(state);
        entities.add(sae);
        return sae;
    }
    
    

    public StateActionEntity createOrGetEntityAndAddAction(TreeSet<Feature> state, SelectedAction selectedAction) {
        StateActionEntity sae = getEntityForGivenState(state);
        if (sae != null) {
            sae.createAction(selectedAction);
            return sae;
        }

        sae = new StateActionEntity(state, selectedAction);
        entities.add(sae);
        return sae;

    }

    /**
     * Zwraca zbiór wartość akcja dla wskazanego stanu
     *
     * @param state
     * @return
     */
    public LinkedHashSet<Action> getActionsForGivenState(TreeSet<Feature> state) {
        StateActionEntity save = getEntityForGivenState(state);
        return save != null ? save.getActions() : null;
    }

    public ArrayList<Action> getMaximizingActionsForGivenState(TreeSet<Feature> state) {
        StateActionEntity save = getEntityForGivenState(state);
        return save != null ? save.getMaximizingActions() : null;
    }

    public ArrayList<Action> getMaximizingActionsForGivenState(State state) {
        StateActionEntity save = getEntityForGivenState(state);
        return save != null ? save.getMaximizingActions() : null;
    }

    /**
     * Zwraca obiekt StateActionsValuesEntity dla wskazanego stanu
     *
     * @param state
     * @return
     */
    private StateActionEntity getEntityForGivenState(TreeSet<Feature> state) {
        for (StateActionEntity save : entities) {
            if (save.isState(state)) {
                return save;
            }
        }
        return null;
    }

    private StateActionEntity getEntityForGivenState(State state) {
        for (StateActionEntity save : entities) {
            if (save.isState(state)) {
                return save;
            }
        }
        return null;
    }

    public ArrayList<StateActionEntity> getEntityWithMaxClassificationEvaluation() {
        ArrayList<StateActionEntity> selected = new ArrayList<StateActionEntity>();
        Double max = -1000000000.0;
        for (StateActionEntity save : entities) {
            if (save.isEvaluatedByClassifier()) {
                if (save.getClassificationEvaluation() > max) {
                    max = save.getClassificationEvaluation();
                    ArrayList<StateActionEntity> toRemove = new ArrayList<StateActionEntity>();
                    for (StateActionEntity bestEntity : selected) {
                        if (abs(bestEntity.getClassificationEvaluation() - max) > 0.01) {
                            toRemove.add(bestEntity);
                        }
                    }
                    selected.removeAll(toRemove);
                    selected.add(save);
                } else if (abs(save.getClassificationEvaluation() - max) < 0.01) {
                    selected.add(save);
                }
            }
        }
        selected.sort((StateActionEntity o1, StateActionEntity o2) -> {
            Double diff = o1.getClassificationEvaluation() - o2.getClassificationEvaluation();
            if (diff.equals(0.0)) {
                return 0;
            }
            if (diff > 0) {
                return 1;
            }
            return -1;

        });
        return selected;
    }

    public Double getClassificationEvaluationForGivenState(TreeSet<Feature> state) {
        StateActionEntity save = getEntityForGivenState(state);
        if (save != null && save.isEvaluatedByClassifier()) {
            return save.getClassificationEvaluation();
        }
        return null;
    }

    public void updateClassificationEvaluationForEntity(TreeSet<Feature> state, Double classificationEvaluation) {

        StateActionEntity stateActionsValuesEntity;
        stateActionsValuesEntity = getEntityForGivenState(state);

        if (stateActionsValuesEntity != null) {
            stateActionsValuesEntity.setClassificationEvaluation(classificationEvaluation);
        } else {
            stateActionsValuesEntity = new StateActionEntity(state, classificationEvaluation);
            entities.add(stateActionsValuesEntity);
        }
    }

    public LinkedHashSet<StateActionEntity> getEntities() {
        return entities;
    }

    public String toString() {
        StringBuilder description = new StringBuilder();
        for (StateActionEntity save : entities) {
            description.append(save.toString());
            description.append("\n");
        }

        return description.toString();
    }

}
