package com.zyxx.sys.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysPermissionInfo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
public interface SysPermissionInfoService extends IService<SysPermissionInfo> {

    /**
     * 查询列表
     */
    List<SysPermissionInfo> list();

    /**
     * 新增
     */
    ResponseResult add(JSONObject param);

    /**
     * 编辑
     */
    ResponseResult update(SysPermissionInfo sysPermissionInfo);

    /**
     * 删除
     */
    ResponseResult delete(Integer id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Integer id, Integer status);

    /**
     * 排序
     */
    ResponseResult updateSort(Integer id, Integer sort);

    /**
     * 根据类型获取权限菜单数据
     */
    ResponseResult listPermissionInfoByPid(Integer type, Integer pid);

    /**
     * 初始化菜单数据
     */
    String initMenu(HttpSession session);

    /**
     * 角色赋权，tree组件
     */
    String listPermissionForTree(Integer roleId);
}
