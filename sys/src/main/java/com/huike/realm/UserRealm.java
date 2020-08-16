package com.huike.realm;

import com.huike.domain.AdminUser;
import com.huike.services.AdminUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    AdminUserService adminUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println(principalCollection.getPrimaryPrincipal()+"进入授权");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username =(String) principalCollection.getPrimaryPrincipal();
        // 1、对当前登录的账号进行角色的授权。
        authorizationInfo.addRoles(adminUserService.findRolesByUserName(username));
        // 2、对当前登录的账号进行权限的授权。
        authorizationInfo.addStringPermissions(adminUserService.findPermissionsByUserName(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println(authenticationToken.getPrincipal()+"进入认证");
        String userName = (String) authenticationToken.getPrincipal();
        AdminUser adminUser = adminUserService.selectAdminUserByName(userName);
        if (adminUser == null) {
            return  null;
        }
        // 账号被禁
        if (adminUser.getIsDisabled()) {
            throw new AuthenticationException("账号已被禁用");
        }
        SimpleAuthenticationInfo
                authenticationInfo =
                new SimpleAuthenticationInfo(
                        adminUser.getAccount(),
                        adminUser.getPassword(),
                        ByteSource.Util.bytes(adminUser.getAccount()),
                        getName());
        return authenticationInfo;
    }
}
