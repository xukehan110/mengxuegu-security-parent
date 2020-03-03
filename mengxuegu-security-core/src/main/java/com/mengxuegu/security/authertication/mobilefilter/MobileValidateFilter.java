package com.mengxuegu.security.authertication.mobilefilter;

import com.mengxuegu.security.authertication.CostomAuthenticationFailureHander;
import com.mengxuegu.security.authertication.exception.ValidateCodeException;
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
 * @auther xukehan
 */
@Component
public class MobileValidateFilter extends OncePerRequestFilter {

    private static final String SESSION_MOBILE_KEY = "SESSION_KEY_MOBILE_CODE";

    @Autowired
    private CostomAuthenticationFailureHander costomAuthenticationFailureHander;

    /**
     * 校验用户输入的验证码是否正确
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         if ("/mobile/form".equals(request.getRequestURI())
                 && request.getMethod().equalsIgnoreCase("post")){
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
        String attribute = (String) request.getSession().getAttribute(SESSION_MOBILE_KEY);
        //获取用户输入的验证码
        String inputCode = request.getParameter("mobilecode");

        if (StringUtils.isBlank(inputCode)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if (!inputCode.equalsIgnoreCase(attribute)){
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
