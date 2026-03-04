package com.immuserisk.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一响应结果类
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; // 编码：1成功，0和其它数字为失败
    private String msg;   // 错误信息
    private T data;      // 数据

    //成功响应（带数据）
    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.code = 1;
        r.data = data;
        return r;
    }

    //成功响应（无数据）
    public static <T> R<T> success() {
        R<T> r = new R<>();
        r.code = 1;
        return r;
    }

    //错误响应（带错误信息）
    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.code = 0;
        r.msg = msg;
        return r;
    }

    //成功响应的别名（带数据）
    public static <T> R<T> ok(T data) {
        return success(data);
    }
    //无数据
    public static <T> R<T> ok() {
        return success();
    }
} 