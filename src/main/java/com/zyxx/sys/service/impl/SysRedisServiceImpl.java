package com.zyxx.sys.service.impl;

import com.zyxx.common.redis.RedisUtil;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.pojo.RedisPojo;
import com.zyxx.sys.service.SysRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SysRedisServiceImpl
 * @Description
 * @Author zxy
 * @Date 2020-07-31 15:58:58
 **/
@Service
public class SysRedisServiceImpl implements SysRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public LayTableResult list(String perfix) {
        Set<String> data = redisUtil.getKeys(perfix + "*");
        List<RedisPojo> list = new ArrayList<>();
        if (null != data && !data.isEmpty()) {
            RedisPojo redisPojo = null;
            for (String item : data) {
                redisPojo = new RedisPojo();
                redisPojo.setKey(item);
                redisPojo.setValue(String.valueOf(redisUtil.get(item)));
                list.add(redisPojo);
            }
        }
        return new LayTableResult<>(Long.parseLong("" + list.size()), list);
    }

    @Override
    public ResponseResult add(RedisPojo redisPojo) {
        if (StringUtils.isBlank(redisPojo.getKey())) {
            return ResponseResult.error("请输入键（key）");
        }
        if (StringUtils.isBlank(redisPojo.getValue())) {
            return ResponseResult.error("请输入值（value）");
        }
        redisUtil.set(redisPojo.getKey(), redisPojo.getValue());
        if (null != redisPojo.getExpire() && -1 != redisPojo.getExpire()) {
            redisUtil.setExpire(redisPojo.getKey(), redisPojo.getExpire());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(String key) {
        if (StringUtils.isBlank(key)) {
            return ResponseResult.error("请输入键（key）");
        }
        String[] keyArr = key.split(",");
        List<String> list = Arrays.asList(keyArr);
        redisUtil.delBatch(list);
        return ResponseResult.success();
    }
}
