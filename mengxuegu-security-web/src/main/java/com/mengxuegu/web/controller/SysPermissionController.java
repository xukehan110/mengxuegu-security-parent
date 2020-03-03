package com.mengxuegu.web.controller;

import com.megxuegu.base.result.MengxueguResult;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 * @auther xukehan
 */
@Controller
@RequestMapping("/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    private static final String HTML_PREFIX = "system/permission/";

    @PreAuthorize("hasAuthority('sys:permission')")
    @GetMapping(value = {"/",""}) //  /permission/ 或者  /permission
    public String permission(){
        return HTML_PREFIX + "permission-list";
    }

    @PreAuthorize("hasAuthority('sys:permission:list')")
    @PostMapping("/list")
    @ResponseBody
    public MengxueguResult list(){
        List<SysPermission> list = sysPermissionService.list();
        return MengxueguResult.ok(list);
    }

    /**
     * 新增或者修改操作跳转页面
     * @PathVariable(required = false) 设置为false，则id可传入也可不传入
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:edit','sys:permission:add')")
    @GetMapping(value = {"/form","/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model){
        System.out.println("id:"+id);

        SysPermission permission = sysPermissionService.getById(id) == null ? new SysPermission() : sysPermissionService.getById(id);
        model.addAttribute("permission",permission);
        return HTML_PREFIX+"permission-form";
    }

    @PreAuthorize("hasAnyAuthority('sys:permission:edit','sys:permission:add')")
    @RequestMapping(value = "",method = {RequestMethod.PUT,RequestMethod.POST})
    public String saveOrUpdata(SysPermission permission){
        sysPermissionService.saveOrUpdate(permission);
        return "redirect:/permission";
    }

    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long id){
        sysPermissionService.deleteById(id);
        return MengxueguResult.ok();
    }


}
