package com.zyxx.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.api.service.UserInfoApiService;
import com.zyxx.business.entity.FeedBackInfo;
import com.zyxx.business.mapper.FeedBackInfoMapper;
import com.zyxx.business.vo.FeedBackInfoVO;
import com.zyxx.common.utils.PasswordUtils;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysUserInfo;
import com.zyxx.sys.service.SysUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserInfoApiServiceImpl
 * @Description
 * @Author zxy
 * @Date 2020-09-28 18:01:01
 **/
@Service
public class UserInfoApiServiceImpl implements UserInfoApiService {

    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private FeedBackInfoMapper feedBackInfoMapper;


    @Override
    public ResponseResult doLogin(String account, String password) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return ResponseResult.error("请输入账户名密码");
        }
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserInfo::getAccount, account);
        SysUserInfo sysUserInfo = sysUserInfoService.getOne(queryWrapper, false);
        if (null == sysUserInfo) {
            return ResponseResult.error("账号不存在");
        }
        if (1 == sysUserInfo.getStatus()) {
            return ResponseResult.error("账号已被冻结");
        }
        if (!PasswordUtils.getPassword(password, account).equals(sysUserInfo.getPassword())) {
            return ResponseResult.error("密码不正确");
        }
        return ResponseResult.success(sysUserInfo);
    }

    @Override
    public ResponseResult doRegister(String phone, String password) {
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
            return ResponseResult.error("请输入手机号密码");
        }
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(i -> i.eq(SysUserInfo::getAccount, phone).or().eq(SysUserInfo::getPhone, phone));
        SysUserInfo dbUserInfo = sysUserInfoService.getOne(queryWrapper, false);
        if (null != dbUserInfo) {
            return ResponseResult.error("该手机号已被注册");
        }
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setAccount(phone);
        sysUserInfo.setPhone(phone);
        sysUserInfo.setPassword(PasswordUtils.getPassword(password, phone));
        sysUserInfo.setStatus(0);
        sysUserInfoService.save(sysUserInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult getUserInfoById(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("用户信息错误");
        }
        return ResponseResult.success(sysUserInfoService.getById(id));
    }

    @Override
    public ResponseResult updateUserInfoById(Integer id, String avatar, String name, Integer sex) {
        if (null == id || 0 == id) {
            return ResponseResult.error("用户信息错误");
        }
        SysUserInfo sysUserInfo = sysUserInfoService.getById(id);
        if (null == sysUserInfo || 1 == sysUserInfo.getStatus()) {
            return ResponseResult.error("用户信息错误");
        }
        sysUserInfo.setAvatar(avatar);
        sysUserInfo.setName(name);
        sysUserInfo.setSex(sex);
        sysUserInfoService.updateById(sysUserInfo);
        return ResponseResult.success(sysUserInfo);
    }

    @Override
    public ResponseResult listFeedBackInfo(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("用户信息错误");
        }
        QueryWrapper<FeedBackInfoVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.del_flag", 0);
        queryWrapper.eq("a.create_user", id);
        queryWrapper.eq("b.dict_code", "feed_back_type");
        queryWrapper.orderByDesc("a.create_time");
        return ResponseResult.success(feedBackInfoMapper.listFeedBackInfoVOApi(new Page<>(1, Integer.MAX_VALUE), queryWrapper).getRecords());
    }

    @Override
    public ResponseResult getFeedBackInfoById(Integer id) {
        QueryWrapper<FeedBackInfoVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.id", id);
        queryWrapper.eq("b.dict_code", "feed_back_type");
        return ResponseResult.success(feedBackInfoMapper.listFeedBackInfoVOApi(new Page<>(1, 1), queryWrapper).getRecords());
    }

    @Override
    public ResponseResult countFeedBackInfo(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("用户信息错误");
        }
        LambdaQueryWrapper<FeedBackInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FeedBackInfo::getCreateUser, id);
        queryWrapper.orderByDesc(FeedBackInfo::getCreateTime);
        return ResponseResult.success(feedBackInfoMapper.selectCount(queryWrapper));
    }

    @Override
    public ResponseResult saveFeedBackInfo(Integer createUser, Integer type, String content) {
        if (null == createUser || 0 == createUser) {
            return ResponseResult.error("用户信息错误");
        }
        FeedBackInfo feedBackInfo = new FeedBackInfo();
        feedBackInfo.setType(type);
        feedBackInfo.setContent(content);
        feedBackInfo.setCreateUser(createUser);
        feedBackInfoMapper.insert(feedBackInfo);
        return ResponseResult.success();
    }
}
