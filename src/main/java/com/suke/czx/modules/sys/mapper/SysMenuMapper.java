package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysMenu;

import java.util.List;

/**
 * 菜单管理
 *
 * @author czx
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 查询用户的权限列表
     */
    List<SysMenu> queryUserList(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);
}
