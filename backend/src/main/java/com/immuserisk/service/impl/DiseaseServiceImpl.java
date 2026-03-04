package com.immuserisk.service.impl;

import com.immuserisk.dao.DiseaseDao;
import com.immuserisk.entity.Disease;
import com.immuserisk.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Service
//public class DiseaseServiceImpl implements DiseaseService {
//
//    @Autowired
//    private DiseaseDao diseaseDao;
//
//    // 从配置文件或直接定义绝对路径的前缀（根据你的实际路径修改）
//    // 建议在application.properties中配置：image.base-path=http://localhost:8085/sky-feeling/src/main/resources/static/
//    @Value("${image.base-path}")
//    private String imageBasePath;
//
//    @Override
//    public List<Disease> getAllDiseases() {
//        return diseaseDao.getAllDiseases();
//    }
//
//    // 新增：根据ID查询单个疾病
//    @Override
//    public Disease getDiseaseById(Integer diseaseID) {
//        return diseaseDao.getDiseaseById(diseaseID);
//    }
//
//    // 新增：根据多个ID查询疾病列表
//    @Override
//    public List<Disease> getDiseasesByIds(List<Integer> ids) {
//        return diseaseDao.getDiseasesByIds(ids);
//    }
//
//    // 新增：获取diseaseID能被四整除的数据
//    @Override
//    public List<Disease> getDiseasesWithIdDivisibleByFour() {
//        return diseaseDao.getDiseasesWithIdDivisibleByFour();
//    }
//}
@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseDao diseaseDao;

    // 从配置文件或直接定义绝对路径的前缀（根据你的实际路径修改）
    // 建议在application.properties中配置：image.base-path=http://localhost:8085/sky-feeling/src/main/resources/static/
    @Value("${image.base-path}")
    private String imageBasePath;

    @Override
    public List<Disease> getAllDiseases() {
        List<Disease> diseases = diseaseDao.getAllDiseases();
        // 处理图片路径：将绝对路径转为相对路径
        return processDiseaseImages(diseases);
    }

    @Override
    public Disease getDiseaseById(Integer diseaseID) {
        Disease disease = diseaseDao.getDiseaseById(diseaseID);
        // 处理单条数据的图片路径
        if (disease != null) {
            processSingleDiseaseImage(disease);
        }
        return disease;
    }

    @Override
    public List<Disease> getDiseasesByIds(List<Integer> ids) {
        List<Disease> diseases = diseaseDao.getDiseasesByIds(ids);
        return processDiseaseImages(diseases);
    }

    // 新增：处理diseaseID能被4整除的疾病数据（含图片路径处理）
    @Override
    public List<Disease> getDiseasesWithIdDivisibleByFour() {
        List<Disease> diseases = diseaseDao.getDiseasesWithIdDivisibleByFour();
        return processDiseaseImages(diseases);
    }

    // 批量处理疾病图片路径
    private List<Disease> processDiseaseImages(List<Disease> diseases) {
        return diseases.stream().map(this::processSingleDiseaseImage).collect(Collectors.toList());
    }

    // 处理单条疾病的图片路径（核心转换逻辑）
    private Disease processSingleDiseaseImage(Disease disease) {
        String fullImagePath = disease.getDiseaseImage();
        if (fullImagePath != null && fullImagePath.startsWith(imageBasePath)) {
            // 去掉前缀，保留相对路径（如 "images/disease1.jpg"）
            String relativePath = fullImagePath.replace(imageBasePath, "");
            disease.setDiseaseImage(relativePath);
        }
        return disease;
    }
}
