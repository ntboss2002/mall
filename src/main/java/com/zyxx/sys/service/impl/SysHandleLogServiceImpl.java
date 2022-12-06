package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysHandleLog;
import com.zyxx.sys.mapper.SysHandleLogMapper;
import com.zyxx.sys.service.SysHandleLogService;
import com.zyxx.sys.vo.SysHandleLogVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-08-31
 */
@Service
public class SysHandleLogServiceImpl extends ServiceImpl<SysHandleLogMapper, SysHandleLog> implements SysHandleLogService {

    @Autowired
    private SysHandleLogMapper sysHandleLogMapper;

    @Override
    public LayTableResult list(Integer page, Integer limit, SysHandleLogVO sysHandleLog) {
        QueryWrapper<SysHandleLogVO> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sysHandleLog.getCreateUserName())) {
            queryWrapper.like("b.name", sysHandleLog.getCreateUserName());
        }
        queryWrapper.orderByDesc("a.create_time");
        return new LayTableResult<>(sysHandleLogMapper.listSysHandleLogVO(new Page<>(page, limit), queryWrapper));
    }
}
