package com.zyxx.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.redis.RedisConst;
import com.zyxx.common.redis.RedisUtil;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysConfigure;
import com.zyxx.sys.mapper.SysConfigureMapper;
import com.zyxx.sys.service.SysConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统设置 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-10-20
 */
@Service
public class SysConfigureServiceImpl extends ServiceImpl<SysConfigureMapper, SysConfigure> implements SysConfigureService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysConfigure saveSysConfigure() {
        Object object = redisUtil.get(RedisConst.Key.SYS_CONFIGURE);
        if (null != object) {
            return (SysConfigure) object;
        }
        SysConfigure sysConfigure = this.getOne(null, false);
        if (null == sysConfigure) {
            sysConfigure = new SysConfigure();
            sysConfigure.setCreateUser(SingletonLoginUtils.getSysUserId());
            this.save(sysConfigure);
        }
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_CONFIGURE, sysConfigure);
        return sysConfigure;
    }

    @Override
    public ResponseResult update(SysConfigure sysConfigure) {
        if (null == sysConfigure || null == sysConfigure.getId() || 0 == sysConfigure.getId()) {
            return ResponseResult.error("数据错误");
        }
        sysConfigure.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(sysConfigure);
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_CONFIGURE, JSONObject.toJSONString(sysConfigure));
        return ResponseResult.success();
    }
}
