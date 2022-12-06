package com.zyxx.sys.mapper;

import com.zyxx.sys.entity.SysRoleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyxx.sys.pojo.XmSelectPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysRoleInfoMapper extends BaseMapper<SysRoleInfo> {

    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    List<XmSelectPojo> listXmSelectPojo(@Param("userId") Integer userId);
}
