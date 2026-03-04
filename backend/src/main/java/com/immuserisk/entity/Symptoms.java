package com.immuserisk.entity;

/**
 * 症状实体类
 */
public class Symptoms {
    private Integer symptomId;
    private String symptomName;
    private Integer affectedId;
    private String disease;

    // Getters and Setters

    public Integer getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Integer symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public Integer getAffectedId() {
        return affectedId;
    }

    public void setAffectedId(Integer affectedId) {
        this.affectedId = affectedId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "symptomId=" + symptomId +
                ", symptomName='" + symptomName + '\'' +
                ", affectedId=" + affectedId +
                ", disease='" + disease + '\'' +
                '}';
    }
}
