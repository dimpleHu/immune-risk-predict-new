package com.immuserisk.controller;

import com.immuserisk.entity.Symptoms;
import com.immuserisk.service.SymptomsService;
import com.immuserisk.common.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 症状控制器
 */
@RestController
@RequestMapping("/symptoms")
public class SymptomsController {
    private static final Logger logger = LoggerFactory.getLogger(SymptomsController.class);

    @Autowired
    private SymptomsService symptomsService;

    /**
     * 查询所有症状
     */
    @GetMapping("/all")
    public R<List<Symptoms>> findAll() {
        try {
            logger.info("查询所有症状");
            List<Symptoms> symptomsList = symptomsService.findAll();
            logger.info("查询到 {} 条症状记录", symptomsList.size());
            return R.ok(symptomsList);
        } catch (Exception e) {
            logger.error("查询所有症状失败", e);
            return R.error("查询所有症状失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询症状
     */
    @GetMapping("/{id}")
    public R<Symptoms> findById(@PathVariable("id") Integer symptomId) {
        try {
            logger.info("根据ID查询症状，ID为：{}", symptomId);
            Symptoms symptoms = symptomsService.findById(symptomId);
            if (symptoms != null) {
                logger.info("根据ID {} 查询到症状：{}", symptomId, symptoms.getSymptomName());
                return R.ok(symptoms);
            } else {
                logger.warn("根据ID {} 未查询到症状记录", symptomId);
                return R.error("根据ID " + symptomId + " 未查询到症状记录");
            }
        } catch (Exception e) {
            logger.error("根据ID查询症状失败，ID为：{}", symptomId, e);
            return R.error("根据ID查询症状失败：" + e.getMessage());
        }
    }

    /**
     * 根据名称模糊查询症状
     */
    @GetMapping("/search")
    public R<List<Symptoms>> findByName(@RequestParam("name") String symptomName) {
        try {
            logger.info("根据名称模糊查询症状，名称为：{}", symptomName);
            List<Symptoms> symptomsList = symptomsService.findByName(symptomName);
            logger.info("根据名称 {} 查询到 {} 条症状记录", symptomName, symptomsList.size());
            return R.ok(symptomsList);
        } catch (Exception e) {
            logger.error("根据名称模糊查询症状失败", e);
            return R.error("根据名称模糊查询症状失败：" + e.getMessage());
        }
    }

//    /**
//     * 添加症状
//     */
//    @PostMapping("/add")
//    public R<String> add(@RequestBody Symptoms symptoms) {
//        try {
//            logger.info("开始添加症状，接收到的参数：{}", symptoms);
//
//            // 参数验证
//            if (symptoms.getSymptomName() == null || symptoms.getSymptomName().trim().isEmpty()) {
//                logger.error("添加症状失败：症状名称不能为空");
//                return R.error("症状名称不能为空");
//            }
//
//            logger.info("参数验证通过，开始调用服务层添加症状");
//            try {
//                int resultTotal = symptomsService.add(symptoms);
//                if (resultTotal > 0) {
//                    logger.info("添加症状成功，ID：{}", symptoms.getSymptomId());
//                    return R.ok("添加成功");
//                } else {
//                    logger.error("添加症状失败：数据库操作未成功");
//                    return R.error("添加失败，请稍后重试");
//                }
//            } catch (Exception e) {
//                logger.error("调用服务层添加症状失败", e);
//                throw e;
//            }
//        } catch (RuntimeException e) {
//            logger.error("添加症状失败（运行时异常）：{}", e.getMessage(), e);
//            return R.error(e.getMessage());
//        } catch (Exception e) {
//            logger.error("添加症状发生未知异常：{}", e.getMessage(), e);
//            return R.error("系统异常，请联系管理员");
//        }
//    }
//
//    /**
//     * 修改症状
//     */
//    @PutMapping("/update")
//    public R<String> update(@RequestBody Symptoms symptoms) {
//        try {
//            logger.info("更新症状，参数：{}", symptoms);
//            int resultTotal = symptomsService.update(symptoms);
//            if (resultTotal > 0) {
//                logger.info("更新症状成功，ID：{}", symptoms.getSymptomId());
//                return R.ok("修改成功");
//            }
//            return R.error("修改失败");
//        } catch (Exception e) {
//            logger.error("更新症状失败", e);
//            return R.error("更新症状失败：" + e.getMessage());
//        }
//    }
//
//    /**
//     * 删除症状
//     */
//    @DeleteMapping("/delete/{id}")
//    public R<String> delete(@PathVariable("id") Integer id) {
//        try {
//            logger.info("删除症状，ID：{}", id);
//            int resultTotal = symptomsService.delete(id);
//            if (resultTotal > 0) {
//                logger.info("删除症状成功，ID：{}", id);
//                return R.ok("删除成功");
//            }
//            return R.error("删除失败");
//        } catch (Exception e) {
//            logger.error("删除症状失败，ID：{}", id, e);
//            return R.error("删除症状失败：" + e.getMessage());
//        }
//    }
}