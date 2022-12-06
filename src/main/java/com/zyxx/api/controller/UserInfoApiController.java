package com.zyxx.api.controller;

import com.zyxx.api.service.UserInfoApiService;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.service.SysDictDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 *
 * @Author zxy
 * @Date 2020-09-25 10:28:28
 **/
@Api(tags = "用户相关")
@RestController
@RequestMapping("api/userInfo")
public class UserInfoApiController {

    @Autowired
    private UserInfoApiService userInfoApiService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("doRegister")
    public ResponseResult doRegister(String phone, String password) {
        return userInfoApiService.doRegister(phone.trim(), password.trim());
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("doLogin")
    public ResponseResult doLogin(String account, String password) {
        return userInfoApiService.doLogin(account.trim(), password.trim());
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "id", required = true))
    @PostMapping("getUserInfoById")
    public ResponseResult getUserInfoById(Integer id) {
        return userInfoApiService.getUserInfoById(id);
    }

    @ApiOperation(value = "编辑用户信息", notes = "编辑用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像", required = true),
            @ApiImplicitParam(name = "name", value = "姓名", required = true),
            @ApiImplicitParam(name = "sex", value = "性别", required = true),
    })
    @PostMapping("updateUserInfoById")
    public ResponseResult updateUserInfoById(Integer id, String avatar, String name, Integer sex) {
        return userInfoApiService.updateUserInfoById(id, avatar, name, sex);
    }

    @ApiOperation(value = "我的意见反馈条数", notes = "我的意见反馈条数")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @PostMapping("countFeedBackInfo")
    public ResponseResult countFeedBackInfo(Integer id) {
        return userInfoApiService.countFeedBackInfo(id);
    }

    @ApiOperation(value = "意见反馈类型", notes = "意见反馈类型")
    @GetMapping("listFeedBackType")
    public ResponseResult listFeedBackType() {
        return ResponseResult.success(sysDictDetailService.listSysDictDetailByDictCode("feed_back_type"));
    }

    @ApiOperation(value = "我的意见反馈", notes = "我的意见反馈")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @PostMapping("listFeedBackInfo")
    public ResponseResult listFeedBackInfo(Integer id) {
        return userInfoApiService.listFeedBackInfo(id);
    }

    @ApiOperation(value = "意见反馈详情", notes = "意见反馈详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "意见反馈id", required = true))
    @PostMapping("getFeedBackInfoById")
    public ResponseResult getFeedBackInfoById(Integer id) {
        return userInfoApiService.getFeedBackInfoById(id);
    }

    @ApiOperation(value = "保存意见反馈", notes = "保存意见反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createUser", value = "用户id", required = true),
            @ApiImplicitParam(name = "type", value = "类型(0-建议1-投诉2-鼓励)", required = true),
            @ApiImplicitParam(name = "content", value = "内容", required = true)
    })
    @PostMapping("saveFeedBackInfo")
    public ResponseResult saveFeedBackInfo(Integer createUser, Integer type, String content) {
        return userInfoApiService.saveFeedBackInfo(createUser, type, content);
    }
}
