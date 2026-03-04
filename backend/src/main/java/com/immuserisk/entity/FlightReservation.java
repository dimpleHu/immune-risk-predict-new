package com.immuserisk.entity;

import java.util.Date;

/**
 * 飞行预约实体类
 */
public class FlightReservation {
    private Integer reservationID;    // 预约编号
    private Integer projectID;        // 飞行项目编号
    private Integer userID;           // 预约用户编号
    private String flightAdd;         // 飞行地点
    private Date flightTime;          // 飞行时间
    private Integer volume;           // 可预约容量
    private Integer headcount;        // 预约人数
    private String contactName;       // 预约人姓名
    private String contactPhone;      // 预约人电话号码
    private String contactID;         // 预约人身份证号
    private String status;            // 预约状态（通过SQL计算得到）
    private String projectName;       // 项目名称（关联查询）
    private String orderStatus;       // 订单状态（关联查询）

    @Override
    public String toString() {
        return "FlightReservation{" +
                "reservationID=" + reservationID +
                ", projectID=" + projectID +
                ", userID=" + userID +
                ", flightAdd='" + flightAdd + '\'' +
                ", flightTime=" + flightTime +
                ", volume=" + volume +
                ", headcount=" + headcount +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactID='" + contactID + '\'' +
                ", status='" + status + '\'' +
                ", projectName='" + projectName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

    // Getters and Setters
    public Integer getReservationID() {
        return reservationID;
    }

    public void setReservationID(Integer reservationID) {
        this.reservationID = reservationID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFlightAdd() {
        return flightAdd;
    }

    public void setFlightAdd(String flightAdd) {
        this.flightAdd = flightAdd;
    }

    public Date getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Date flightTime) {
        this.flightTime = flightTime;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
} 