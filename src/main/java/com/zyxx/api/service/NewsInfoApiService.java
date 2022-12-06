package com.zyxx.api.service;

import com.zyxx.common.utils.ResponseResult;

/**
 * @ClassName NewsInfoApiService
 * @Description
 * @Author zxy
 * @Date 2020-09-30 11:17:17
 **/
public interface NewsInfoApiService {

    /**
     * 根据类型分页查询新闻资讯
     */
    ResponseResult listNewsInfoByType(Integer page, Integer limit, Integer type);

    /**
     * 获取新闻资讯详情
     */
    ResponseResult getNewsInfoById(Integer id);
}
