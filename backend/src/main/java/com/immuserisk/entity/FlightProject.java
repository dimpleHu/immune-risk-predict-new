package com.immuserisk.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FlightProject {
    /**
     * 飞行项目编号
     */
    private Integer projectID;
    
    /**
     * 飞行项目名称
     */
    private String projectName;
    
    /**
     * 飞行项目简介
     */
    private String briefIntroduction;
    
    /**
     * 飞行高度
     */
    private String flightHeight;//
    private Integer flightTime;//飞行时长（分钟h）
    private BigDecimal price;//飞行项目价格
    private String targetCrowd;//适合人群
    private String projectType;//飞行项目类型
    private String projectImage;//飞行项目图片链接或路径
    private Integer volume;//可预约容量
} 