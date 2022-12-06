package com.zyxx.sys.controller;


import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.service.SysHandleLogService;
import com.zyxx.sys.vo.SysHandleLogVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @since 2020-08-31
 */
@Controller
@RequestMapping("/sys/sys-handle-log")
public class SysHandleLogController {

    @Autowired
    private SysHandleLogService sysHandleLogService;

    @ApiOperation(value = "请求操作日志列表页", notes = "请求操作日志列表页")
    @GetMapping("init")
    public String init() {
        return "sys/handlelog/list";
    }

    @ApiOperation(value = "分页查询操作日志数据", notes = "分页查询操作日志数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "account", value = "操作账户")
    })
    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, SysHandleLogVO sysHandleLog) {
        return sysHandleLogService.list(page, limit, sysHandleLog);
    }
}
