package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysDataTableColumn;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxy
 * @since 2020-08-11
 */
public interface SysDataTableColumnService extends IService<SysDataTableColumn> {

    /**
     * 分页查询查询所有数据表信息
     */
    LayTableResult list(Integer page, Integer limit, SysDataTableColumn sysDataTableColumn);
}
