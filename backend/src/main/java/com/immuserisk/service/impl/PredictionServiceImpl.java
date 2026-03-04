package com.immuserisk.service.impl;

import com.immuserisk.dao.PredictionDao;
import com.immuserisk.entity.Prediction;
import com.immuserisk.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PredictionServiceImpl implements PredictionService {

    @Autowired
    private PredictionDao predictionDao;

    @Override
    public boolean addPrediction(Prediction prediction) {
        return predictionDao.insertPrediction(prediction) > 0;
    }

    @Override
    public Prediction getPredictionById(Integer predictionID) {
        return predictionDao.selectPredictionById(predictionID);
    }

    @Override
    public List<Prediction> getPredictionsByUserId(Integer userID) {
        return predictionDao.selectPredictionsByUserId(userID);
    }

    @Override
    public boolean updatePrediction(Prediction prediction) {
        return predictionDao.updatePrediction(prediction) > 0;
    }

    @Override
    public boolean removePredictionById(Integer predictionID) {
        return predictionDao.deletePredictionById(predictionID) > 0;
    }


    @Override
    public List<Prediction> getUserPredictionList(Map<String, Object> params) {
        return predictionDao.getUserPredictionList(params);
    }

    @Override
    public int getUserPredictionCount(Map<String, Object> params) {
        return predictionDao.getUserPredictionCount(params);
    }

}
