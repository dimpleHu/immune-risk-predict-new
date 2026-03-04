package com.immuserisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.immuserisk.common.R;
import com.immuserisk.entity.Prediction;
import com.immuserisk.service.PredictionService;

@RestController
@RequestMapping("/predictions")
public class PredictionController {
    // 初始化日志对象，指定当前类作为日志来源
    private static final Logger logger = LoggerFactory.getLogger(PredictionController.class);

    @Autowired
    private PredictionService predictionService;

    /**
     * 新增预测记录
     */
    @PostMapping("/add")
    public R<String> addPrediction(@RequestBody Prediction prediction) {
        try {
            // 记录接收到的请求参数
            logger.info("开始处理新增预测记录请求，接收参数：{}", prediction);

            // 参数验证
            if (prediction.getUserID() == null) {
                logger.error("新增预测记录失败：用户ID不能为空");
                return R.error("用户ID不能为空");
            }
            if (prediction.getAge() == null || prediction.getAge() <= 0 || prediction.getAge() > 150) {
                logger.error("新增预测记录失败：年龄不合法，当前值：{}", prediction.getAge());
                return R.error("年龄必须为1-150之间的整数");
            }
            if (prediction.getSex() == null || (prediction.getSex() != 0 && prediction.getSex() != 1)) {
                logger.error("新增预测记录失败：性别不合法，当前值：{}", prediction.getSex());
                return R.error("性别必须为0（女）或1（男）");
            }
            if (prediction.getSymptomsID() == null || prediction.getSymptomsID().trim().isEmpty()) {
                logger.error("新增预测记录失败：症状ID不能为空");
                return R.error("请至少选择一个症状");
            }

            logger.info("参数验证通过，开始调用服务层执行新增操作");
            try {
                // 调用服务层方法
                boolean success = predictionService.addPrediction(prediction);

                if (success) {
                    logger.info("新增预测记录成功，记录ID：{}", prediction.getPredictionID());
                    return R.success("新增成功");
                } else {
                    logger.error("新增预测记录失败：服务层返回操作失败");
                    return R.error("新增失败，请稍后重试");
                }
            } catch (Exception e) {
                logger.error("调用服务层新增预测记录时发生异常", e);
                throw e; // 抛出异常由外层外层try-catch处理
            }
        } catch (IllegalArgumentException e) {
            logger.error("新增预测记录失败（参数异常）：{}", e.getMessage());
            return R.error(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("新增预测记录失败（运行时异常）：{}", e.getMessage(), e);
            return R.error("操作失败：" + e.getMessage());
        } catch (Exception e) {
            logger.error("新增预测记录发生未知未知异常", e);
            return R.error("系统异常，请请联系管理员");
        }
    }

//    /**
//     * 获取当前用户的预测记录列表
//     */
//    @GetMapping("/userlist")
//    public R<Map<String, Object>> getUserPredictionList(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer limit,
//            @RequestParam(required = false) String startDate,
//            @RequestParam(required = false) String endDate,
//            @RequestParam Integer userId) {
//        try {
//            // 构造查询参数
//            Map<String, Object> params = new HashMap<>();
//            params.put("offset", (page - 1) * limit);
//            params.put("limit", limit);
//            params.put("userId", userId);
//            params.put("startDate", startDate);
//            params.put("endDate", endDate);
//
//            // 查询数据
//            List<Prediction> items = predictionService.getUserPredictionList(params);
//            int total = predictionService.getUserPredictionCount(params);
//
//            // 构造返回结果
//            Map<String, Object> result = new HashMap<>();
//            result.put("items", items);
//            result.put("total", total);
//
//            return R.success(result);
//        } catch (Exception e) {
//            return R.error("获取预测记录失败：" + e.getMessage());
//        }
//    }

    /**
     * 获取当前用户的预测记录列表
     */
    @GetMapping("/userlist")
    public R<Map<String, Object>> getUserPredictionList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam Integer userId) {
        // 记录方法调用开始及接收的参数
        logger.info("开始处理用户预测记录列表请求 - page: {}, limit: {}, startDate: {}, endDate: {}, userId: {}",
                page, limit, startDate, endDate, userId);

        try {
            // 参数合法性校验
            if (page < 1) {
                logger.error("分页参数不合法 - page: {}（必须大于等于1）", page);
                return R.error("分页参数不合法，页码必须大于等于1");
            }
            if (limit < 1 || limit > 100) {
                logger.error("分页参数不合法 - limit: {}（必须在1-100之间）", limit);
                return R.error("分页参数不合法，每页条数必须在1-100之间");
            }
            if (userId == null || userId <= 0) {
                logger.error("用户ID不合法 - userId: {}（必须大于0）", userId);
                return R.error("用户ID不合法");
            }

            // 构造查询参数
            Map<String, Object> params = new HashMap<>();
            int offset = (page - 1) * limit;
            params.put("offset", offset);
            params.put("limit", limit);
            params.put("userID", userId);
            params.put("startDate", startDate);
            params.put("endDate", endDate);

            logger.debug("构造查询参数: {}", params);

            // 查询数据
            logger.info("开始查询用户用户[{}]的预测记录，分页参数: offset {}, limit: {}, offset: {}",
                    userId, page, limit, offset);

            List<Prediction> items = predictionService.getUserPredictionList(params);
            int total = predictionService.getUserPredictionCount(params);

            logger.info("查询询用户[{}]的预测记录完成 - 总条数: {}, 当前页记录数: {}",
                    userId, total, items.size());

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("items", items);
            result.put("total", total);

            logger.debug("返回结果: {}", result);
            return R.success(result);
        } catch (IllegalArgumentException e) {
            // 处理参数参数异常（如服务层抛出的参数校验异常）
            logger.error("获取预测记录失败（参数异常）- userId: {}，错误信息: {}", userId, e.getMessage(), e);
            return R.error("获取预测记录失败：" + e.getMessage());
//        } catch (ServiceException e) {
//            // 自定义务业务异常（自定义定义的服务层异常）
//            logger.error("获取取预测记录失败（业务异常）- userId: {}，错误信息: {}", userId, e.getMessage(), e);
//            return R.error("获取测预测记录失败：" + e.getMessage());
        } catch (Exception e) {
            // 捕获所有其他异常，记录详细堆栈
            logger.error("获取取预测记录失败（系统异常）- userId: {}", userId, e);
            return R.error("系统异常，请请联系管理员");
        }
    }


    /**

     根据 ID 查询预测记录
     */
    @GetMapping ("/detail/{id}")
    public R<Prediction> getPredictionById (@PathVariable ("id") Integer predictionID) {
        Prediction prediction = predictionService.getPredictionById (predictionID);
        return prediction != null ? R.success (prediction) : R.error ("记录不存在");
    }

    /**

     根据用户 ID 查询预测记录
     */
    @GetMapping ("/user/{userId}")
    public R<List<Prediction>> getPredictionsByUserId(@PathVariable("userId") Integer userID) {
        List<Prediction> predictions = predictionService.getPredictionsByUserId(userID);
        return R.success(predictions);
    }

    /**

     更新预测记录
     */
    @PutMapping
    public R<String> updatePrediction (@RequestBody Prediction prediction) {
        boolean success = predictionService.updatePrediction (prediction);
        return success ? R.success ("更新成功") : R.error ("更新失败");
    }

    /**

     根据 ID 删除预测记录
     */
    @DeleteMapping ("/{id}")
    public R<String> removePredictionById (@PathVariable ("id") Integer predictionID) {
        boolean success = predictionService.removePredictionById (predictionID);
        return success ? R.success ("删除成功") : R.error ("删除失败");
    }
}
