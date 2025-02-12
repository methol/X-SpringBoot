package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysRole;

import java.util.List;

/**
 * 角色管理
 *
 * @author czx
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

}
