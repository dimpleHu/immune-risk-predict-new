package com.immuserisk.service;
import com.immuserisk.entity.Disease;

import java.util.List;

public interface DiseaseService {
    List<Disease> getAllDiseases();

    // 新增：根据ID查询单个疾病
    Disease getDiseaseById(Integer diseaseID);

    // 新增：根据多个ID查询疾病列表
    List<Disease> getDiseasesByIds(List<Integer> ids);

    // 新增：获取diseaseID能被四整除的数据
    List<Disease> getDiseasesWithIdDivisibleByFour();
}
