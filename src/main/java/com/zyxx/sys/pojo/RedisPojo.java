package com.zyxx.sys.pojo;

import lombok.Data;

/**
 * @ClassName RedisPojo
 * 页面管理redis对象
 * @Author zxy
 * @Date 2020-07-31 16:13:13
 **/
@Data
public class RedisPojo {

    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;
    /**
     * 过期时间
     */
    private Long expire;
}
