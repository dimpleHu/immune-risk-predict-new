package com.immuserisk.common.exception;

import com.immuserisk.common.R;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理Shiro权限异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public R<String> handleAuthorizationException(AuthorizationException e) {
        return R.error("没有相关权限");
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> handleBusinessException(BusinessException e) {
        return R.error(e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        return R.error("系统异常，请联系管理员");
    }
} 