package com.zyxx.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysRoleInfo;
import com.zyxx.sys.mapper.SysRoleInfoMapper;
import com.zyxx.sys.pojo.XmSelectPojo;
import com.zyxx.sys.service.SysRoleInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
@Service
public class SysRoleInfoServiceImpl extends ServiceImpl<SysRoleInfoMapper, SysRoleInfo> implements SysRoleInfoService {

    @Autowired
    private SysRoleInfoMapper sysRoleInfoMapper;

    @Override
    public LayTableResult list(Integer page, Integer limit, SysRoleInfo sysRoleInfo) {
        LambdaQueryWrapper<SysRoleInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sysRoleInfo.getName())) {
            queryWrapper.like(SysRoleInfo::getName, sysRoleInfo.getName());
        }
        if (StringUtils.isNotBlank(sysRoleInfo.getSign())) {
            queryWrapper.like(SysRoleInfo::getSign, sysRoleInfo.getSign());
        }
        if (null != sysRoleInfo.getStatus()) {
            queryWrapper.eq(SysRoleInfo::getStatus, sysRoleInfo.getStatus());
        }
        queryWrapper.orderByDesc(SysRoleInfo::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysRoleInfo sysRoleInfo) {
        LambdaQueryWrapper<SysRoleInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleInfo::getName, sysRoleInfo.getName()).or().eq(SysRoleInfo::getSign, sysRoleInfo.getSign());
        List<SysRoleInfo> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该角色名称或标识已经存在，请检查后提交");
        }
        sysRoleInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(sysRoleInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysRoleInfo sysRoleInfo) {
        LambdaQueryWrapper<SysRoleInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysRoleInfo::getId, sysRoleInfo.getId());
        queryWrapper.and(i -> i.eq(SysRoleInfo::getName, sysRoleInfo.getName()).or().eq(SysRoleInfo::getSign, sysRoleInfo.getSign()));
        List<SysRoleInfo> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该角色名称或标识已经存在，请检查后提交");
        }
        sysRoleInfo.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(sysRoleInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Integer id) {
        this.removeById(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Integer id, Integer status) {
        SysRoleInfo sysRoleInfo = this.getById(id);
        sysRoleInfo.setStatus(status);
        this.updateById(sysRoleInfo);
        return ResponseResult.success();
    }

    @Override
    public String listXmSelectPojo(Integer userId) {
        JSONObject resJson = new JSONObject();
        List<XmSelectPojo> list = sysRoleInfoMapper.listXmSelectPojo(userId);
        resJson.put("data", list);
        return resJson.toJSONString();
    }
}
