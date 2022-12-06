package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.redis.RedisConst;
import com.zyxx.common.redis.RedisUtil;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysDict;
import com.zyxx.sys.mapper.SysDictMapper;
import com.zyxx.sys.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public LayTableResult list(Integer page, Integer limit, SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sysDict.getCode())) {
            queryWrapper.like(SysDict::getCode, sysDict.getCode());
        }
        if (StringUtils.isNotBlank(sysDict.getName())) {
            queryWrapper.like(SysDict::getName, sysDict.getName());
        }
        if (null != sysDict.getStatus()) {
            queryWrapper.eq(SysDict::getStatus, sysDict.getStatus());
        }
        queryWrapper.orderByDesc(SysDict::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDict::getCode, sysDict.getCode());
        List<SysDict> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        sysDict.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(sysDict);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysDict::getId, sysDict.getId());
        queryWrapper.eq(SysDict::getCode, sysDict.getCode());
        List<SysDict> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        this.updateById(sysDict);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Integer id) {
        SysDict sysDict = this.getById(id);
        this.removeById(id);
        Set<String> keys = redisUtil.getKeys(RedisConst.Key.SYS_DICT + sysDict.getCode() + "*");
        redisUtil.delBatch(keys);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Integer id, Integer status) {
        SysDict sysDict = this.getById(id);
        sysDict.setStatus(status);
        this.updateById(sysDict);
        return ResponseResult.success();
    }
}
