package com.zyxx.api.controller;

import com.zyxx.api.service.NewsInfoApiService;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.service.SysDictDetailService;
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
 * 新闻资讯
 *
 * @Author zxy
 * @Date 2020-09-26 10:28:28
 **/
@Api(tags = "新闻资讯")
@RestController
@RequestMapping("api/newsInfo")
public class NewsInfoApiController {

    @Autowired
    private NewsInfoApiService newsInfoApiService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "获取新闻资讯类型", notes = "获取新闻资讯类型")
    @GetMapping("listNewsType")
    public ResponseResult listNewsType() {
        return ResponseResult.success(sysDictDetailService.listSysDictDetailByDictCode("news_type"));
    }

    @ApiOperation(value = "根据类型分页查询新闻资讯", notes = "根据类型分页查询新闻资讯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "limit", value = "条数"),
            @ApiImplicitParam(name = "type", value = "类型")
    })
    @PostMapping("listNewsInfoByType")
    public ResponseResult listNewsInfoByType(Integer page, Integer limit, Integer type) {
        return newsInfoApiService.listNewsInfoByType(page, limit, type);
    }

    @ApiOperation(value = "获取新闻资讯详情", notes = "获取新闻资讯详情")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "id", required = true))
    @PostMapping("getNewsInfoById")
    public ResponseResult getNewsInfoById(Integer id) {
        return newsInfoApiService.getNewsInfoById(id);
    }
}
