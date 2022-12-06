package com.zyxx.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.api.service.NewsInfoApiService;
import com.zyxx.business.entity.NewsInfo;
import com.zyxx.business.mapper.NewsInfoMapper;
import com.zyxx.common.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName NewsInfoApiServiceImpl
 * @Description
 * @Author zxy
 * @Date 2020-09-30 11:17:17
 **/
@Service
public class NewsInfoApiServiceImpl implements NewsInfoApiService {

    @Autowired
    private NewsInfoMapper newsInfoMapper;

    @Override
    public ResponseResult listNewsInfoByType(Integer page, Integer limit, Integer type) {
        LambdaQueryWrapper<NewsInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(NewsInfo::getId, NewsInfo::getType, NewsInfo::getTitle, NewsInfo::getCover, NewsInfo::getCreateTime);
        if (null != type) {
            queryWrapper.eq(NewsInfo::getType, type);
        }
        queryWrapper.orderByAsc(NewsInfo::getSort).orderByDesc(NewsInfo::getCreateTime);
        return ResponseResult.success(newsInfoMapper.selectPage(new Page<>(null != page ? page : 1, null != limit ? limit : Integer.MAX_VALUE), queryWrapper).getRecords());
    }

    @Override
    public ResponseResult getNewsInfoById(Integer id) {
        return ResponseResult.success(newsInfoMapper.selectById(id));
    }
}
