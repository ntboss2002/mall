package com.zyxx.common.utils;

/**
 * 关于请求处理的工具类
 *
 * @Author zxy
 **/
public class ServletUtils {

    /**
     * 重定向至地址 url
     */
    public static String redirectTo(String url) {
        StringBuilder rto = new StringBuilder("redirect:");
        rto.append(url);
        return rto.toString();
    }
}
