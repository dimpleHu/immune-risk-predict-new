package com.immuserisk.dao;

import com.immuserisk.entity.TokenBlacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface TokenBlacklistDao {
    /**
     * 添加token到黑名单
     */
    void insert(TokenBlacklist tokenBlacklist);

    /**
     * 根据token查询黑名单记录
     */
    TokenBlacklist getByToken(@Param("token") String token);

    /**
     * 删除过期的token
     */
    void deleteExpired(@Param("now") LocalDateTime now);
} 