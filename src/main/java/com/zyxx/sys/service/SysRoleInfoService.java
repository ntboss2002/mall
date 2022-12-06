package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysRoleInfo;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysRoleInfoService extends IService<SysRoleInfo> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, SysRoleInfo sysRoleInfo);

    /**
     * 新增
     */
    ResponseResult add(SysRoleInfo sysRoleInfo);

    /**
     * 编辑
     */
    ResponseResult update(SysRoleInfo sysRoleInfo);

    /**
     * 删除
     */
    ResponseResult delete(Integer id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Integer id, Integer status);

    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    String listXmSelectPojo(Integer userId);
}
