package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysRole;

import java.util.List;


/**
 * 角色
 *
 * @author czx
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    void deleteBath(Long[] ids);

    void saveRoleMenu(SysRole role);

    void updateRoleMenu(SysRole role);

}
