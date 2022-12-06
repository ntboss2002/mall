package com.zyxx.sys.controller;

import com.zyxx.common.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 **/
@Api(tags = "后台管理端--异常处理")
@Controller
public class SysExceptionController {

    @ApiOperation(value = "请求400页面", notes = "请求400页面")
    @GetMapping("400")
    public String badRequest() {
        return "sys/exception/400";
    }

    @ApiOperation(value = "请求401页面", notes = "请求401页面")
    @GetMapping("403")
    public String unauthorized() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            return ServletUtils.redirectTo("login");
        }
        return "sys/exception/403";
    }

    @ApiOperation(value = "请求404页面", notes = "请求404页面")
    @GetMapping("404")
    public String notFound() {
        return "sys/exception/404";
    }

    @ApiOperation(value = "请求500页面", notes = "请求500页面")
    @GetMapping("500")
    public String serverError() {
        return "sys/exception/500";
    }
}
