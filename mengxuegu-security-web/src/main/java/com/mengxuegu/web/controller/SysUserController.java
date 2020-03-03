package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.megxuegu.base.result.MengxueguResult;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 * @auther xukehan
 */
@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    private static final String HTML_PREFIX = "system/user/";

    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping(value = {"/",""}) //  /user/ 或者  /user
    public String user(){
        return HTML_PREFIX + "user-list";
    }


    @PreAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/page")
    @ResponseBody
    public MengxueguResult page(Page<SysUser> page,SysUser sysUser){

        return MengxueguResult.ok(sysUserService.selectPage(page,sysUser));
    }

    @PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
    @GetMapping(value = {"/form","/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model){
        SysUser sysUser= sysUserService.findById(id);
        model.addAttribute("user",sysUser);

        List<SysRole> roleList = sysRoleService.list();
        model.addAttribute("roleList",roleList);
        return HTML_PREFIX + "user-form";
    }

    @PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
    @RequestMapping(value = "",method = {RequestMethod.PUT,RequestMethod.POST})
    public String saveOrUpdate(SysUser sysUser){

        sysUserService.saveOrUpdate(sysUser);
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable Long id){
        sysUserService.deleteUserById(id);
        return MengxueguResult.ok();
    }


}
