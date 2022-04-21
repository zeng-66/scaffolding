package com.pro.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        // 登录配置
        shiroFilter.setLoginUrl("/index.html");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setUnauthorizedUrl("/error?code=403");
        // 自定义过滤器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("myLoginFilter", new MyLoginFilter());
        shiroFilter.setFilters(filtersMap);
        // 拦截配置
        Map<String, String> filterChainDefinitions = new LinkedHashMap<>();
        //放开登录接口
        filterChainDefinitions.put("/admin/system/login", "anon");
        //后台上传接口
        filterChainDefinitions.put("/admin/file/**", "anon");
        //html页面详情接口
        filterChainDefinitions.put("/admin/config/pageDetail", "anon");
        //app端登录
        filterChainDefinitions.put("/server/**", "anon");
        //H5端
        filterChainDefinitions.put("/api/**", "anon");

        //放开阿里云oss回调
        filterChainDefinitions.put("/admin/oss/callback", "anon");
        //登出
        filterChainDefinitions.put("/logout", "logout");
        //对所有用户验证
        filterChainDefinitions.put("/swagger-ui.html","anon");
        filterChainDefinitions.put("/doc.html","anon");
        filterChainDefinitions.put("/swagger/**","anon");
        filterChainDefinitions.put("/webjars/**", "anon");
        filterChainDefinitions.put("/swagger-resources/**","anon");
        filterChainDefinitions.put("/v2/**","anon");
        filterChainDefinitions.put("/static/**", "anon");
        filterChainDefinitions.put("/**", "myLoginFilter,authc");
        filterChainDefinitions.put("/index.html","anon");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitions);
        return shiroFilter;
    }

    @Bean(name = "userRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setSessionManager(getDefaultWebSessionManager());
        return securityManager;
    }

//    @Bean(name = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");  //散列算法
        credentialsMatcher.setHashIterations(3);  //散列次数
        return credentialsMatcher;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }

    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1000 * 60 * 60 * 6);// 会话过期时间，单位：毫秒(在无操作时开始计时)
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        return defaultWebSessionManager;
    }

    /**
     * shiro里实现的Advisor类,用来拦截注解的方法 .
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
