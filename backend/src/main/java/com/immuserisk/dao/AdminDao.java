package com.immuserisk.dao;

import com.immuserisk.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminDao {
    /**
     * 根据手机号查询管理员
     */
    Admin getByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * 分页查询管理员列表
     */
    List<Admin> page(@Param("offset") Integer offset, 
                     @Param("limit") Integer limit, 
                     @Param("adminName") String adminName, 
                     @Param("phoneNumber") String phoneNumber);

    /**
     * 查询总记录数
     */
    Integer count(@Param("adminName") String adminName, 
                 @Param("phoneNumber") String phoneNumber);

    /**
     * 添加管理员
     */
    void insert(Admin admin);

    /**
     * 更新管理员信息
     */
    void update(Admin admin);

    /**
     * 删除管理员
     */
    void delete(@Param("adminID") Integer adminID);
} 