package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysRolePermission;

import java.util.Set;

/**
 * <p>
 * 角色-权限关系表 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 角色授权
     */
    ResponseResult auth(Integer id, String perms);

    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    Set<String> listRolePermissionByUserId(Integer userId);
}
