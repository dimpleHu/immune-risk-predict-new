package com.immuserisk.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Order {
    // 新增订单状态常量
    public static final String STATUS_RESERVED = "已预约";
    public static final String STATUS_PENDING = "待支付";
    public static final String STATUS_PAID = "已支付";
    public static final String STATUS_COMPLETED = "已完成";
    public static final String STATUS_CANCELLED = "已取消";
    public static final String STATUS_EXPIRED = "已失效";

    private Integer orderID;      // 订单编号
    private Integer userID;       // 下单用户编号
    private Integer reservationID;// 预约编号
    private Integer headcount;    // 预约总人数
    private Double orderAmount;   // 订单金额
    private String status;        // 订单状态（已预约、待支付、已支付、已完成、已取消）
    private Integer projectId;    // 项目ID
    private Date createTime;      // 创建时间
    private Date expirationTime; // 新增支付过期时间
    
    // 关联对象
    private User user;                    // 用户信息
    private FlightReservation reservation;// 预约信息
    private Payment payment;              // 支付信息

    // 新增检查是否可支付
    public boolean isPayable() {
        return STATUS_PENDING.equals(this.status) || STATUS_RESERVED.equals(this.status);
    }

    // 新增检查是否过期
    public boolean isExpired() {
        return expirationTime != null && new Date().after(expirationTime);
    }
} 