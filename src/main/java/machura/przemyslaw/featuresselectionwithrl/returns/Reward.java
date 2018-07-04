/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.returns;

/**
 *
 * @author Przemek
 */
public class Reward {
    private Double value;
    private Integer timeStep;
    
    public Reward(){
        this.value = null;
        this.timeStep = null;
    }
    
    public Reward(Double value){
        this.value = value;
        this.timeStep = null;
    }
    
    public Reward(Double value, int timeStep){
        this(value);
        this.timeStep = timeStep;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Integer timeStep) {
        this.timeStep = timeStep;
    }
    
    
}
