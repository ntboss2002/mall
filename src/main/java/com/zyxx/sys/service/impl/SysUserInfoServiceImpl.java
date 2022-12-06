package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.PasswordUtils;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysUserInfo;
import com.zyxx.sys.mapper.SysUserInfoMapper;
import com.zyxx.sys.service.SysUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-06
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo> implements SysUserInfoService {

    @Override
    public LayTableResult list(Integer page, Integer limit, SysUserInfo sysUserInfo) {
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sysUserInfo.getName())) {
            queryWrapper.like(SysUserInfo::getName, sysUserInfo.getName());
        }
        if (StringUtils.isNotBlank(sysUserInfo.getAccount())) {
            queryWrapper.like(SysUserInfo::getAccount, sysUserInfo.getAccount());
        }
        if (StringUtils.isNotBlank(sysUserInfo.getPhone())) {
            queryWrapper.like(SysUserInfo::getPhone, sysUserInfo.getPhone());
        }
        if (null != sysUserInfo.getSex()) {
            queryWrapper.eq(SysUserInfo::getSex, sysUserInfo.getSex());
        }
        if (null != sysUserInfo.getStatus()) {
            queryWrapper.eq(SysUserInfo::getStatus, sysUserInfo.getStatus());
        }
        queryWrapper.orderByDesc(SysUserInfo::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysUserInfo sysUserInfo) {
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserInfo::getAccount, sysUserInfo.getAccount());
        List<SysUserInfo> userInfoList = this.list(queryWrapper);
        if (null != userInfoList && !userInfoList.isEmpty()) {
            return ResponseResult.error("该账户已经存在，请检查后提交");
        }
        String password = PasswordUtils.getPassword("123456", sysUserInfo.getAccount());
        sysUserInfo.setPassword(password);
        sysUserInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(sysUserInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysUserInfo sysUserInfo) {
        sysUserInfo.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(sysUserInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult info(SysUserInfo sysUserInfo) {
        this.updateById(sysUserInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Long id) {
        this.removeById(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Long id, Integer status) {
        SysUserInfo userInfo = this.getById(id);
        userInfo.setStatus(status);
        this.updateById(userInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePassword(String oldPassword, String password) {
        if (oldPassword.equals(password)) {
            return ResponseResult.error("新密码不能与旧密码相同");
        }
        // 获取登录用户
        SysUserInfo sysUserInfo = this.getById(SingletonLoginUtils.getSysUserId());
        oldPassword = PasswordUtils.getPassword(oldPassword, sysUserInfo.getAccount());
        if (!oldPassword.equals(sysUserInfo.getPassword())) {
            return ResponseResult.error("旧密码不正确");
        }
        password = PasswordUtils.getPassword(password, sysUserInfo.getAccount());
        sysUserInfo.setPassword(password);
        this.updateById(sysUserInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePassword(Integer id, String password) {
        if (null == id || 0 == id) {
            return ResponseResult.error("请选择需要操作的用户");
        }
        if (StringUtils.isBlank(password)) {
            return ResponseResult.error("请输入密码");
        }
        SysUserInfo sysUserInfo = this.getById(id);
        if (null == sysUserInfo) {
            return ResponseResult.error("用户信息错误");
        }
        sysUserInfo.setPassword(PasswordUtils.getPassword(password, sysUserInfo.getAccount()));
        this.updateById(sysUserInfo);
        return ResponseResult.success();
    }
}
