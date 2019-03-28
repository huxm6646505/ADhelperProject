package com.hansholdings.authorization.shiro.realm;

import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.entity.User;
import com.hansholdings.manage.entity.Admin;
import com.hansholdings.manage.entity.Function;
import com.hansholdings.manage.entity.Role;
import com.hansholdings.manage.service.IAdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maozz11347 on 2017/7/17.
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Admin admin = adminService.findByUsername(username);
        Set<Role> roles = admin.getRoles();
        Set<String> shiroPermissions = new HashSet<>();
        for (Role role : roles) {
            Set<Function> funcs = role.getFunctions();
            for (Function function : funcs) {
                shiroPermissions.add(function.getShiroPermission());
            }
        }

        authorizationInfo.setStringPermissions(shiroPermissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Admin admin = adminService.findByUsername(username);
        String password = new String((char[]) token.getCredentials());

        if (admin == null) { throw new UnknownAccountException(); }
        User user = admin.getUser();
        // 账号不存在
        if (user == null) { throw new UnknownAccountException("账号或密码不正确"); }
        // 密码错误
        if (!password.equals(user.getPassword())) { throw new IncorrectCredentialsException("账号或密码不正确"); }
        // 账号锁定
         if (admin.getLocked() == 0) { throw new
         LockedAccountException("账号已被锁定,请联系管理员"); }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

        admin.setLoginTime(new Date());
        admin.setLoginCount(admin.getLoginCount()!=null?(admin.getLoginCount()+1):1);
        adminService.update(admin);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(Constants.ADMIN_SESSION_ID,admin.getId());
        session.setAttribute(Constants.USER_SESSION_ID, user);
        session.setAttribute(Constants.OUROLES_SESSION_ID,admin.getOuroles());

        return info;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}

