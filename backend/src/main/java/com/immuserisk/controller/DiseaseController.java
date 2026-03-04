package com.immuserisk.controller;

import com.immuserisk.common.R;
import com.immuserisk.entity.Disease;
import com.immuserisk.service.DiseaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disease")
public class DiseaseController {
    private static final Logger logger = LoggerFactory.getLogger(SymptomsController.class);

    @Autowired
    private DiseaseService diseaseService;

    @GetMapping("/all")
    public R<List<Disease>> getAllDiseases() {
        try{
            List<Disease> allDisease= diseaseService.getAllDiseases();
            logger.info("查询到 {} 条免疫病", allDisease.size());
            return R.ok(allDisease);
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }

    // 新增：根据单个ID查询疾病
    @GetMapping("/{id}")
    public R<Disease> getDiseaseById(@PathVariable Integer id) {
        try{
            logger.info("查询ID为 {} 的疾病数据", id);
            Disease disease = diseaseService.getDiseaseById(id);
            if (disease == null) {
                return R.error("未找到对应疾病");
            }
            return R.ok(disease);
        }catch (Exception e){
            logger.error("查询疾病失败", e);
            return R.error(e.getMessage());
        }
    }

    // 新增：根据多个ID查询疾病（支持逗号分隔的ID字符串）
    @GetMapping("/batch")
    public R<List<Disease>> getDiseasesByIds(@RequestParam String ids) {
        try{
            logger.info("批量查询疾病ID：{}", ids);
            // 将逗号分隔的ID字符串转换为整数列表
            List<Integer> idList = Arrays.stream(ids.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            List<Disease> diseases = diseaseService.getDiseasesByIds(idList);
            return R.ok(diseases);
        }catch (NumberFormatException e) {
            return R.error("ID格式不正确");
        }catch (Exception e){
            logger.error("批量查询疾病失败", e);
            return R.error(e.getMessage());
        }
    }

    // 新增：获取diseaseID能被四整除的数据
    @GetMapping("/divisible-by-four")
    public R<List<Disease>> getDiseasesWithIdDivisibleByFour() {
        try{
            logger.info("查询diseaseID能被四整除的疾病数据");
            List<Disease> diseases = diseaseService.getDiseasesWithIdDivisibleByFour();
            logger.info("查询到 {} 条diseaseID能被四整除的疾病数据", diseases.size());
            return R.ok(diseases);
        }catch (Exception e){
            logger.error("查询diseaseID能被四整除的疾病数据失败", e);
            return R.error(e.getMessage());
        }
    }
}