package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysUser;

/**
 * @auther xukehan
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查找用户信息
     * @param username
     * @return
     */
    SysUser findByUserName(String username);

    /**
     * 通过手机号查询用户信息
     * @param mobile
     * @return
     */
    SysUser findByMobile(String mobile);

    /**
     * 分页查询用户信息
     * @param page
     * @param sysUser
     * @return
     */
    IPage<SysUser> selectPage(Page<SysUser> page,SysUser sysUser);

    /**
     * 通过用户id查询用户信息
     * @param id
     * @return
     */
    SysUser findById(Long id);



    /**
     * 保存用户信息
     * @return
     */
    boolean saveOrUpdate(SysUser sysUser);


    /**
     * 逻辑删除用户信息
     * @param id
     * @return
     */
    boolean deleteUserById(Long id);

}
