package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysUser;

import java.util.List;

/**
 * 系统用户
 *
 * @author czx
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);
}
