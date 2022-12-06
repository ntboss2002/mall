package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.consts.CacheConst;
import com.zyxx.common.redis.RedisConst;
import com.zyxx.common.redis.RedisUtil;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysDictDetail;
import com.zyxx.sys.mapper.SysDictDetailMapper;
import com.zyxx.sys.service.SysDictDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 */
@Service
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements SysDictDetailService {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public LayTableResult list(String dictCode) {
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictDetail::getDictCode, dictCode);
        queryWrapper.orderByAsc(SysDictDetail::getCode);
        List<SysDictDetail> list = this.list(queryWrapper);
        return new LayTableResult<>(null != list && !list.isEmpty() ? list.size() : 0L, list);
    }

    @Override
    public ResponseResult add(SysDictDetail sysDictDetail) {
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictDetail::getDictCode, sysDictDetail.getDictCode());
        queryWrapper.eq(SysDictDetail::getCode, sysDictDetail.getCode());
        List<SysDictDetail> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        sysDictDetail.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(sysDictDetail);
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_DICT + sysDictDetail.getDictCode() + ":" + sysDictDetail.getCode(), sysDictDetail.getName());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysDictDetail sysDictDetail) {
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysDictDetail::getId, sysDictDetail.getId());
        queryWrapper.eq(SysDictDetail::getDictCode, sysDictDetail.getDictCode());
        queryWrapper.eq(SysDictDetail::getCode, sysDictDetail.getCode());
        List<SysDictDetail> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        sysDictDetail.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(sysDictDetail);
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_DICT + sysDictDetail.getDictCode() + ":" + sysDictDetail.getCode(), sysDictDetail.getName());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Long id) {
        SysDictDetail sysDictDetail = sysDictDetailMapper.selectById(id);
        this.removeById(id);
        // 删除redis
        redisUtil.del(RedisConst.Key.SYS_DICT + sysDictDetail.getDictCode() + ":" + sysDictDetail.getCode());
        return ResponseResult.success();
    }

    @Override
    @Cacheable(value = CacheConst.SYS_DICT_CACHE, key = "#dictCode+':'+#code")
    public String getDictDataByTypeAndValue(String dictCode, String code) {
        // 先从 Redis里面获取
        Object object = redisUtil.get(RedisConst.Key.SYS_DICT + dictCode + ":" + code);
        if (null != object) {
            return String.valueOf(object);
        }
        // 再从数据库获取
        String dictText = sysDictDetailMapper.getSysDictDetail(dictCode, code);
        // 存入 Redis
        if (StringUtils.isNotBlank(dictText)) {
            redisUtil.set(RedisConst.Key.SYS_DICT + dictCode + ":" + code, dictText);
        }
        return dictText;
    }

    @Override
    public List<SysDictDetail> listSysDictDetailByDictCode(String dictCode) {
        QueryWrapper<SysDictDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysDictDetail::getCode, SysDictDetail::getName);
        queryWrapper.lambda().eq(SysDictDetail::getDictCode, dictCode);
        return this.list(queryWrapper);
    }
}
