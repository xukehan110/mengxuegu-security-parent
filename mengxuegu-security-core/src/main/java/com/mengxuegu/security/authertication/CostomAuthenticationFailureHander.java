package com.mengxuegu.security.authertication;

import com.megxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.emun.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理失败认证
 */
@Component("costomAuthenticationFailureHander")
//public class CostomAuthenticationFailureHander implements AuthenticationFailureHandler {
public class CostomAuthenticationFailureHander extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 认证失败时抛出的异常
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            //认证失败响应json字符串
            MengxueguResult build = MengxueguResult.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(build.toJsonString());
        }else{
            //super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
            String referer = request.getHeader("Referer");

            //配置多端登录和登录失败页面
            String lastUrl = request.getAttribute("toAuthentication") != null ?
                    securityProperties.getAuthentication().getLoginPage():StringUtils.substringBefore(referer, "?");

            super.setDefaultFailureUrl(lastUrl+"?error");
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
