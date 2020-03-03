package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entities.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther xukehan
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> page,@Param("sysUser") SysUser sysUser);


    boolean deleteUserRoleByUserId(@Param("userId") Long id);


    boolean saveUserRoleByUserId(@Param("userId") Long id, @Param("perIds") List<Long> perIds);
}
