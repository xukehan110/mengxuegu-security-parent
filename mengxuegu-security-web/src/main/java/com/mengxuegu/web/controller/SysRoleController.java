package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.megxuegu.base.result.MengxueguResult;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 * @auther xukehan
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    private static final String HTML_PREFIX = "system/role/";

    @PreAuthorize("hasAuthority('sys:role')")
    @GetMapping(value = {"/",""}) //  /role/ 或者  /role
    public String role(){
        return HTML_PREFIX + "role-list";
    }

    @PreAuthorize("hasAuthority('sys:role:list')")
    @PostMapping("/page")
    @ResponseBody
    public MengxueguResult page(Page<SysRole> page,SysRole sysRole){

       return MengxueguResult.ok(sysRoleService.selectPage(page,sysRole));
    }

    /**
     * 点击新增或者修改数据进入页面
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    @GetMapping(value = {"/form","/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model){
        SysRole sysRole = sysRoleService.findById(id);
        model.addAttribute("role",sysRole);
        return HTML_PREFIX + "role-form";
    }

    /**
     * 提交新增或修改的数据
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    @RequestMapping(method = {RequestMethod.PUT,RequestMethod.POST},value = "")
    public String saveOrUpdate(SysRole sysRole){

        sysRoleService.saveOrUpdate(sysRole);
        return "redirect:/role";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long id){
        return MengxueguResult.ok(sysRoleService.deletRole(id));
    }

}
