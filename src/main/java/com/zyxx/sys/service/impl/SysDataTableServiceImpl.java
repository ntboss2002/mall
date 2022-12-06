package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysDataTable;
import com.zyxx.sys.mapper.SysDataTableMapper;
import com.zyxx.sys.service.SysDataTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-08-11
 */
@Service
public class SysDataTableServiceImpl extends ServiceImpl<SysDataTableMapper, SysDataTable> implements SysDataTableService {

    @Value("${spring.datasource.url}")
    private String database;

    @Override
    public LayTableResult list(Integer page, Integer limit, SysDataTable sysDataTable) {
        LambdaQueryWrapper<SysDataTable> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(database)) {
            queryWrapper.eq(SysDataTable::getTableSchema, database.substring(database.indexOf("3306/") + 5, database.indexOf("?")));
        }
        if (StringUtils.isNotBlank(sysDataTable.getTableName())) {
            queryWrapper.like(SysDataTable::getTableName, sysDataTable.getTableName());
        }
        if (StringUtils.isNotBlank(sysDataTable.getTableComment())) {
            queryWrapper.like(SysDataTable::getTableComment, sysDataTable.getTableComment());
        }
        queryWrapper.orderByDesc(SysDataTable::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public List<SysDataTable> listSysDataTableForSelect() {
        LambdaQueryWrapper<SysDataTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysDataTable::getTableName);
        if (StringUtils.isNotBlank(database)) {
            queryWrapper.eq(SysDataTable::getTableSchema, database.substring(database.indexOf("3306/") + 5, database.indexOf("?")));
        }
        return this.list(queryWrapper);
    }
}
