package com.immuserisk.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 当前页数
     */
    private int pageNum;

    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 分页
     * @param list 列表数据
     * @param total 总记录数
     * @param pageSize 每页记录数
     * @param pageNum 当前页数
     */
    public PageResult(List<T> list, long total, int pageSize, int pageNum) {
        this.list = list;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
} 