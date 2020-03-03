package com.mengxuegu.security.authertication;

import com.alibaba.fastjson.JSON;
import com.megxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.emun.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证处理器
 * 1. 决定 响应json还是跳转页面，或者认证成功进行其他处理
 */
@Component("costomAuthenticationSuccessHander")
//public class CostomAuthenticationSuccessHander implements AuthenticationSuccessHandler {
public class CostomAuthenticationSuccessHander extends SavedRequestAwareAuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SecurityProperties securityProperties;

    @Autowired(required = false)  //容器中可以不需要有接口的实现，如果有自动注入
    AuthenticationSuccessListener authenticationSuccessListener;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if(authenticationSuccessListener != null){
            //认证成功后，调用此监听，进行后续处理，比如加载用户菜单
            authenticationSuccessListener.successListener(request,response,authentication);
        }


        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())){
            //认证成功后，响应json字符串
            MengxueguResult result = MengxueguResult.ok("认证成功");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        }else {
            //重定向到上一次的地址上,引发跳转到认证页面的地址上
            logger.info("authentication = "+ JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
