/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.states;

import java.util.Comparator;
import java.util.Objects;
import weka.core.Attribute;

/**
 *
 * @author Przemek
 */


public class Feature implements Comparable<Feature>, Comparator<Feature> {
    private final Attribute attribute;
       
    public Feature(Attribute attribute){
        this.attribute = attribute;
    }
    
    public int getIndex(){
        return this.attribute.index();
    }
    
    @Override
    public int compareTo(Feature f) {
        return this.getAttribute().index() - f.getAttribute().index();
    }

    @Override
    public int hashCode() {       
        return attribute.hashCode();
    }

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
        final Feature other = (Feature) obj;
        if (!Objects.equals(this.attribute, other.attribute)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compare(Feature o1, Feature o2) {
        return o1.getAttribute().index() - o2.getAttribute().index();
    }
    
    public String toString(){
        return String.valueOf(this.attribute.index());
    }

    public Attribute getAttribute() {
        return attribute;
    }    
 
}
