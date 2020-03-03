package com.mengxuegu.security.session;

import com.mengxuegu.security.authertication.CostomAuthenticationFailureHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 当同一用户的session达到指定数量时，会执行该类
 * @auther xukehan
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {


    @Autowired
    CostomAuthenticationFailureHander costomAuthenticationFailureHander;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        //1.获取用户名
        UserDetails userDetails = (UserDetails)event.getSessionInformation().getPrincipal();

        AuthenticationException exception =
                new AuthenticationServiceException(
                        String.format("[%s] 用户在另外一台电脑登录，您已被下线",userDetails.getUsername()));

        try {
            event.getRequest().setAttribute("toAuthentication",true);
            costomAuthenticationFailureHander.onAuthenticationFailure(event.getRequest(),event.getResponse(),exception);

        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
