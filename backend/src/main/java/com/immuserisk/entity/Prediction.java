package com.immuserisk.entity;

import java.util.Date;

public class Prediction {
    private Integer predictionID;
    private Integer userID;
    private Integer age;
    private Integer sex;
    private String symptomsID;
    private String symptoms;
    private String diseasesID;
    private Date predictionTime;
    private String diseaseProbabilities;

    public Integer getPredictionID() {
        return predictionID;
    }
    public void setPredictionID(Integer predictionID) {
        this.predictionID = predictionID;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {this.userID = userID;}
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getSymptomsID() {
        return symptomsID;
    }
    public void setSymptomsID(String symptomsID) {
        this.symptomsID = symptomsID;
    }
    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    public String getDiseasesID() {
        return diseasesID;
    }
    public void setDiseasesID(String diseasesID) {
        this.diseasesID = diseasesID;
    }
    public Date getPredictionTime() {
        return predictionTime;
    }
    public void setPredictionTime(Date predictionTime) {
        this.predictionTime = predictionTime;
    }

    public String getDiseaseProbabilities() {return diseaseProbabilities;}
    public void setDiseaseProbabilities(String diseaseProbabilities) {this.diseaseProbabilities = diseaseProbabilities;}
    @Override
    public String toString() {
        return "Prediction{" +
                "predictionID=" + predictionID +
                ", userID=" + userID +
                ", age=" + age +
                ", sex=" + sex +
                ", symptomsID='" + symptomsID + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", diseasesID='" + diseasesID + '\'' +
                ", predictionTime=" + predictionTime + '\'' +
                ", diseaseProbabilities=" + diseaseProbabilities +
                '}';
    }
}