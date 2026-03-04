package com.immuserisk.common;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//分页查询基础类
@Data
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum = 1;

    /**
     * 每页显示记录数
     */
    @NotNull(message = "每页记录数不能为空")
    @Min(value = 1, message = "每页记录数不能小于 1")
    private Integer pageSize = 10;

    /**
     * 获取开始位置
     */
    public Integer getStart() {
        return (pageNum - 1) * pageSize;
    }
} 