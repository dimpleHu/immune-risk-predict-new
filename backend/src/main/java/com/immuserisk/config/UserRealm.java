package com.immuserisk.config;

import com.immuserisk.entity.Admin;
import com.immuserisk.service.AdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义Realm
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Admin admin = (Admin) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 可以根据需要添加角色和权限
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String phoneNumber = upToken.getUsername();
        String password = new String(upToken.getPassword());

        // 根据手机号查询管理员
        Admin admin = adminService.getByPhoneNumber(phoneNumber);
        if (admin == null) {
            throw new UnknownAccountException("账号不存在");
        }

        // 验证密码
        if (!admin.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码错误");
        }

        return new SimpleAuthenticationInfo(admin, password, getName());
    }
} 