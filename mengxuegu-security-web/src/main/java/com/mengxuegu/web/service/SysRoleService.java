package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysRole;

/**
 * @auther xukehan
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 模糊分页查询角色列表
     * @param page   分页对象
     * @param sysRole 条件值
     * @return
     */
    IPage<SysRole> selectPage(Page<SysRole> page,SysRole sysRole);

    /**
     * 通过id查询角色和所拥有的的权限信息
     * @param id
     * @return
     */
    SysRole findById(Long id);

    /**
     * 保存角色拥有的权限
     * @return
     */
    boolean saveOrUpdate(SysRole sysRole);

    /**
     * 删除角色
     * @param id
     * @return
     */
    boolean deletRole(Long id);

}
