package com.suke.czx.modules.sys.service;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author czx
 */
public interface PermissionsService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);
}
