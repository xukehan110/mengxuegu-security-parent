package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther xukehan
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if (userId == null){
            return null;
        }
        List<SysPermission> sysPermissions = baseMapper.selectPermissionByUserId(userId);
        //如果没有权限，将集合中的null数据移除
        sysPermissions.remove(null);
        return sysPermissions;
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        //1.删除当前id权限
        baseMapper.deleteById(id);
        //2.删除parentId = id的子权限
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPermission::getParentId,id);
        baseMapper.delete(queryWrapper);
        return true;
    }
}
