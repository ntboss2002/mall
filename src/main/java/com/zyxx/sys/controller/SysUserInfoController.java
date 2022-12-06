package com.zyxx.sys.controller;


import com.zyxx.common.excel.ExcelUtils;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysUserInfo;
import com.zyxx.sys.service.SysDictDetailService;
import com.zyxx.sys.service.SysRoleInfoService;
import com.zyxx.sys.service.SysUserInfoService;
import com.zyxx.sys.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-06
 */
@Api(tags = "后台管理端--用户管理")
@Controller
@RequestMapping("/sys/sys-user-info")
public class SysUserInfoController {

    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private SysRoleInfoService sysRoleInfoService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @ApiOperation(value = "请求用户管理列表页", notes = "请求用户管理列表页")
    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        model.addAttribute("status", sysDictDetailService.listSysDictDetailByDictCode("user_status"));
        return "sys/userinfo/list";
    }

    @ApiOperation(value = "请求用户管理新增页", notes = "请求用户管理新增页")
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        return "sys/userinfo/add";
    }

    @ApiOperation(value = "请求用户管理编辑页", notes = "请求用户管理编辑页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysUserInfoService.getById(id));
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        return "sys/userinfo/update";
    }

    @ApiOperation(value = "请求用户个人信息页", notes = "请求用户个人信息页")
    @GetMapping("info")
    public String info(Model model) {
        model.addAttribute("data", sysUserInfoService.getById(SingletonLoginUtils.getSysUserId()));
        model.addAttribute("sex", sysDictDetailService.listSysDictDetailByDictCode("user_sex"));
        return "sys/userinfo/info";
    }

    @ApiOperation(value = "请求修改密码页", notes = "请求修改密码页")
    @GetMapping("updatePassword")
    public String updatePassword() {
        return "sys/userinfo/password";
    }

    @ApiOperation(value = "请求用户管理授权页", notes = "请求用户管理授权页")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("auth/{id}")
    public String auth(@PathVariable Long id, Model model) {
        model.addAttribute("data", sysUserInfoService.getById(id));
        return "sys/userinfo/auth";
    }

    @ApiOperation(value = "根据用户id查询用户角色", notes = "根据用户id查询用户角色")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("listRoleForSelect/{id}")
    @ResponseBody
    public String listRoleForSelect(@PathVariable Integer id) {
        return sysRoleInfoService.listXmSelectPojo(id);
    }

    @ApiOperation(value = "分页查询用户信息数据", notes = "分页查询用户信息数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true),
            @ApiImplicitParam(name = "name", value = "用户姓名"),
            @ApiImplicitParam(name = "account", value = "账户"),
            @ApiImplicitParam(name = "phone", value = "电话"),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）")
    })
    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, SysUserInfo sysUserInfo) {
        return sysUserInfoService.list(page, limit, sysUserInfo);
    }

    @ApiOperation(value = "添加用户信息", notes = "添加用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "sex", value = "性别")
    })
    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody SysUserInfo sysUserInfo) {
        return sysUserInfoService.add(sysUserInfo);
    }

    @ApiOperation(value = "编辑用户信息", notes = "编辑用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "sex", value = "性别")
    })
    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysUserInfo sysUserInfo) {
        return sysUserInfoService.update(sysUserInfo);
    }

    @ApiOperation(value = "用户授权", notes = "用户授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "roles", value = "多个角色id，逗号分隔", required = true)
    })
    @GetMapping("auth/{id}/{roles}")
    @ResponseBody
    public ResponseResult auth(@PathVariable("id") Integer id, @PathVariable("roles") String roles) {
        return sysUserRoleService.auth(id, roles);
    }

    @ApiOperation(value = "禁/启用用户信息", notes = "禁/启用用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "status", value = "数据状态（0正常，1禁用）", required = true)
    })
    @GetMapping("updateStatus/{id}/{status}")
    @ResponseBody
    public ResponseResult updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return sysUserInfoService.updateStatus(id, status);
    }

    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "用户id", required = true))
    @GetMapping("delete/{id}")
    @ResponseBody
    public ResponseResult delete(@PathVariable Long id) {
        return sysUserInfoService.delete(id);
    }

    @ApiOperation(value = "编辑个人信息", notes = "编辑个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "name", value = "用户名称", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "sex", value = "性别")
    })
    @PostMapping("info")
    @ResponseBody
    public ResponseResult info(@RequestBody SysUserInfo sysUserInfo) {
        return sysUserInfoService.info(sysUserInfo);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true),
            @ApiImplicitParam(name = "password", value = "新密码", required = true)
    })
    @PostMapping("updatePassword/{oldPassword}/{password}")
    @ResponseBody
    public ResponseResult updatePassword(@PathVariable String oldPassword, @PathVariable String password) {
        return sysUserInfoService.updatePassword(oldPassword.trim(), password.trim());
    }

    @GetMapping("updatepassword/{id}/{password}")
    @ResponseBody
    public ResponseResult updatepassword(@PathVariable("id") Integer id, @PathVariable("password") String password) {
        return sysUserInfoService.updatePassword(id, password);
    }

    @ApiOperation(value = "导出用户信息", notes = "导出用户信息")
    @GetMapping(value = "/export")
    public ModelAndView exportXls(SysUserInfo sysUserInfo) {
        return ExcelUtils.export("用户信息统计报表", SysUserInfo.class, sysUserInfoService.list(1, Integer.MAX_VALUE, sysUserInfo).getData());
    }
}
