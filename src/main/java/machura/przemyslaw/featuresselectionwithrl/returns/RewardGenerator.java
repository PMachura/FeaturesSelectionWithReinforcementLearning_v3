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
public class RewardGenerator {
    private Double featureNumberImportanceCoefficient = 0.0; //Wspo
    private Double maxGeneratedValue = 1.0;
    private Double minGeneratedValue = 0.0;
    
    public Reward generateReward(Double classificationEvaluation,Integer maxSubsetFeatureNubmer, Integer featuresInSubset){
        if(featureNumberImportanceCoefficient.equals(0.0) || maxSubsetFeatureNubmer == null || featuresInSubset == null)
            return new Reward(classificationEvaluation);
        
        Double val = classificationEvaluation - ((double)featuresInSubset / (double)maxSubsetFeatureNubmer )* featureNumberImportanceCoefficient;
        val = val < minGeneratedValue ? minGeneratedValue : val;
        val = val > maxGeneratedValue ? maxGeneratedValue : val;
        return new Reward(val);
    }
    
    public Reward generateReward(Double classificationEvaluation){
        return new Reward(classificationEvaluation);
    }
    
    
    public Double setFeatureNumberImportanceCoefficientToObtainGivenMaxPunishment(Integer wholeFeaturesInInputDataNumber, Integer maxFeaturesSubsetSizeNumber, Double maxPunishment){
        this.featureNumberImportanceCoefficient = ((double) maxFeaturesSubsetSizeNumber * maxPunishment) /(double)wholeFeaturesInInputDataNumber;
        return featureNumberImportanceCoefficient;
    }

    public void setFeatureNumberImportanceCoefficient(Double featureNumberImportanceCoefficient) {
        this.featureNumberImportanceCoefficient = featureNumberImportanceCoefficient;
    }

    public Double getMaxGeneratedValue() {
        return maxGeneratedValue;
    }

    public void setMaxGeneratedValue(Double maxGeneratedValue) {
        this.maxGeneratedValue = maxGeneratedValue;
    }

    public Double getMinGeneratedValue() {
        return minGeneratedValue;
    }

    public void setMinGeneratedValue(Double minGeneratedValue) {
        this.minGeneratedValue = minGeneratedValue;
    }

    public Double getFeatureNumberImportanceCoefficient() {
        return featureNumberImportanceCoefficient;
    }
    
    
    
}
