package com.zyxx.sys.controller;


import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysDict;
import com.zyxx.sys.service.SysDictDetailService;
import com.zyxx.sys.service.SysDictService;
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
 * @since 2020-07-15
 */
@Api(tags = "后台管理端--字典管理")
@Controller
@RequestMapping("/sys/sys-dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求字典管理列表页", notes = "请求字典管理列表页")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("dict_status"));
        return "sys/dict/list";
    }

    @ApiOperation(value = "请求字典管理新增页", notes = "请求字典管理新增页")
    @GetMapping("add")
    public String add() {
        return "sys/dict/add";
    }

    @ApiOperation(value = "请求字典管理编辑页", notes = "请求字典管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典管理id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysDictService.getById(id));
        return "sys/dict/update";
    }

    @ApiOperation(value = "分页查询字典管理数据", notes = "分页查询字典管理数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "code", value = "字典编码"),
            @ApiImplicitParam(name = "name", value = "字典名称"),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, SysDict sysDict) {
        return sysDictService.list(page, limit, sysDict);
    }

    @ApiOperation(value = "新增字典管理", notes = "新增字典管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编码", required = true),
            @ApiImplicitParam(name = "name", value = "字典名称", required = true),
            @ApiImplicitParam(name = "descript", value = "字典描述")
    })
    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody SysDict sysDict) {
        return sysDictService.add(sysDict);
    }

    @ApiOperation(value = "修改字典管理", notes = "修改字典管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true),
            @ApiImplicitParam(name = "code", value = "字典编码", required = true),
            @ApiImplicitParam(name = "name", value = "字典名称", required = true),
            @ApiImplicitParam(name = "descript", value = "字典描述")
    })
    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysDict sysDict) {
        return sysDictService.update(sysDict);
    }

    @ApiOperation(value = "禁/启用字典管理", notes = "禁/启用字典管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id", required = true),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    public ResponseResult updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        return sysDictService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除字典管理", notes = "删除字典管理")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    public ResponseResult delete(@PathVariable Integer id) {
        return sysDictService.delete(id);
    }
}
