package com.zyxx.sys.service;

import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.pojo.RedisPojo;

/**
 * @ClassName SysRedisService
 * @Description
 * @Author zxy
 * @Date 2020-07-31 15:58:58
 **/
public interface SysRedisService {

    /**
     * 查询所有key
     */
    LayTableResult list(String perfix);

    /**
     * 新增 key
     * 修改 key
     */
    ResponseResult add(RedisPojo redisPojo);

    /**
     * 根据 key 删除数据
     */
    ResponseResult delete(String key);
}
