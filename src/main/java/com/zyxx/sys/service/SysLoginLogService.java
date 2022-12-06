package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysLoginLog;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-28
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, SysLoginLog sysLoginLog);

    /**
     * 保存登录日志
     */
    void save(String account, Integer status, String descript);
}
