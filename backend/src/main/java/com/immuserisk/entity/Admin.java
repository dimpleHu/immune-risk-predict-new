package com.immuserisk.entity;

import lombok.Data;

@Data
public class Admin {
    private Integer adminID;
    private String adminName;
    private String phoneNumber;
    private String password;
} 