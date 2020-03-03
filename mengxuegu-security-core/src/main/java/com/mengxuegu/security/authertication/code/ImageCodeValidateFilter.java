package com.mengxuegu.security.authertication.code;


import com.mengxuegu.security.authertication.CostomAuthenticationFailureHander;
import com.mengxuegu.security.authertication.exception.ValidateCodeException;
import com.mengxuegu.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter: 所有请求之前被调用一次
 */
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private CostomAuthenticationFailureHander costomAuthenticationFailureHander;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.如果是post的登录请求，则校验输入的验证码
        if (securityProperties.getAuthentication().getLoginProcessingUrl()
                .equals(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("post")) {
            try {
                //验证合法性
                validate(request);
            }catch (AuthenticationException e){
                //交给失败处理器处理
                costomAuthenticationFailureHander.onAuthenticationFailure(request,response,e);
                //记得结束
                return;
            }
        }
        //放行请求
        filterChain.doFilter(request,response);
    }

    private void validate(HttpServletRequest request) {
        //获取session中的验证码
        String attribute = (String) request.getSession().getAttribute(SESSION_KEY);
        //获取用户输入的验证码
        String inputCode = request.getParameter("code");

        if (StringUtils.isBlank(inputCode)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if (!inputCode.equalsIgnoreCase(attribute)){
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
