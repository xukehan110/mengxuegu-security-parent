package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过手机号获取用户和权限资源
 * @auther xukehan
 */
@Component("mobileUserDetailsService")
public class MobileUserDetailsService extends AbstructUserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    SysUser findUser(String userOrMobile) {
        return sysUserService.findByMobile(userOrMobile);
    }
}
