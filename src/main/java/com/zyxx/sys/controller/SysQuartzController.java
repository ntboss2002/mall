package com.zyxx.sys.controller;


import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysQuartz;
import com.zyxx.sys.service.SysDictDetailService;
import com.zyxx.sys.service.SysQuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 定时任务信息表 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-21
 */
@Api(tags = "后台管理端--定时任务管理")
@Controller
@RequestMapping("/sys/sys-quartz")
public class SysQuartzController {

    @Autowired
    private SysQuartzService sysQuartzService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求定时任务管理列表页", notes = "请求定时任务管理列表页")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("quartz_status"));
        model.addAttribute("dataStatus", sysDictDetailService.listSysDictDetailByDictCode("quartz_data_status"));
        return "sys/quartz/list";
    }

    @ApiOperation(value = "请求定时任务管理新增页", notes = "请求定时任务管理新增页")
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("quartz_status"));
        return "sys/quartz/add";
    }

    @ApiOperation(value = "请求定时任务管理新增页", notes = "请求定时任务管理新增页")
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysQuartzService.getById(id));
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("quartz_status"));
        return "sys/quartz/update";
    }

    @ApiOperation(value = "分页查询定时任务管理数据", notes = "分页查询定时任务管理数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "className", value = "人物类名"),
            @ApiImplicitParam(name = "quartzStatus", value = "任务状态"),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, SysQuartz sysQuartz) {
        return sysQuartzService.list(page, limit, sysQuartz);
    }

    @ApiOperation(value = "新增定时任务", notes = "新增定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "className", value = "任务类名", required = true),
            @ApiImplicitParam(name = "cronExpression", value = "cron表达式", required = true),
            @ApiImplicitParam(name = "param", value = "参数"),
            @ApiImplicitParam(name = "quartzStatus", value = "启动状态（0--启动1--停止）", required = true),
            @ApiImplicitParam(name = "descript", value = "字典描述")
    })
    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody SysQuartz sysQuartz) {
        return sysQuartzService.add(sysQuartz);
    }

    @ApiOperation(value = "编辑定时任务", notes = "编辑定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务id", required = true),
            @ApiImplicitParam(name = "className", value = "任务类名", required = true),
            @ApiImplicitParam(name = "cronExpression", value = "cron表达式", required = true),
            @ApiImplicitParam(name = "param", value = "参数"),
            @ApiImplicitParam(name = "quartzStatus", value = "启动状态（0--启动1--停止）", required = true),
            @ApiImplicitParam(name = "descript", value = "字典描述")
    })
    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysQuartz sysQuartz) {
        try {
            return sysQuartzService.update(sysQuartz);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseResult.error();
    }

    @ApiOperation(value = "启/停用定时任务", notes = "启/停用定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "定时任务id", required = true),
            @ApiImplicitParam(name = "quartzStatus", value = "任务状态（0启动，1停止）", required = true)
    })
    @GetMapping("updateQuartzStatus/{id}/{quartzStatus}")
    @ResponseBody
    public ResponseResult updateQuartzStatus(@PathVariable("id") Long id, @PathVariable("quartzStatus") Integer quartzStatus) {
        try {
            return sysQuartzService.updateQuartzStatus(id, quartzStatus);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return ResponseResult.error();
    }

    @ApiOperation(value = "禁/启用定时任务", notes = "禁/启用定时任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "定时任务id", required = true),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    public ResponseResult updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return sysQuartzService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除定时任务", notes = "删除定时任务")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "定时任务id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    public ResponseResult delete(@PathVariable Long id) {
        return sysQuartzService.delete(id);
    }
}
