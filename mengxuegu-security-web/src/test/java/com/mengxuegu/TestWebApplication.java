package com.mengxuegu;

import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.service.SysPermissionService;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @auther xukehan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWebApplication {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysPermissionService sysPermissionService;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired


    @Test
    public void test(){
        //System.out.println(sysPermissionService.getById(17));
//        Page<SysRole> page = new Page<>(0,3);
//        SysRole sysRole = new SysRole();
//        sysRole.setName("管理");
        System.out.println(sysPermissionMapper.findByRoleId(9L));
        //System.out.println(sysUserService.findByUserName("test"));
    }
}
