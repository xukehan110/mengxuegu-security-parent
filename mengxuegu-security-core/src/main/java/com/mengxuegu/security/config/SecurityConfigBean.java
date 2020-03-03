package com.mengxuegu.security.config;

import com.mengxuegu.security.mobile.Impl.SmsCodeSender;
import com.mengxuegu.security.mobile.SmsSend;
import com.mengxuegu.security.session.CustomInvaildSessionStrategy;
import com.mengxuegu.security.session.CustomSessionInformationExpiredStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @auther xukehan
 */
@Configuration
public class SecurityConfigBean {



    /**
     * @ConditionalOnMissingBean(SmsSend.class)
     * 默认情况下，采用的是SmsCodeSender实例，
     * 但是容器中有其他的SmsSend类型的实例，
     * 则当前的SmsSend实例就失效了
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend(){
        return new SmsCodeSender();
    }

    /**
     * 当session失效后的处理类
     * @return
     */
    @Autowired
    private SessionRegistry sessionRegistry;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new CustomInvaildSessionStrategy(sessionRegistry);
    }

    /**
     * 允许一个用户登录策略
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new CustomSessionInformationExpiredStrategy();
    }

    //位置存放不对会导致 Error creating bean with name 'sessionRegistry': Requested bean is currently in creation: Is there an unresolvable circular reference?
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}
