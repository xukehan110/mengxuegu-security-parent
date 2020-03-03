package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstructUserDetailsService{

    @Autowired
    private SysUserService sysUserService;

    @Override
    SysUser findUser(String userOrMobile) {
        return sysUserService.findByUserName(userOrMobile);
    }
}
