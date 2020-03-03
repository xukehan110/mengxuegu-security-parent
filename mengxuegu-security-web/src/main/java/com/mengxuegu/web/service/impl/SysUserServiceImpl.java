package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @auther xukehan
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final String PASSWORD_DEFAULT = "$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysUser findByUserName(String username) {
        if (StringUtils.isEmpty(username)){
            return null;
        }
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,username);
        return baseMapper.selectOne(queryWrapper);
    }


    @Override
    public SysUser findByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getMobile,mobile);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }


    @Override
    public IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser) {

        return baseMapper.selectPage(page, sysUser);
    }

    @Override
    public SysUser findById(Long id) {

        if (id == null){
            return new SysUser();
        }

        SysUser sysUser = baseMapper.selectById(id);
        List<SysRole> sysRoles = sysRoleMapper.findByUserId(id);
        sysUser.setRoleList(sysRoles);

        return sysUser;
    }

    @Transactional
    @Override
    public boolean saveOrUpdate(SysUser sysUser) {
        if (sysUser !=null && sysUser.getId() == null){
            sysUser.setPassword(PASSWORD_DEFAULT);
        }

        sysUser.setUpdateDate(new Date());
        boolean flag = super.saveOrUpdate(sysUser);
        if (flag){
            //1.删除用户与角色关联表的数据
            baseMapper.deleteUserRoleByUserId(sysUser.getId());
            //2.将新的用户新的角色加入
            if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())){
                baseMapper.saveUserRoleByUserId(sysUser.getId(),sysUser.getRoleIds());
            }
        }
        return flag;
    }


    @Override
    public boolean deleteUserById(Long id) {
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setEnabled(false);
        sysUser.setUpdateDate(new Date());
        baseMapper.updateById(sysUser);
        return true;
    }
}
