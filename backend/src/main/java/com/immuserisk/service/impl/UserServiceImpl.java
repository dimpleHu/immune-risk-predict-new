package com.immuserisk.service.impl;

import com.immuserisk.common.exception.BusinessException;
import com.immuserisk.dao.TokenBlacklistDao;
import com.immuserisk.dao.UserDao;
import com.immuserisk.entity.TokenBlacklist;
import com.immuserisk.entity.User;
import com.immuserisk.service.UserService;
import com.immuserisk.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenBlacklistDao tokenBlacklistDao;

    @Override
    public Map<String, Object> login(String phoneNumber, String password) {
        // 1. 根据手机号查询用户
        User user = userDao.getByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new BusinessException("账号不存在");
        }

        // 2. 密码校验
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 3. 生成token
        String token = JwtUtil.generateToken(user.getUserID());

        // 4. 返回结果
        Map<String, Object> result = new HashMap<>();
        user.setPassword(null); // 清除密码
        result.put("token", token);
        result.put("userInfo", user);
        
        return result;
    }

    @Override
    public void register(User user) {
        // 1. 检查手机号是否已存在
        User existUser = userDao.getByPhoneNumber(user.getPhoneNumber());
        if (existUser != null) {
            throw new BusinessException("该手机号已被注册");
        }

        // 2. 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        
        // 3. 保存用户信息
        userDao.insert(user);
    }

    @Override
    public Map<String, Object> page(int page, int limit, String userName, String phoneNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (page - 1) * limit);
        params.put("limit", limit);
        params.put("userName", userName);
        params.put("phoneNumber", phoneNumber);

        List<User> items = userDao.list(params);
        // 查询时不返回密码
        items.forEach(user -> user.setPassword(null));
        
        long total = userDao.count(params);

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("total", total);
        
        return result;
    }

    @Override
    public User getById(Integer userID) {
        User user = userDao.getById(userID);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public void add(User user) {
        // 1. 检查手机号是否已存在
        User existUser = userDao.getByPhoneNumber(user.getPhoneNumber());
        if (existUser != null) {
            throw new BusinessException("该手机号已被注册");
        }

        // 2. 密码加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        
        // 3. 保存用户信息
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        // 1. 检查手机号是否已被其他用户使用
        User existUser = userDao.getByPhoneNumber(user.getPhoneNumber());
        if (existUser != null && !existUser.getUserID().equals(user.getUserID())) {
            throw new BusinessException("该手机号已被其他用户使用");
        }

        // 2. 更新用户信息
        userDao.update(user);
    }

    @Override
    public void delete(Integer userID) {
        // 1. 检查用户是否存在
        User user = userDao.getById(userID);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 删除用户
        userDao.delete(userID);
    }

    @Override
    public void updatePassword(Integer userID, String oldPassword, String newPassword) {
        // 1. 获取用户信息
        User user = userDao.getById(userID);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 验证旧密码
        String encryptedOldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!encryptedOldPassword.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        // 3. 更新密码
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        userDao.update(user);
    }

    @Override
    public void logout(String token) {
        // 1. 验证token是否有效
        if (!JwtUtil.validateToken(token)) {
            throw new BusinessException("无效的token");
        }

        // 2. 获取token中的用户ID
        Integer userId = JwtUtil.getAdminIdFromToken(token);

        // 3. 将token加入黑名单
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setAdminId(userId);
        blacklist.setCreateTime(LocalDateTime.now());
        blacklist.setExpireTime(LocalDateTime.now().plusDays(1)); // token有效期1天
        tokenBlacklistDao.insert(blacklist);

        // 4. 清理过期的token
        tokenBlacklistDao.deleteExpired(LocalDateTime.now());
    }
} 