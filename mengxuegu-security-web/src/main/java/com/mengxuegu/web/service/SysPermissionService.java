package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysPermission;

import java.util.List;

/**
 * @auther xukehan
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 通过用户id查询用户拥有的权限
     * @param userId
     * @return
     */
    List<SysPermission> findByUserId(Long userId);

    /**
     * 通过id删除权限资源
     * @param id
     * @return
     */
    Boolean deleteById(Long id);


}
