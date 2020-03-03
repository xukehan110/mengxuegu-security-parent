package com.mengxuegu.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将所有的授权配置统一管理起来
 * @auther xukehan
 */
@Component
public class CustomAuthorizeConfigurerManager implements AuthorizeConfigurerManager {

    @Autowired
    List<AuthorizeConfigurerProvider> authorizeConfigurerProviders;

    //将一个个AuthorizeConfigurerProvider的实现类，传入配置的参数
    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigurerProvider provider:authorizeConfigurerProviders){
                provider.configurer(config);
        }
    }
}
