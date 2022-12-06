package com.zyxx.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.api.service.IndexApiService;
import com.zyxx.business.entity.BannerInfo;
import com.zyxx.business.entity.NewsInfo;
import com.zyxx.business.entity.NoticeInfo;
import com.zyxx.business.mapper.BannerInfoMapper;
import com.zyxx.business.mapper.NewsInfoMapper;
import com.zyxx.business.mapper.NoticeInfoMapper;
import com.zyxx.common.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IndexApiServiceImpl
 * @Description
 * @Author zxy
 * @Date 2020-09-28 13:40:40
 **/
@Service
public class IndexApiServiceImpl implements IndexApiService {

    @Autowired
    private BannerInfoMapper bannerInfoMapper;
    @Autowired
    private NoticeInfoMapper noticeInfoMapper;
    @Autowired
    private NewsInfoMapper newsInfoMapper;


    @Override
    public ResponseResult initIndexData() {
        JSONObject res = new JSONObject();
        // 轮播
        LambdaQueryWrapper<BannerInfo> bannerInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bannerInfoLambdaQueryWrapper.select(BannerInfo::getId, BannerInfo::getUrl, BannerInfo::getType, BannerInfo::getLink);
        res.put("banner", bannerInfoMapper.selectList(bannerInfoLambdaQueryWrapper));
        // 公告
        LambdaQueryWrapper<NoticeInfo> noticeInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeInfoLambdaQueryWrapper.select(NoticeInfo::getId, NoticeInfo::getTitle, NoticeInfo::getType, NoticeInfo::getLink);
        res.put("notice", noticeInfoMapper.selectList(noticeInfoLambdaQueryWrapper));
        // 新闻资讯
        LambdaQueryWrapper<NewsInfo> newsInfoQueryWrapper = new LambdaQueryWrapper<>();
        newsInfoQueryWrapper.orderByDesc(NewsInfo::getCreateTime);
        res.put("news", newsInfoMapper.selectPage(new Page<>(1, 6), newsInfoQueryWrapper).getRecords());
        return ResponseResult.success(res);
    }
}
