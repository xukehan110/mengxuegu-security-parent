package com.mengxuegu.security.authorize;

import com.mengxuegu.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 安全认证 （身份认证的配置）
 * @auther xukehan
 */
@Component
@Order(Integer.MAX_VALUE)//值越小加载越优先
public class CustomAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider{

    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void configurer(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(securityProperties.getAuthentication().getLoginPage(),
                securityProperties.getAuthentication().getImageCodeUrl(),
                securityProperties.getAuthentication().getMobileCodeUrl(),
                securityProperties.getAuthentication().getMobilePage()).permitAll()

                .anyRequest().authenticated();//其他请求都要通过身份认证
    }
}
