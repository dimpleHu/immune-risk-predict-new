package com.immuserisk.dao;

import com.immuserisk.entity.Disease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiseaseDao {
    List<Disease> getAllDiseases();

    // 新增：根据疾病ID查询
    Disease getDiseaseById(@Param("diseaseID") Integer diseaseID);

    // 新增：根据多个疾病ID查询
    List<Disease> getDiseasesByIds(@Param("ids") List<Integer> ids);

    // 新增：获取diseaseID能被四整除的数据
    List<Disease> getDiseasesWithIdDivisibleByFour();
}
