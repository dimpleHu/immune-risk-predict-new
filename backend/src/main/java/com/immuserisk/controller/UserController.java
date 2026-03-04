package com.immuserisk.controller;

import com.immuserisk.annotation.IgnoreAuth;
import com.immuserisk.common.R;
import com.immuserisk.entity.User;
import com.immuserisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public R<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            String userName,
            String phoneNumber) {
        Map<String, Object> pageResult = userService.page(page, limit, userName, phoneNumber);
        return R.success(pageResult);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody User user) {
        userService.add(user);
        return R.success("添加成功");
    }

    @PutMapping("/update")
    public R<String> update(@RequestBody User user) {
        userService.update(user);
        return R.success("更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable("id") Integer userID) {
        userService.delete(userID);
        return R.success("删除成功");
    }

    /**
     * 用户登录
     */
    @IgnoreAuth
    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody User user) {
        try {
            Map<String, Object> result = userService.login(user.getPhoneNumber(), user.getPassword());
            return R.success(result);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 用户注册
     */
    @IgnoreAuth
    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return R.success("注册成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info/{userId}")
    public R<User> info(@PathVariable("userId") Integer userId) {
        try {
            User userInfo = userService.getById(userId);
            if (userInfo == null) {
                return R.error("用户不存在");
            }
            return R.success(userInfo);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update/{userId}")
    public R<String> updateInfo(@PathVariable("userId") Integer userId, @RequestBody User user) {
        try {
            user.setUserID(userId);
            userService.update(user);
            return R.success("更新成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/password/{userId}")
    public R<String> updatePassword(@PathVariable("userId") Integer userId, @RequestBody Map<String, String> params) {
        try {
            String oldPassword = params.get("oldPassword");
            String newPassword = params.get("newPassword");
            userService.updatePassword(userId, oldPassword, newPassword);
            return R.success("密码修改成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R<String> logout(@RequestHeader("Token") String token) {
        try {
            userService.logout(token);
            return R.success("退出成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
} 