package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysUserRole;

import java.util.Set;

/**
 * <p>
 * 用户-角色关系表 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 登录时，根据用户id查询全部角色标识
     */
    Set<String> listUserRoleByUserId(Integer userId);

    /**
     * 授权
     */
    ResponseResult auth(Integer id, String roles);
}
