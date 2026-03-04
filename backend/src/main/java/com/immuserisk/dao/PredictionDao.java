package com.immuserisk.dao;

import com.immuserisk.entity.Prediction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PredictionDao {
    /**
     * 新增预测记录
     * @param prediction 预测实体
     * @return 影响行数
     */
    int insertPrediction(Prediction prediction);

    /**
     * 根据ID查询预测记录
     * @param predictionID 预测ID
     * @return 预测实体
     */
    Prediction selectPredictionById(Integer predictionID);

    /**
     * 根据用户ID查询预测记录
     * @param userID 用户ID
     * @return 预测记录列表
     */
    List<Prediction> selectPredictionsByUserId(Integer userID);

    /**
     * 更新预测记录
     * @param prediction 预测实体
     * @return 影响行数
     */
    int updatePrediction(Prediction prediction);

    /**
     * 根据ID删除预测记录
     * @param predictionID 预测ID
     * @return 影响行数
     */
    int deletePredictionById(Integer predictionID);



    /**
     * 查询用户预测记录列表
     */
    List<Prediction> getUserPredictionList(Map<String, Object> params);

    /**
     * 查询用户预测记录总数
     */
    int getUserPredictionCount(Map<String, Object> params);
}
