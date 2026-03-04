package com.immuserisk.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TokenBlacklist {
    /**
     * 主键ID
     */
    private Integer id;
    
    /**
     * JWT token
     */
    private String token;
    
    /**
     * 管理员ID
     */
    private Integer adminId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
} 