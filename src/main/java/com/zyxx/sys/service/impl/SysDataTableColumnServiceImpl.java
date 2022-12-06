package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysDataTableColumn;
import com.zyxx.sys.mapper.SysDataTableColumnMapper;
import com.zyxx.sys.service.SysDataTableColumnService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-08-11
 */
@Service
public class SysDataTableColumnServiceImpl extends ServiceImpl<SysDataTableColumnMapper, SysDataTableColumn> implements SysDataTableColumnService {

    @Value("${spring.datasource.url}")
    private String database;

    @Override
    public LayTableResult list(Integer page, Integer limit, SysDataTableColumn sysDataTableColumn) {
        LambdaQueryWrapper<SysDataTableColumn> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(database)) {
            queryWrapper.eq(SysDataTableColumn::getTableSchema, database.substring(database.indexOf("3306/") + 5, database.indexOf("?")));
        }
        if (StringUtils.isNotBlank(sysDataTableColumn.getTableName())) {
            queryWrapper.like(SysDataTableColumn::getTableName, sysDataTableColumn.getTableName());
        }
        if (StringUtils.isNotBlank(sysDataTableColumn.getColumnComment())) {
            queryWrapper.like(SysDataTableColumn::getColumnComment, sysDataTableColumn.getColumnComment());
        }
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }
}
