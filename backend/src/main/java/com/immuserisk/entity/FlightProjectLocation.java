package com.immuserisk.entity;

import lombok.Data;

@Data
public class FlightProjectLocation {
    private Integer projectLocationID;//关联编号
    private Integer projectID; //飞行项目编号
    private Integer locationID; //地点编号
} 