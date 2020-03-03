package com.mengxuegu.security.authertication.mobilefilter;

import com.mengxuegu.security.authertication.exception.ValidateCodeException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机认证提供者
 * @auther xukehan
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 认证处理
     * 1.通过手机号码，查询用户信息（UserDetailsService实现)
     * 2.当查询到用户信息，则认证通过，封装Authentication对象
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken =
                (MobileAuthenticationToken)authentication;
        String mobile = (String)mobileAuthenticationToken.getPrincipal();
        //1.通过手机号码，查询用户信息（UserDetailsService实现)
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if (userDetails == null){
            throw new ValidateCodeException("该手机号未注册");
        }

        //封装到 MobileAuthenticationToken
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());

        return authenticationToken;
    }

    /**
     * 判断传入的类型是不是MobileAuthenticationToken类型
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
