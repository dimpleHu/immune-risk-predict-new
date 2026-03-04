package com.immuserisk.entity;

public class Disease {
    private Integer diseaseID;
    private String diseaseName;
    private String diseaseType;
    private Integer probability;
    private String description;
    private String suggestions;
    private String symptoms;
    private String riskfactors;
    private String diseaseImage;
    private String otherImages;

    // Getters and Setters
    public Integer getDiseaseID() {
        return diseaseID;
    }
    public void setDiseaseID(Integer diseaseID) {
        this.diseaseID = diseaseID;
    }

    public String getDiseaseName() {
        return diseaseName;
    }
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseType() {
        return diseaseType;
    }
    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public Integer getProbability() {
        return probability;
    }
    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestions() {
        return suggestions;
    }
    public void setSuggestions(String suggestions) {
        this.suggestions= suggestions;
    }

    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms= symptoms;
    }

    public String getRiskfactors() {
        return riskfactors;
    }
    public void setRiskfactors(String riskfactors) {
        this.riskfactors= riskfactors;
    }

    public String getDiseaseImage() {
        return diseaseImage;
    }
    public void setDiseaseImage(String diseaseImage) {
        this.diseaseImage= diseaseImage;
    }

    public String getOtherImages() {
        return otherImages;
    }
    public void setOtherImages(String otherImages) {
        this.otherImages= otherImages;
    }


    @Override
    public String toString() {
        return "Disease{" +
                "diseaseID=" + diseaseID +
                ", diseaseName='" + diseaseName + '\'' +
                ", diseaseType='" + diseaseType + '\'' +
                ", probability='" + probability + '\'' +
                ", description='" + description + '\'' +
                ", suggestions='" + suggestions + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", riskfactors='" + riskfactors + '\'' +
                ", diseaseImage='" + diseaseImage + '\'' +
                '}';
    }
}
