package com.immuserisk.service;

import com.immuserisk.entity.User;
import java.util.Map;

public interface UserService {
    /**
     * 用户登录
     * @param phoneNumber 手机号
     * @param password 密码
     * @return 登录结果
     */
    Map<String, Object> login(String phoneNumber, String password);

    /**
     * 用户注册
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 分页查询用户列表
     */
    Map<String, Object> page(int page, int limit, String userName, String phoneNumber);
    
    /**
     * 根据ID查询用户
     */
    User getById(Integer userID);
    
    /**
     * 新增用户
     */
    void add(User user);
    
    /**
     * 更新用户
     */
    void update(User user);
    
    /**
     * 删除用户
     */
    void delete(Integer userID);

    /**
     * 修改密码
     * @param userID 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Integer userID, String oldPassword, String newPassword);

    /**
     * 退出登录
     * @param token JWT token
     */
    void logout(String token);
} 