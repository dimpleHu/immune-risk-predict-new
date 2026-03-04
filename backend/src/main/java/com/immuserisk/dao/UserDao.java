package com.immuserisk.dao;

import com.immuserisk.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserDao {
    /**
     * 查询用户列表
     */
    List<User> list(Map<String, Object> params);
    
    /**
     * 查询用户总数
     */
    long count(Map<String, Object> params);
    
    /**
     * 根据ID查询用户
     */
    User getById(@Param("userID") Integer userID);
    
    /**
     * 根据手机号查询用户
     */
    User getByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    
    /**
     * 新增用户
     */
    int insert(User user);
    
    /**
     * 更新用户
     */
    int update(User user);
    
    /**
     * 删除用户
     */
    int delete(@Param("userID") Integer userID);
} 