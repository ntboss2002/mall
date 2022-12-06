package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.AddressUtils;
import com.zyxx.common.utils.IPUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.SpringContextUtils;
import com.zyxx.sys.entity.SysLoginLog;
import com.zyxx.sys.mapper.SysLoginLogMapper;
import com.zyxx.sys.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-28
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public LayTableResult list(Integer page, Integer limit, SysLoginLog sysLoginLog) {
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SysLoginLog::getAccount, SingletonLoginUtils.getSysUserInfo().getAccount());
        if (null != sysLoginLog.getStatus()) {
            queryWrapper.eq(SysLoginLog::getStatus, sysLoginLog.getStatus());
        }
        queryWrapper.orderByDesc(SysLoginLog::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public void save(String account, Integer status, String descript) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setAccount(account);
        try {
            // 获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            // 获取ip地址
            sysLoginLog.setIp(IPUtils.getIpAddress(request));
            sysLoginLog.setLocation(AddressUtils.getAddressByIP(sysLoginLog.getIp()));
        } catch (Exception e) {
            sysLoginLog.setIp("127.0.0.1");
            sysLoginLog.setLocation("局域网，无法获取位置");
        }
        sysLoginLog.setStatus(status);
        sysLoginLog.setDescript(descript);
        this.save(sysLoginLog);
    }
}
