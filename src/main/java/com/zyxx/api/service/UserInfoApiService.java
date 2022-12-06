package com.zyxx.api.service;

import com.zyxx.common.utils.ResponseResult;

/**
 * @ClassName UserInfoApiService
 * @Description
 * @Author zxy
 * @Date 2020-09-28 18:01:01
 **/
public interface UserInfoApiService {

    /**
     * 执行登录
     */
    ResponseResult doLogin(String account, String password);

    /**
     * 用户注册
     */
    ResponseResult doRegister(String phone, String password);

    /**
     * 获取用户信息
     */
    ResponseResult getUserInfoById(Integer id);

    /**
     * 修改用户信息
     */
    ResponseResult updateUserInfoById(Integer id, String avatar, String name, Integer sex);

    /**
     * 获取我的意见反馈
     */
    ResponseResult listFeedBackInfo(Integer id);

    /**
     * 意见反馈详情
     */
    ResponseResult getFeedBackInfoById(Integer id);

    /**
     * 获取我的意见反馈条数
     */
    ResponseResult countFeedBackInfo(Integer id);

    /**
     * 保存我的意见反馈
     */
    ResponseResult saveFeedBackInfo(Integer createUser, Integer type, String content);
}
