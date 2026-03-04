package com.immuserisk.service;

import com.immuserisk.entity.Admin;
import com.immuserisk.entity.User;

import java.util.Map;

public interface AdminService {
    /**
     * 管理员登录
     * @param phoneNumber 手机号
     * @param password 密码
     * @return token
     */
    String login(String phoneNumber, String password);

    /**
     * 管理员退出
     * @param token JWT token
     */
    void logout(String token);

    /**
     * 验证token是否有效
     * @param token JWT token
     * @return 是否有效
     */
    boolean validateToken(String token);

    /**
     * 根据手机号查询管理员
     * @param phoneNumber 手机号
     * @return 管理员信息
     */
    Admin getByPhoneNumber(String phoneNumber);

    /**
     * 分页查询管理员列表
     * @param page 页码
     * @param limit 每页数量
     * @param adminName 管理员姓名
     * @param phoneNumber 手机号
     * @return 分页结果
     */
    Map<String, Object> page(Integer page, Integer limit, String adminName, String phoneNumber);

    /**
     * 添加管理员
     * @param admin 管理员信息
     */
    void add(Admin admin);

    /**
     * 更新管理员信息
     * @param admin 管理员信息
     */
    void update(Admin admin);

    /**
     * 删除管理员
     * @param id 管理员ID
     */
    void delete(Integer id);
} 