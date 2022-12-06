package com.zyxx.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.business.entity.NewsInfo;
import com.zyxx.business.mapper.NewsInfoMapper;
import com.zyxx.business.service.NewsInfoService;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-09-30
 */
@Service
public class NewsInfoServiceImpl extends ServiceImpl<NewsInfoMapper, NewsInfo> implements NewsInfoService {

    @Override
    public LayTableResult list(Integer page, Integer limit, NewsInfo newsInfo) {
        LambdaQueryWrapper<NewsInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (null != newsInfo.getType()) {
            queryWrapper.eq(NewsInfo::getType, newsInfo.getType());
        }
        if (StringUtils.isNotBlank(newsInfo.getTitle())) {
            queryWrapper.like(NewsInfo::getTitle, newsInfo.getTitle());
        }
        queryWrapper.orderByAsc(NewsInfo::getSort).orderByDesc(NewsInfo::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(NewsInfo newsInfo) {
        if (null == newsInfo) {
            return ResponseResult.error("数据错误");
        }
        if (StringUtils.isBlank(newsInfo.getTitle())) {
            return ResponseResult.error("请输入标题");
        }
        newsInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(newsInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(NewsInfo newsInfo) {
        if (null == newsInfo || null == newsInfo.getId() || 0 == newsInfo.getId()) {
            return ResponseResult.error("数据错误");
        }
        if (StringUtils.isBlank(newsInfo.getTitle())) {
            return ResponseResult.error("请输入标题");
        }
        newsInfo.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(newsInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult del(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("数据错误");
        }
        this.removeById(id);
        return ResponseResult.success();
    }
}
