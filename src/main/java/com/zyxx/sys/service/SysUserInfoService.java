package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysUserInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-06
 */
public interface SysUserInfoService extends IService<SysUserInfo> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, SysUserInfo sysUserInfo);

    /**
     * 新增
     */
    ResponseResult add(SysUserInfo sysUserInfo);

    /**
     * 编辑
     */
    ResponseResult update(SysUserInfo sysUserInfo);

    /**
     * 编辑个人资料
     */
    ResponseResult info(SysUserInfo sysUserInfo);

    /**
     * 删除
     */
    ResponseResult delete(Long id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Long id, Integer status);

    /**
     * 修改密码
     */
    ResponseResult updatePassword(String oldPassword, String password);

    /**
     * 修改密码
     */
    ResponseResult updatePassword(Integer id, String password);
}
