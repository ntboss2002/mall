package com.zyxx.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyxx.sys.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 角色-权限关系表 Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 登录时，根据用户id查询所有的权限标识
     */
    Set<String> listRolePermissionByUserId(@Param("userId") Integer userId);
}
