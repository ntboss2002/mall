package com.zyxx.sys.controller;


import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysDictDetail;
import com.zyxx.sys.service.SysDictDetailService;
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
@Api(tags = "后台管理端--字典配置")
@Controller
@RequestMapping("/sys/sys-dict-detail")
public class SysDictDetailController {

    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求字典配置列表页", notes = "请求字典配置列表页")
    @ApiImplicitParams(@ApiImplicitParam(name = "dictCode", value = "字典类型", required = true))
    @GetMapping("init/{dictCode}")
    public String auth(@PathVariable String dictCode, Model model) {
        model.addAttribute("dictCode", dictCode);
        return "sys/dictdetail/list";
    }

    @ApiOperation(value = "请求字典配置新增页", notes = "请求字典配置新增页")
    @ApiImplicitParams(@ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true))
    @GetMapping("add/{dictCode}")
    public String add(@PathVariable String dictCode, Model model) {
        model.addAttribute("dictCode", dictCode);
        return "sys/dictdetail/add";
    }

    @ApiOperation(value = "请求字典配置编辑页", notes = "请求字典配置编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典配置id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("data", sysDictDetailService.getById(id));
        return "sys/dictdetail/update";
    }

    @ApiOperation(value = "查询字典配置列表", notes = "查询字典配置列表")
    @ApiImplicitParams(@ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true))
    @PostMapping("listDetail")
    @ResponseBody
    public LayTableResult listDetail(String dictCode) {
        return sysDictDetailService.list(dictCode);
    }

    @ApiOperation(value = "新增字典配置", notes = "新增字典配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true),
            @ApiImplicitParam(name = "code", value = "字典配置code", required = true),
            @ApiImplicitParam(name = "name", value = "字典配置名称", required = true)
    })
    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody SysDictDetail sysDictDetail) {
        return sysDictDetailService.add(sysDictDetail);
    }

    @ApiOperation(value = "编辑字典配置", notes = "编辑字典配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictCode", value = "字典类型code", required = true),
            @ApiImplicitParam(name = "id", value = "字典配置id", required = true),
            @ApiImplicitParam(name = "code", value = "字典配置code", required = true),
            @ApiImplicitParam(name = "name", value = "字典配置名称", required = true)
    })
    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysDictDetail sysDictDetail) {
        return sysDictDetailService.update(sysDictDetail);
    }

    @ApiOperation(value = "删除字典配置", notes = "删除字典配置")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "字典配置id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    public ResponseResult delete(@PathVariable Long id) {
        return sysDictDetailService.delete(id);
    }
}