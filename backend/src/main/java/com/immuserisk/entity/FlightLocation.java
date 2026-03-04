package com.immuserisk.entity;

import lombok.Data;

@Data
public class FlightLocation {
    /**
     * 地点编号
     */
    private Integer locationID;
    
    /**
     * 地点名称
     */
    private String locationName;
    
    /**
     * 详细地址
     */
    private String locationAddress;
    
    /**
     * 地点描述
     */
    private String locationDescription;
    
    /**
     * 状态：1-可用，0-不可用
     */
    private Integer status;
} 