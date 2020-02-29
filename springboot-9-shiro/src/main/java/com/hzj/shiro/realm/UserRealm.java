package com.hzj.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject currentUser = SecurityUtils.getSubject();
        String principal = (String) currentUser.getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if("admin".equals(principal)) {
            info.addRole("level1");
            info.addRole("level2");
            info.addRole("level3");
        }
        if("manager".equals(principal)) {
            info.addRole("level1");
            info.addRole("level2");
        }
        if("employee".equals(principal)) {
            info.addRole("level1");
        }
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

//        System.out.println(token.getUsername());
//        System.out.println(token.getPassword());

        //  ！这里有一点需要注意：若判断 !"12345".equals(token.getPassword()，
        //  则会报错。至于为什么，我也不知道。
        if("admin".equals(token.getUsername()) || "manager".equals(token.getUsername()) || "employee".equals(token.getUsername())) {
            return new SimpleAuthenticationInfo(token.getUsername(),"12345","");
        } else {
            return null;
        }
    }
}
