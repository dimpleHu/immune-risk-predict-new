package com.immuserisk.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAsync
public class AsyncConfig {

    // 关键修改：返回值改为 ThreadPoolTaskExecutor，与 Controller 注入类型一致
    @Bean(name = "shiroAsyncExecutor")
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("ShiroAsync-");
        executor.setTaskDecorator(runnable -> {
            Subject subject = SecurityUtils.getSubject();
            SecurityManager securityManager = SecurityUtils.getSecurityManager();
            Map<Object, Object> contextMap = new HashMap<>(ThreadContext.getResources());

            return () -> {
                try {
                    ThreadContext.setResources(contextMap);
                    SecurityUtils.setSecurityManager(securityManager);
                    runnable.run();
                } finally {
                    ThreadContext.remove();
                    SecurityUtils.setSecurityManager(null);
                }
            };
        });
        executor.initialize(); // 必须初始化
        return executor;
    }
}