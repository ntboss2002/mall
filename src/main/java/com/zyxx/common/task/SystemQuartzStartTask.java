package com.zyxx.common.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyxx.sys.entity.SysQuartz;
import com.zyxx.sys.service.SysQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目启动任务--启动定时任务
 *
 * @Author zxy
 **/
@Component
@Order(100)
public class SystemQuartzStartTask implements CommandLineRunner {

    @Autowired
    private SysQuartzService sysQuartzService;

    @Override
    public void run(String... args) throws Exception {
        // 查询定时任务
        LambdaQueryWrapper<SysQuartz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysQuartz::getStatus, 0);
        queryWrapper.eq(SysQuartz::getQuartzStatus, 0);
        List<SysQuartz> list = sysQuartzService.list(queryWrapper);
        if (null != list && !list.isEmpty()) {
            for (SysQuartz item : list) {
                // 删除定时任务
                sysQuartzService.schedulerDelete(item.getClassName().trim());
                // 添加定时任务
                sysQuartzService.schedulerAdd(item.getClassName().trim(), item.getCronExpression().trim(), item.getParam());
            }
        }
    }
}
