package com.hzj.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hzj.shiro.realm.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建 ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //  设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //  添加 shiro 内置过滤器
        Map<String,String> map = new LinkedHashMap<>();
        //  可以匿名访问 / 跳转到 welcome页面
        map.put("/","anon");
        //  可以匿名访问 /login 跳转到 登录页面
        map.put("/login","anon");
        //  可以匿名访问 /loginForm 提交表单信息
        map.put("/loginForm","anon");
        //  登出
        map.put("/logout","logout");

        //  role level1 可以访问 level1
        map.put("/level1/**","authc,roles[level1]");
        //  role level2 可以访问 level1、level2
        map.put("/level2/**","authc,roles[level2]");
        //  role level3 可以访问 level1、level2、level3
        map.put("/level3/**","authc,roles[level3]");


        //  其他页面都需要经过认证才能访问
        map.put("/**","authc");
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建 DefaultWebSecurityManager
     * @param userRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //  关联 Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建 UserRealm
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 配置 ShiroDialect，用于 thymeleaf 和 shiro 标签配合使用
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
