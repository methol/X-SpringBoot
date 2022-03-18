package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysRoleMenu;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 * @author czx
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

}
