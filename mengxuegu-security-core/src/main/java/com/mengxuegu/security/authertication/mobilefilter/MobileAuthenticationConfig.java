package com.mengxuegu.security.authertication.mobilefilter;

import com.mengxuegu.security.authertication.CostomAuthenticationFailureHander;
import com.mengxuegu.security.authertication.CostomAuthenticationSuccessHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * 用于组合其他关于手机登录的组件
 * @auther xukehan
 */
@Configuration
public class MobileAuthenticationConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CostomAuthenticationFailureHander costomAuthenticationFailureHander;

    @Autowired
    private CostomAuthenticationSuccessHander costomAuthenticationSuccessHander;

    @Autowired
    private UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();

        //获取容器中已经存在的AuthenticationManager对象，并传入mobileAuthenticationFilter中
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        // 指定记住我功能
        mobileAuthenticationFilter.setRememberMeServices(http.getSharedObject(RememberMeServices.class));

        //session重复登录 管理
        mobileAuthenticationFilter.setSessionAuthenticationStrategy(http.getSharedObject(SessionAuthenticationStrategy.class));

        //加入失败和成功处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(costomAuthenticationSuccessHander);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(costomAuthenticationFailureHander);

        //构建一个MobileAuthenticationProvider实例，接收mobileUserDetailsService 通过手机号查询信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);

        //将provider绑定到HttpSecurity上，并将手机号认证过滤器绑定到用户名密码认证过滤器之后
        http.authenticationProvider(provider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
