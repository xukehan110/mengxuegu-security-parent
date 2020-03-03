package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @auther xukehan
 */
public abstract class AbstructUserDetailsService implements UserDetailsService{

    @Autowired
    private SysPermissionService sysPermissionService;

    abstract SysUser findUser(String userOrMobile);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = findUser(username);
        findSysPermission(sysUser);
        return sysUser;
    }

    private void findSysPermission(SysUser sysUser){
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        //2.查询该用户有哪一些权限
        List<SysPermission> permissions = sysPermissionService.findByUserId(sysUser.getId());

        if (CollectionUtils.isEmpty(permissions)){
            return;
        }

        //在左侧菜单，动态渲染的时候会使用
        sysUser.setPermissions(permissions);

        //3.封装用户信息和权限信息
        List<GrantedAuthority> authorities = Lists.newArrayList();
        permissions.forEach(sysPermission -> {
            //添加权限标识
            authorities.add(new SimpleGrantedAuthority(sysPermission.getCode()));
        });
        sysUser.setAuthorities(authorities);
    }
}
