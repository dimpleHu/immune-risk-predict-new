package com.immuserisk.controller;

import com.immuserisk.common.R;
import com.immuserisk.entity.Admin;
import com.immuserisk.service.AdminService;
import com.immuserisk.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AdminDao adminDao;

    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody Admin admin) {
        // 调用service层的登录方法
        String token = adminService.login(admin.getPhoneNumber(), admin.getPassword());
        
        // 查询管理员信息（不包含密码）
        Admin adminInfo = adminDao.getByPhoneNumber(admin.getPhoneNumber());
        adminInfo.setPassword(null);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("adminInfo", adminInfo);
        
        return R.success(map);
    }

    @PostMapping("/logout")
    public R<String> logout(@RequestHeader("Authorization") String token) {
        adminService.logout(token);
        return R.success("退出成功");
    }

    @GetMapping("/list")
    public R<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            String adminName,
            String phoneNumber) {
        Map<String, Object> pageResult = adminService.page(page, limit, adminName, phoneNumber);
        return R.success(pageResult);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody Admin admin) {
        adminService.add(admin);
        return R.success("添加成功");
    }

    @PutMapping("/update")
    public R<String> update(@RequestBody Admin admin) {
        adminService.update(admin);
        return R.success("更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public R<String> delete(@PathVariable("id") Integer id) {
        adminService.delete(id);
        return R.success("删除成功");
    }
} 