package com.mengxuegu.authorize;

import com.mengxuegu.security.authorize.AuthorizeConfigurerProvider;
import com.mengxuegu.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 系统管理模块的授权配置
 * @auther xukehan
 */
@Component
public class SystemAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void configurer(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //应用的权限控制
//        config.antMatchers("/user").hasAuthority("sys:user")
//                .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
//                .antMatchers(HttpMethod.GET, "/permission")
//                .access("hasAuthority('sys:permission') or hasAnyRole('ADMIN','ROOT')");

    }
}
