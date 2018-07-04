/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machura.przemyslaw.featuresselectionwithrl.actions;

import java.util.BitSet;
import java.util.TreeSet;
import machura.przemyslaw.featuresselectionwithrl.states.Feature;
import machura.przemyslaw.featuresselectionwithrl.states.State;
import weka.core.Instances;

/**
 * Klasa zawierająca zbiory możliwych akcji- czyli w praktyce atrybutów, jakie
 * można dodać lub usunąć z aktualnie wybranego podzbioru Można zmienić nazwę na
 * ActionAllowed
 *
 * @author Przemek
 */
public class ActionsSpace {

    TreeSet<Feature> featuresAllowedToAdd;
    TreeSet<Feature> featuresAllowedToRemove;
    TreeSet<Feature> featuresSubset; 
    TreeSet<Feature> allFeatures; 
    Integer featuresAmount; 

    private ActionsSpace() {
        this.featuresAmount = 0;
        this.featuresAllowedToAdd = new TreeSet<Feature>();
        this.allFeatures = new TreeSet<Feature>();
        this.featuresAllowedToRemove = new TreeSet<Feature>();
        this.featuresSubset = new TreeSet<Feature>();
    }

    public static ActionsSpace createActionSpaceWithAllowedActionOnly(ActionsSpace actionSpace) {
        ActionsSpace copy = new ActionsSpace();
        copy.setFeaturesAllowedToAdd(new TreeSet<Feature>(actionSpace.getFeaturesAllowedToAdd()));
        copy.setFeaturesAllowedToRemove(new TreeSet<Feature>(actionSpace.getFeaturesAllowedToRemove()));
        copy.setFeaturesSubset(new TreeSet<Feature>(actionSpace.getFeaturesSubset()));
        return copy;
    }
    
    public ActionsSpace(Instances data) {
        this();
        this.featuresAmount = data.numAttributes() - 1;
        this.addAllFeaturesToSet(data, this.featuresAllowedToAdd);
        this.addAllFeaturesToSet(data, this.allFeatures);
    }

    public void updateByState(State state){
        featuresSubset = new TreeSet<Feature>(state.getState());
        featuresAllowedToRemove.clear();
        featuresAllowedToAdd = new TreeSet<Feature>(allFeatures);
        for(Feature feature : state.getState()){
            featuresAllowedToRemove.add(feature);
            featuresAllowedToAdd.remove(feature);
        }      
    }

    

    private void addAllFeaturesToSet(Instances data, TreeSet<Feature> set) {
        int featureAmount = data.numAttributes() - 1;

        for (int i = 0; i < featureAmount; i++) {
            set.add(new Feature(data.attribute(i)));
        }

    }

    public Double getFeatureAllowedToAddPercentage() {
        return ((double) featuresAllowedToAdd.size() / (double) (featuresAllowedToAdd.size() + featuresAllowedToRemove.size()));
    }

  
    public boolean checkConsistency() throws Exception {
        if (featuresAllowedToAdd.size() + featuresAllowedToRemove.size() != featuresAmount) {
            throw new Exception("Suma cech możliwych do dodania i możliwych do usnięcia nie równa się zbiorowi wszystkich cech");
        }
        return true;
    }

    private void updateAllowedFeaturesByAddedFeatures(TreeSet<Feature> addedFeatures) throws Exception {
        for (Feature feature : addedFeatures) {
            if (!featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Dodano atrybut, który nie znajdował się w zbiorze możliwych do dodania");
            }
            featuresAllowedToAdd.remove(feature);

            if (featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się w zbiorze możliwych do usunięcia");
            }
            featuresAllowedToRemove.add(feature);

        }
    }

    private void updateAllowedFeaturesByRemovedFeatures(TreeSet<Feature> removedFeatures) throws Exception {
        for (Feature feature : removedFeatures) {
            if (featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Usunięto atrybut, który znajdował się w zbiorze możliwych do dodania");
            }
            featuresAllowedToAdd.add(feature);

            if (!featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w zbiorze możliwych do usunięcia");
            }
            featuresAllowedToRemove.remove(feature);

        }
    }

    public void updateAllowedFeatures(SelectedAction selectedAction) throws Exception {
        updateAllowedFeaturesByAddedFeatures(selectedAction.getFeaturesToAdd());
        updateAllowedFeaturesByRemovedFeatures(selectedAction.getFeaturesToRemove());
    }

    private void updateAllowedFeaturesAndSubsetByAddedFeatures(TreeSet<Feature> addedFeatures) throws Exception {
        for (Feature feature : addedFeatures) {
            if (!featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Dodano atrybut, który nie znajdował się w zbiorze możliwych do dodania");
            }
            featuresAllowedToAdd.remove(feature);

            if (featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się w zbiorze możliwych do usunięcia");
            }
            featuresAllowedToRemove.add(feature);

            if (featuresSubset.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się już w aktualnym podzbiorze");
            }
            featuresSubset.add(feature);

        }
    }

    private void updateAllowedFeaturesAndSubsetByRemovedFeatures(TreeSet<Feature> removedFeatures) throws Exception {
        for (Feature feature : removedFeatures) {
            if (featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Usunięto atrybut, który znajdował się w zbiorze możliwych do dodania");
            }
            featuresAllowedToAdd.add(feature);

            if (!featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w zbiorze możliwych do usunięcia");
            }
            featuresAllowedToRemove.remove(feature);

            if (!featuresSubset.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w obecnym podzbiorze");
            }
            featuresSubset.remove(feature);

        }
    }

    public void updateAllowedFeaturesAndSubset(SelectedAction selectedAction) throws Exception {
        updateAllowedFeaturesAndSubsetByAddedFeatures(selectedAction.getFeaturesToAdd());
        updateAllowedFeaturesAndSubsetByRemovedFeatures(selectedAction.getFeaturesToRemove());
    }

    private void removeAllowedFeaturesAndSubsetByAddedFeatures(TreeSet<Feature> addedFeatures) throws Exception {
        for (Feature feature : addedFeatures) {
            if (!featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Dodano atrybut, który nie znajdował się w zbiorze możliwych do dodania");
            }
            featuresAllowedToAdd.remove(feature);

            if (featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się w zbiorze możliwych do usunięcia");
            }

            if (featuresSubset.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się już w aktualnym podzbiorze");
            }
            featuresSubset.add(feature);
        }
    }

    private void removeAllowedFeaturesAndSubsetByRemovedFeatures(TreeSet<Feature> removedFeatures) throws Exception {
        for (Feature feature : removedFeatures) {
            if (featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Usunięto atrybut, który znajdował się w zbiorze możliwych do dodania");
            }

            if (!featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w zbiorze możliwych do usunięcia");
            }
            featuresAllowedToRemove.remove(feature);

            if (!featuresSubset.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w obecnym podzbiorze");
            }
            featuresSubset.remove(feature);

        }
    }

    public void removeAllowedFeaturesAndSubsetBySelectedAction(SelectedAction selectedAction) throws Exception {
        removeAllowedFeaturesAndSubsetByAddedFeatures(selectedAction.getFeaturesToAdd());
        removeAllowedFeaturesAndSubsetByRemovedFeatures(selectedAction.getFeaturesToRemove());
    }
    
    
    private void removeAllowedFeaturesByAddedFeatures(TreeSet<Feature> addedFeatures) throws Exception {
        for (Feature feature : addedFeatures) {
            if (!featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Dodano atrybut, który nie znajdował się w zbiorze możliwych do dodania");
            }
            featuresAllowedToAdd.remove(feature);

            if (featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się w zbiorze możliwych do usunięcia");
            }

            if (featuresSubset.contains(feature)) {
                throw new Exception("Dodano atrybut, który znajdował się już w aktualnym podzbiorze");
            }
        }
    }

    private void removeAllowedFeaturesByRemovedFeatures(TreeSet<Feature> removedFeatures) throws Exception {
        for (Feature feature : removedFeatures) {
            if (featuresAllowedToAdd.contains(feature)) {
                throw new Exception("Usunięto atrybut, który znajdował się w zbiorze możliwych do dodania");
            }

            if (!featuresAllowedToRemove.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w zbiorze możliwych do usunięcia");
            }
            featuresAllowedToRemove.remove(feature);

            if (!featuresSubset.contains(feature)) {
                throw new Exception("Usunięto atrybut, który nie znajdował się w obecnym podzbiorze");
            }

        }
    }

    public void removeAllowedFeaturesBySelectedAction(SelectedAction selectedAction) throws Exception {
        removeAllowedFeaturesByAddedFeatures(selectedAction.getFeaturesToAdd());
        removeAllowedFeaturesByRemovedFeatures(selectedAction.getFeaturesToRemove());
    }
     

    public int getFeaturesInSubsetAmount() {
        return featuresSubset.size();
    }

    public TreeSet<Feature> getFeaturesAllowedToAdd() {
        return featuresAllowedToAdd;
    }

    public void setFeaturesAllowedToAdd(TreeSet<Feature> featuresAllowedToAdd) {
        this.featuresAllowedToAdd = featuresAllowedToAdd;
    }

    public TreeSet<Feature> getFeaturesAllowedToRemove() {
        return featuresAllowedToRemove;
    }

    public void setFeaturesAllowedToRemove(TreeSet<Feature> featuresAllowedToRemove) {
        this.featuresAllowedToRemove = featuresAllowedToRemove;
    }

    public Integer getFeaturesAmount() {
        return featuresAmount;
    }

    public void setFeaturesAmount(Integer featuresAmount) {
        this.featuresAmount = featuresAmount;
    }

    public TreeSet<Feature> getFeaturesSubset() {
        return featuresSubset;
    }

    public void setFeaturesSubset(TreeSet<Feature> featuresSubset) {
        this.featuresSubset = featuresSubset;
    }

    public TreeSet<Feature> getAllFeatures() {
        return allFeatures;
    }

    public void setAllFeatures(TreeSet<Feature> allFeatures) {
        this.allFeatures = allFeatures;
    }
    

    
    
}
