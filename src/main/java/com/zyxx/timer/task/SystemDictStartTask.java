package com.zyxx.timer.task;

import com.zyxx.common.redis.RedisConst;
import com.zyxx.common.redis.RedisUtil;
import com.zyxx.sys.entity.SysDictDetail;
import com.zyxx.sys.mapper.SysDictDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目启动任务--将数据字典存入Redis
 *
 * @Author zxy
 **/
// @Component
@Order(200)
public class SystemDictStartTask implements CommandLineRunner {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        List<SysDictDetail> list = sysDictDetailMapper.listSysDictDetailOnSystemStart();
        if (null != list && !list.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            list.forEach(i -> {
                map.put(RedisConst.Key.SYS_DICT + i.getDictCode() + ":" + i.getCode(), i.getName());
            });
            // 批量存入
            redisUtil.mset(map);
        }
    }
}
