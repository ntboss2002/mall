package com.zyxx.sys.job;

import com.zyxx.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName TestJob
 * 测试定时任务
 * @Author zxy
 * @Date 2020-07-21 10:58:58
 **/
@Slf4j
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时任务启动：" + DateUtils.getYmdHms());
    }
}
