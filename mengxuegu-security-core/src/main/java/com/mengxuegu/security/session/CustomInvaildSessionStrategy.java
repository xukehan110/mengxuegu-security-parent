package com.mengxuegu.security.session;

import com.megxuegu.base.result.MengxueguResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当session失效后的处理逻辑
 * @auther xukehan
 */
public class CustomInvaildSessionStrategy implements InvalidSessionStrategy {


    private SessionRegistry sessionRegistry;

    public CustomInvaildSessionStrategy(SessionRegistry sessionRegistry){
        this.sessionRegistry = sessionRegistry;
    }


    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {

        sessionRegistry.removeSessionInformation(request.getRequestedSessionId());

        //要将cookie中的jssionId删除
        cancelCookie(request,response);

        MengxueguResult build = new MengxueguResult().build(
                HttpStatus.UNAUTHORIZED.value(), "登录已超时，请重新登录");

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(build.toJsonString());
    }


    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
