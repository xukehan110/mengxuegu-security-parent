package com.mengxuegu.security.authertication;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口监听认证成功后的处理
 * @auther xukehan
 */
public interface AuthenticationSuccessListener {

    void successListener(HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication);
}
