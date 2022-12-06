package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysDataTable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxy
 * @since 2020-08-11
 */
public interface SysDataTableService extends IService<SysDataTable> {

    /**
     * 分页查询查询所有数据表信息
     */
    LayTableResult list(Integer page, Integer limit, SysDataTable sysDataTable);

    /**
     * 查询所有表格数据，提供给下拉选择
     */
    List<SysDataTable> listSysDataTableForSelect();
}
