package com.zyxx.sys.controller;


import com.alibaba.fastjson.JSONObject;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysPermissionInfo;
import com.zyxx.sys.service.SysPermissionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
@Api(tags = "后台管理端--菜单管理")
@Controller
@RequestMapping("/sys/sys-permission-info")
public class SysPermissionInfoController {

    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;

    @ApiOperation(value = "请求权限菜单管理列表页", notes = "请求权限菜单管理列表页")
    @GetMapping("init")
    public String init() {
        return "sys/permissioninfo/list";
    }

    @ApiOperation(value = "请求权限菜单管理新增页", notes = "请求权限菜单管理新增页")
    @GetMapping("add")
    public String add() {
        return "sys/permissioninfo/add";
    }

    @ApiOperation(value = "请求权限菜单管理编辑页", notes = "请求权限菜单管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "权限菜单id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysPermissionInfoService.getById(id));
        return "sys/permissioninfo/update";
    }

    @ApiOperation(value = "查询权限菜单列表数据", notes = "查询权限菜单列表数据")
    @GetMapping("list")
    @ResponseBody
    public LayTableResult list() {
        List<SysPermissionInfo> list = sysPermissionInfoService.list();
        return new LayTableResult<>(null != list && !list.isEmpty() ? Long.valueOf(list.size()) : 0, list);
    }

    @ApiOperation(value = "权限菜单数据排序", notes = "权限菜单数据排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限菜单id", required = true),
            @ApiImplicitParam(name = "id", value = "顺序", required = true)
    })
    @GetMapping("updateSort/{id}/{sort}")
    @ResponseBody
    public ResponseResult updateSort(@PathVariable("id") Integer id, @PathVariable("sort") Integer sort) {
        return sysPermissionInfoService.updateSort(id, sort);
    }

    @ApiOperation(value = "根据类型获取权限菜单数据", notes = "根据类型获取权限菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "权限菜单类型", required = true),
            @ApiImplicitParam(name = "pid", value = "父级菜单id", required = true)
    })
    @GetMapping("listPermissionInfoByPid/{type}/{pid}")
    @ResponseBody
    public ResponseResult listPermissionInfoByPid(@PathVariable("type") Integer type, @PathVariable("pid") Integer pid) {
        return sysPermissionInfoService.listPermissionInfoByPid(type, pid);
    }

    @ApiOperation(value = "新增权限菜单数据", notes = "新增权限菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", required = true),
            @ApiImplicitParam(name = "pid", value = "父级id"),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "sign", value = "标识", required = true),
            @ApiImplicitParam(name = "href", value = "链接地址", required = true),
            @ApiImplicitParam(name = "target", value = "打开方式", required = true),
            @ApiImplicitParam(name = "icon", value = "图标"),
            @ApiImplicitParam(name = "sort", value = "排序"),
            @ApiImplicitParam(name = "descript", value = "描述")
    })
    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody JSONObject jsonObject) {
        return sysPermissionInfoService.add(jsonObject);
    }

    @ApiOperation(value = "编辑权限菜单数据", notes = "编辑权限菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限菜单id", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "sign", value = "标识", required = true),
            @ApiImplicitParam(name = "href", value = "链接地址", required = true),
            @ApiImplicitParam(name = "target", value = "打开方式", required = true),
            @ApiImplicitParam(name = "icon", value = "图标"),
            @ApiImplicitParam(name = "sort", value = "排序"),
            @ApiImplicitParam(name = "descript", value = "描述")
    })
    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysPermissionInfo sysPermissionInfo) {
        return sysPermissionInfoService.update(sysPermissionInfo);
    }

    @ApiOperation(value = "禁/启用权限菜单信息", notes = "禁/启用权限菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限菜单id", required = true),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    public ResponseResult updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        return sysPermissionInfoService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除权限菜单信息", notes = "删除权限菜单信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "权限菜单id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    public ResponseResult delete(@PathVariable Integer id) {
        return sysPermissionInfoService.delete(id);
    }
}
