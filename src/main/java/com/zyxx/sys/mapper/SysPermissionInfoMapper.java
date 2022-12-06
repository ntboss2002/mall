package com.zyxx.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyxx.sys.entity.SysPermissionInfo;
import com.zyxx.sys.pojo.LayuiTreePojo;
import com.zyxx.sys.pojo.MenuPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysPermissionInfoMapper extends BaseMapper<SysPermissionInfo> {

    /**
     * 根据用户id，查询用户的菜单权限信息
     */
    List<MenuPojo> listPermissionInfoByUserId(@Param("id") Integer id);

    /**
     * 角色赋权，tree组件
     */
    List<LayuiTreePojo> listPermissionForTree(@Param("id") Integer id);
}
