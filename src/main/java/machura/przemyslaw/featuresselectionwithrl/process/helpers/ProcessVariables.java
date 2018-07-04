/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.process.helpers;

import machura.przemyslaw.featuresselectionwithrl.actions.Action;
import machura.przemyslaw.featuresselectionwithrl.returns.Reward;
import machura.przemyslaw.featuresselectionwithrl.states.State;
import machura.przemyslaw.featuresselectionwithrl.states.StateActionEntity;

/**
 *
 * @author Przemek
 */
public class ProcessVariables{
    private Reward reward = null;
    private State state = null;
    private Action action = null;
    private Integer timeStep = null;
    private StateActionEntity currentStateActionEntity = null;
    private StateActionEntity nextStateActionEntity = null;

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }


    public Integer getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Integer timeStep) {
        this.timeStep = timeStep;
    } 

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }   

    public StateActionEntity getCurrentStateActionEntity() {
        return currentStateActionEntity;
    }

    public void setCurrentStateActionEntity(StateActionEntity currentStateActionEntity) {
        this.currentStateActionEntity = currentStateActionEntity;
    }

    public StateActionEntity getNextStateActionEntity() {
        return nextStateActionEntity;
    }

    public void setNextStateActionEntity(StateActionEntity nextStateActionEntity) {
        this.nextStateActionEntity = nextStateActionEntity;
    }

    
    
    
}