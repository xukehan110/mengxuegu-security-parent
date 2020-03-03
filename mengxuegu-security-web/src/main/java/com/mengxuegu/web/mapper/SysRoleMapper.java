package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entities.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther xukehan
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 第一个参数必须是page,第二个参数是查询参数
     * @param page
     * @param sysRole
     * @return
     */
   IPage<SysRole> selectPage(Page<SysRole> page,@Param("p") SysRole sysRole);

    /**
     * 通过角色id删除角色关系表中角色与权限的关系
     * @param roleId
     * @return
     */
   Boolean deleteRolePermissionByRoleId(@Param("roleId") Long roleId);

    /**
     * 保存角色拥有的权限
     * @param roleId
     * @param perIds
     * @return
     */
   Boolean saveRolePermissionByRoleId(@Param("roleId") Long roleId,@Param("perIds") List<Long> perIds);


    /**
     * 通过用户id查询角色信息
     * @param id
     * @return
     */
   List<SysRole> findByUserId(@Param("userId") Long id);


}
