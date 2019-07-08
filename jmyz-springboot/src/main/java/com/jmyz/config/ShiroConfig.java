package com.jmyz.config;

import com.jmyz.utils.shiro.JWTFilter;
import com.jmyz.utils.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(shiroRealm());

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        Map<String, Filter> jwtFilterMap = new HashMap<>();
        jwtFilterMap.put("jwt", new JWTFilter());
        shiroFilter.setFilters(jwtFilterMap);
        shiroFilter.setSecurityManager(securityManager);
//        shiroFilter.setLoginUrl("/login.html");
        //shiroFilter.setSuccessUrl("/index.html");
        shiroFilter.setUnauthorizedUrl("/");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/account/**", "anon");
        filterMap.put("/region/**", "anon");

        //swagger配置
        filterMap.put("/swagger**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/webjars/**", "anon");

        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    /**
     * 会话Cookie模板
     *
     * @return
     */
    @Bean(name = "sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("smsSid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public UserRealm shiroRealm() {
        return new UserRealm();
    }

}
