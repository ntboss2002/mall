package com.zyxx.sys.controller;

import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysDataTableColumn;
import com.zyxx.sys.service.SysDataTableColumnService;
import com.zyxx.sys.service.SysDataTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-08-11
 **/
@Api(tags = "后台管理端--数据备份")
@Controller
@RequestMapping("/sys/sys-data-table-column")
public class SysDataTableColumnController {

    @Autowired
    private SysDataTableColumnService sysDataTableColumnService;
    @Autowired
    private SysDataTableService sysDataTableService;

    @ApiOperation(value = "请求数据表字段列表页", notes = "请求数据表字段列表页")
    @GetMapping("init/{tableName}")
    public String init(@PathVariable("tableName") String tableName, Model model) {
        model.addAttribute("tableName", tableName);
        model.addAttribute("tables", sysDataTableService.listSysDataTableForSelect());
        return "sys/datatablecolumn/list";
    }

    @ApiOperation(value = "分页查询数据表字段数据", notes = "分页查询数据表字段数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "tableName", value = "表名")
    })
    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, SysDataTableColumn sysDataTableColumn) {
        return sysDataTableColumnService.list(page, limit, sysDataTableColumn);
    }
}
