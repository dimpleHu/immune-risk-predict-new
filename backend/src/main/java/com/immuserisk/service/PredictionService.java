package com.immuserisk.service;

import com.immuserisk.entity.Prediction;

import java.util.List;
import java.util.Map;

public interface PredictionService {
    /**
     * 新增预测记录
     * @param prediction 预测实体
     * @return 是否成功
     */
    boolean addPrediction(Prediction prediction);

    /**
     * 根据ID查询预测记录
     * @param predictionID 预测ID
     * @return 预测实体
     */
    Prediction getPredictionById(Integer predictionID);

    /**
     * 根据用户ID查询预测记录
     * @param userID 用户ID
     * @return 预测记录列表
     */
    List<Prediction> getPredictionsByUserId(Integer userID);

    /**
     * 更新预测记录
     * @param prediction 预测实体
     * @return 是否成功
     */
    boolean updatePrediction(Prediction prediction);

    /**
     * 根据ID删除预测记录
     * @param predictionID 预测ID
     * @return 是否成功
     */
    boolean removePredictionById(Integer predictionID);




    /**
     * 获取用户预测记录列表
     */
    List<Prediction> getUserPredictionList(Map<String, Object> params);

    /**
     * 获取用户预测记录总数
     */
    int getUserPredictionCount(Map<String, Object> params);
}

