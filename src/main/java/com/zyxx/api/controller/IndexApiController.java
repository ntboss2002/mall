package com.zyxx.api.controller;

import com.zyxx.api.service.IndexApiService;
import com.zyxx.business.service.BannerInfoService;
import com.zyxx.business.service.NoticeInfoService;
import com.zyxx.common.utils.ResponseResult;
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
 * 首页相关
 *
 * @Author zxy
 * @Date 2020-09-25 10:28:28
 **/
@Api(tags = "首页相关")
@RestController
@RequestMapping("api/index")
public class IndexApiController {

    @Autowired
    private IndexApiService indexApiService;
    @Autowired
    private BannerInfoService bannerInfoService;
    @Autowired
    private NoticeInfoService noticeInfoService;

    @ApiOperation(value = "获取首页数据", notes = "获取首页数据")
    @GetMapping("initIndexData")
    public ResponseResult initIndexData() {
        return indexApiService.initIndexData();
    }

    @ApiOperation(value = "根据id获取轮播信息", notes = "根据id获取轮播信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "轮播id", required = true))
    @PostMapping("getBannerInfoById")
    public ResponseResult getBannerInfoById(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("数据错误");
        }
        return ResponseResult.success(bannerInfoService.getById(id));
    }

    @ApiOperation(value = "根据id获取公告信息", notes = "根据id获取公告信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "公告id", required = true))
    @PostMapping("getNoticeInfoById")
    public ResponseResult getNoticeInfoById(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("数据错误");
        }
        return ResponseResult.success(noticeInfoService.getById(id));
    }
}
