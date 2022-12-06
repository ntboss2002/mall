package com.zyxx.sys.controller;


import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysLoginLog;
import com.zyxx.sys.service.SysDictDetailService;
import com.zyxx.sys.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-28
 */
@Api(tags = "后台管理端--登录日志")
@Controller
@RequestMapping("/sys/sys-login-log")
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求登录日志列表页", notes = "请求登录日志列表页")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("login_status"));
        return "sys/loginlog/list";
    }

    @ApiOperation(value = "分页查询登录日志数据", notes = "分页查询登录日志数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "account", value = "登录账户"),
            @ApiImplicitParam(name = "status", value = "数据状态（0成功，1失败）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, SysLoginLog sysLoginLog) {
        return sysLoginLogService.list(page, limit, sysLoginLog);
    }

}
