package com.zyxx.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyxx.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 用户-角色关系表 Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id，查询多个角色标识
     */
    Set<String> listUserRoleByUserId(@Param("userId") Integer userId);
}
