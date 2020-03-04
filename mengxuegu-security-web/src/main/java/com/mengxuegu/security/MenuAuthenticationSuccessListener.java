package com.mengxuegu.security;

import com.mengxuegu.security.authertication.AuthenticationSuccessListener;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @auther xukehan
 */
@Service
public class MenuAuthenticationSuccessListener implements AuthenticationSuccessListener {
    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 查询用户所拥有的权限菜单
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("用户所拥有的全部权限信息"+authentication);
        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof SysUser){
            SysUser sysUser = (SysUser) principal;
            loadMenuTree(sysUser);
        }
        logger.info("newAuthentication:"+authentication.getPrincipal());
    }

    /**
     * 只加载菜单，不需要按钮
     * @param sysUser
     */
    public void loadMenuTree(SysUser sysUser){
        //获取到了当前登录用户的菜单和按钮
        List<SysPermission> permissions = sysUser.getPermissions();
        logger.info("permissions:  "+permissions);
        if (CollectionUtils.isEmpty(permissions)){
            return;
        }
         List<SysPermission> menuList = Lists.newArrayList();
         permissions.forEach(sysPermission -> {
             if (sysPermission.getType() == 1){
                 menuList.add(sysPermission);
             }
         });

         //获取 每个菜单的 子菜单
         menuList.forEach(sysPermission -> {
             //子菜单
            List<SysPermission> childMenu = Lists.newArrayList();
            List<String> childUrl = Lists.newArrayList();
            menuList.forEach(permission ->{
                if (permission.getParentId().equals(sysPermission.getId())){
                    childMenu.add(permission);
                    childUrl.add(permission.getUrl());
                }
            });
            sysPermission.setChildren(childMenu);
            sysPermission.setChildrenUrl(childUrl);
         });


         //3.menuList 里面每元素的子菜单分配好
            List<SysPermission> result = Lists.newArrayList();
            menuList.forEach(permission -> {
                if (permission.getParentId().equals(0L)){
                    result.add(permission);
                }
            });
            sysUser.setPermissions(result);
    }
}
