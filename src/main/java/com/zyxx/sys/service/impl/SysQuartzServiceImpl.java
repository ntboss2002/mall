package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysQuartz;
import com.zyxx.sys.mapper.SysQuartzMapper;
import com.zyxx.sys.service.SysQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 定时任务信息表 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-21
 */
@Slf4j
@Service
public class SysQuartzServiceImpl extends ServiceImpl<SysQuartzMapper, SysQuartz> implements SysQuartzService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public LayTableResult list(Integer page, Integer limit, SysQuartz sysQuartz) {
        LambdaQueryWrapper<SysQuartz> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sysQuartz.getClassName())) {
            queryWrapper.like(SysQuartz::getClassName, sysQuartz.getClassName());
        }
        if (null != sysQuartz.getQuartzStatus()) {
            queryWrapper.eq(SysQuartz::getQuartzStatus, sysQuartz.getQuartzStatus());
        }
        queryWrapper.orderByDesc(SysQuartz::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysQuartz sysQuartz) {
        if (!CronExpression.isValidExpression(sysQuartz.getCronExpression())) {
            return ResponseResult.error("请输入正确的cron表达式");
        }
        LambdaQueryWrapper<SysQuartz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysQuartz::getClassName, sysQuartz.getClassName());
        List<SysQuartz> sysQuartzList = this.list(queryWrapper);
        if (null != sysQuartzList && !sysQuartzList.isEmpty()) {
            return ResponseResult.error("该任务类名已经存在");
        }
        // 启动
        if (0 == sysQuartz.getQuartzStatus()) {
            ResponseResult res = this.schedulerAdd(sysQuartz.getClassName().trim(), sysQuartz.getCronExpression().trim(), sysQuartz.getParam());
            if (500 == res.getCode()) {
                return res;
            }
        }
        sysQuartz.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(sysQuartz);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysQuartz sysQuartz) throws SchedulerException {
        if (!CronExpression.isValidExpression(sysQuartz.getCronExpression())) {
            return ResponseResult.error("请输入正确的cron表达式");
        }
        LambdaQueryWrapper<SysQuartz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysQuartz::getId, sysQuartz.getId());
        queryWrapper.eq(SysQuartz::getClassName, sysQuartz.getClassName());
        SysQuartz dbObj = this.getOne(queryWrapper, false);
        if (null != dbObj) {
            return ResponseResult.error("该任务类名已经存在");
        }
        // 启动
        if (0 == sysQuartz.getQuartzStatus()) {
            // 删除定时任务
            ResponseResult res = this.schedulerDelete(sysQuartz.getClassName().trim());
            if (500 == res.getCode()) {
                return res;
            }
            // 添加定时任务
            res = this.schedulerAdd(sysQuartz.getClassName().trim(), sysQuartz.getCronExpression().trim(), sysQuartz.getParam());
            if (500 == res.getCode()) {
                return res;
            }
        }
        // 停止
        else if (1 == sysQuartz.getQuartzStatus()) {
            scheduler.pauseJob(JobKey.jobKey(sysQuartz.getClassName().trim()));
        }
        sysQuartz.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(sysQuartz);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Long id) {
        this.removeById(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Long id, Integer status) {
        SysQuartz sysQuartz = this.getById(id);
        if (1 == status && 0 == sysQuartz.getQuartzStatus()) {
            return ResponseResult.error("请先停止该任务");
        }
        this.updateById(sysQuartz);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateQuartzStatus(Long id, Integer quartzStatus) throws SchedulerException {
        SysQuartz sysQuartz = this.getById(id);
        // 启动
        if (0 == quartzStatus) {
            // 删除定时任务
            ResponseResult res = this.schedulerDelete(sysQuartz.getClassName().trim());
            if (500 == res.getCode()) {
                return res;
            }
            // 添加定时任务
            res = this.schedulerAdd(sysQuartz.getClassName().trim(), sysQuartz.getCronExpression().trim(), sysQuartz.getParam());
            if (500 == res.getCode()) {
                return res;
            }
        }
        // 停止
        else if (1 == quartzStatus) {
            scheduler.pauseJob(JobKey.jobKey(sysQuartz.getClassName().trim()));
        }
        // 更改启动状态
        sysQuartz.setQuartzStatus(quartzStatus);
        this.updateById(sysQuartz);
        return ResponseResult.success();
    }

    /**
     * 添加定时任务
     *
     * @param className
     * @param cronExpression
     * @param param
     */
    @Override
    public ResponseResult schedulerAdd(String className, String cronExpression, String param) {
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(className).getClass()).withIdentity(className).usingJobData("param", param).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return ResponseResult.success();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseResult.error("定时任务创建失败，请检查任务类名");
    }

    /**
     * 删除定时任务
     *
     * @param className
     */
    @Override
    public ResponseResult schedulerDelete(String className) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(className));
            scheduler.unscheduleJob(TriggerKey.triggerKey(className));
            scheduler.deleteJob(JobKey.jobKey(className));
            return ResponseResult.success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseResult.error("定时任务删除失败，请检查任务类名");
    }

    private static Job getClass(String className) throws Exception {
        Class<?> class1 = Class.forName(className);
        return (Job) class1.newInstance();
    }
}
