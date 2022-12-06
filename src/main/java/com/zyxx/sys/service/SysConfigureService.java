package com.zyxx.sys.service;

import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysConfigure;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统设置 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-10-20
 */
public interface SysConfigureService extends IService<SysConfigure> {

    /**
     * 获取系统配置，没有则新增
     */
    SysConfigure saveSysConfigure();

    /**
     * 修改系统设置
     */
    ResponseResult update(SysConfigure sysConfigure);
}
