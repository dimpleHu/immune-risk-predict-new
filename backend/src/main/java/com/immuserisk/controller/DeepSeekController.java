package com.immuserisk.controller;

import com.immuserisk.service.DeepSeekService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/deepseekApi")
public class DeepSeekController {
    private static final Logger logger = Logger.getLogger(DeepSeekController.class.getName());

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    @Qualifier("shiroAsyncExecutor") // 引用上面配置的线程池
    private ThreadPoolTaskExecutor executor;

    // 流式对话接口（要求用户登录）
    @PostMapping(value = "/chatStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatStream(@RequestParam String message) {
        SseEmitter emitter = new SseEmitter(180000L);
        // 获取登录用户ID（未登录会直接被Shiro拦截，不会走到这步）
        Subject subject = SecurityUtils.getSubject();
        Long userId = Long.valueOf(subject.getPrincipal().toString());

        try {
            logger.info("开始处理用户[" + userId + "]的流式请求: " + message);
            // 提交到自定义线程池执行（确保Shiro上下文正确传递）
            executor.submit(() -> {
                deepSeekService.callDeepSeekStream(userId, message)
                        .subscribe(
                                content -> {
                                    try {
                                        emitter.send(SseEmitter.event().data(content));
                                    } catch (IOException e) {
                                        logger.severe("流式发送失败: " + e.getMessage());
                                        emitter.completeWithError(e);
                                    }
                                },
                                error -> {
                                    try {
                                        logger.severe("流式处理异常: " + error.getMessage());
                                        emitter.send(SseEmitter.event().data("错误: " + error.getMessage()));
                                    } catch (IOException e) {
                                        logger.severe("异常信息发送失败: " + e.getMessage());
                                    } finally {
                                        emitter.complete();
                                    }
                                },
                                () -> {
                                    logger.info("用户[" + userId + "]流式请求处理完成");
                                    emitter.complete();
                                }
                        );
            });
        } catch (Exception e) {
            try {
                logger.severe("请求初始化异常: " + e.getMessage());
                emitter.send(SseEmitter.event().data("系统错误: " + e.getMessage()));
            } catch (IOException ex) {
                logger.severe("错误信息发送失败: " + ex.getMessage());
            } finally {
                emitter.complete();
            }
        }
        return emitter;
    }

    // 普通对话接口（要求用户登录）
    @PostMapping("/chat")
    public String chat(@RequestParam String message) {
        Subject subject = SecurityUtils.getSubject();
        Long userId = Long.valueOf(subject.getPrincipal().toString());

        logger.info("开始处理用户[" + userId + "]的普通请求: " + message);
        try {
            return deepSeekService.callDeepSeek(userId, message);
        } catch (Exception e) {
            logger.severe("普通请求处理失败: " + e.getMessage());
            return "错误: " + e.getMessage();
        }
    }
}