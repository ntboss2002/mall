package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysQuartz;
import org.quartz.SchedulerException;

/**
 * <p>
 * 定时任务信息表 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-21
 */
public interface SysQuartzService extends IService<SysQuartz> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, SysQuartz sysQuartz);

    /**
     * 新增
     */
    ResponseResult add(SysQuartz sysQuartz);

    /**
     * 编辑
     */
    ResponseResult update(SysQuartz sysQuartz) throws SchedulerException;

    /**
     * 删除
     */
    ResponseResult delete(Long id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Long id, Integer status);

    /**
     * 启/停
     */
    ResponseResult updateQuartzStatus(Long id, Integer quartzStatus) throws SchedulerException;

    /**
     * 添加定时任务
     */
    ResponseResult schedulerAdd(String className, String cronExpression, String param);

    /**
     * 删除定时任务
     */
    ResponseResult schedulerDelete(String className);
}
