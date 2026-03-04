package com.immuserisk.service.impl;

import com.immuserisk.common.exception.BusinessException;
import com.immuserisk.dao.AdminDao;
import com.immuserisk.dao.TokenBlacklistDao;
import com.immuserisk.entity.Admin;
import com.immuserisk.entity.TokenBlacklist;
import com.immuserisk.entity.User;
import com.immuserisk.service.AdminService;
import com.immuserisk.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private TokenBlacklistDao tokenBlacklistDao;

    @Override
    public String login(String phoneNumber, String password) {
        // 1. 根据手机号查询管理员
        Admin admin = adminDao.getByPhoneNumber(phoneNumber);
        if (admin == null) {
            throw new BusinessException("账号不存在");
        }

        // 2. 密码校验
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encryptedPassword.equals(admin.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 3. 生成token
        return JwtUtil.generateToken(admin.getAdminID());
    }

    @Override
    public void logout(String token) {
        // 1. 验证token是否有效
        if (!JwtUtil.validateToken(token)) {
            throw new BusinessException("无效的token");
        }

        // 2. 获取token中的管理员ID
        Integer adminId = JwtUtil.getAdminIdFromToken(token);

        // 3. 将token加入黑名单
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setAdminId(adminId);
        blacklist.setCreateTime(LocalDateTime.now());
        blacklist.setExpireTime(LocalDateTime.now().plusDays(1)); // token有效期1天
        tokenBlacklistDao.insert(blacklist);

        // 4. 清理过期的token
        tokenBlacklistDao.deleteExpired(LocalDateTime.now());
    }

    @Override
    public boolean validateToken(String token) {
        // 1. 检查token格式是否有效
        if (!JwtUtil.validateToken(token)) {
            return false;
        }

        // 2. 检查token是否在黑名单中
        TokenBlacklist blacklist = tokenBlacklistDao.getByToken(token);
        return blacklist == null;
    }

    @Override
    public Admin getByPhoneNumber(String phoneNumber) {
        return adminDao.getByPhoneNumber(phoneNumber);
    }

    @Override
    public Map<String, Object> page(Integer page, Integer limit, String adminName, String phoneNumber) {
        // 计算偏移量
        int offset = (page - 1) * limit;
        
        // 查询数据
        List<Admin> items = adminDao.page(offset, limit, adminName, phoneNumber);
        Integer total = adminDao.count(adminName, phoneNumber);

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("total", total);
        
        return result;
    }

    @Override
    public void add(Admin admin) {
        // 加密密码
        admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        adminDao.insert(admin);
    }

    @Override
    public void update(Admin admin) {
        // 如果密码不为空，则需要加密
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
        }
        adminDao.update(admin);
    }

    @Override
    public void delete(Integer id) {
        adminDao.delete(id);
    }
} 