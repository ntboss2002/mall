package com.zyxx.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyxx.common.consts.SystemConst;
import com.zyxx.common.enums.StatusEnums;
import com.zyxx.common.kaptcha.KaptchaUtil;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.PasswordUtils;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.common.utils.ServletUtils;
import com.zyxx.sys.entity.SysUserInfo;
import com.zyxx.sys.service.SysConfigureService;
import com.zyxx.sys.service.SysLoginLogService;
import com.zyxx.sys.service.SysPermissionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-01
 **/
@Api(tags = "后台管理端--登录")
@Controller
public class SysLoginController {

    @Autowired
    private SysPermissionInfoService sysPermissionInfoService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private SysConfigureService sysConfigureService;

    @ApiOperation(value = "请求登录页面", notes = "请求登录页面")
    @GetMapping("login")
    public String init(Model model) {
        model.addAttribute("sysConfig", sysConfigureService.saveSysConfigure());
        return "sys/login";
    }

    @ApiOperation(value = "请求欢迎页面", notes = "请求欢迎页面")
    @GetMapping("welcome")
    public String welcome(Model model) {
        model.addAttribute("sysConfig", sysConfigureService.saveSysConfigure());
        return "sys/welcome";
    }

    @ApiOperation(value = "请求主页面", notes = "请求主页面")
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("data", SingletonLoginUtils.getSysUserInfo());
        model.addAttribute("sysConfig", sysConfigureService.saveSysConfigure());
        return "sys/index";
    }

    @ApiOperation(value = "登录验证", notes = "登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "resCode", value = "验证码", required = true),
            @ApiImplicitParam(name = "rememberMe", value = "记住登录", required = true)
    })
    @PostMapping("doLogin")
    @ResponseBody
    public ResponseResult doLogin(String account, String password, String resCode, Boolean rememberMe, HttpServletRequest request) throws Exception {
        // 验证码
        if (!KaptchaUtil.validate(resCode, request)) {
            return ResponseResult.error(StatusEnums.KAPTCH_ERROR);
        }
        // 验证帐号和密码
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        // 记住登录状态
        token.setRememberMe(rememberMe);
        try {
            // 执行登录
            subject.login(token);
            // 将用户保存到session中
            SysUserInfo userInfo = SingletonLoginUtils.getSysUserInfo();
            request.getSession().setAttribute(SystemConst.SYSTEM_USER_SESSION, userInfo);
            // 保存登录日志
            sysLoginLogService.save(account, 0, "用户登录成功");
            return ResponseResult.success("登录成功，欢迎回来！");
        } catch (UnknownAccountException e) {
            sysLoginLogService.save(account, 1, "登录账户不存在");
            return ResponseResult.error("账户不存在");
        } catch (DisabledAccountException e) {
            sysLoginLogService.save(account, 1, "登录账户已被冻结");
            return ResponseResult.error("账户已被冻结");
        } catch (IncorrectCredentialsException e) {
            sysLoginLogService.save(account, 1, "登录密码不正确");
            return ResponseResult.error("密码不正确");
        } catch (ExcessiveAttemptsException e) {
            sysLoginLogService.save(account, 1, "密码连续输入错误超过5次，锁定半小时");
            return ResponseResult.error("密码连续输入错误超过5次，锁定半小时");
        } catch (RuntimeException e) {
            sysLoginLogService.save(account, 1, "未知错误");
            return ResponseResult.error("未知错误");
        }
    }

    @ApiOperation(value = "登录成功，跳转主页面", notes = "登录成功，跳转主页面")
    @PostMapping("success")
    public String success() {
        return ServletUtils.redirectTo("/");
    }

    @ApiOperation(value = "初始化菜单数据", notes = "初始化菜单数据")
    @GetMapping("initMenu")
    @ResponseBody
    public String initMenu(HttpSession session) {
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_MENU);
        if (null != object) {
            return object.toString();
        }
        return sysPermissionInfoService.initMenu(session);
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @GetMapping(value = "loginOut")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ServletUtils.redirectTo("login");
    }

    @ApiOperation(value = "页面解锁", notes = "页面解锁")
    @ApiImplicitParams(@ApiImplicitParam(name = "password", value = "登录密码", required = true))
    @PostMapping(value = "unlock")
    @ResponseBody
    public ResponseResult unlock(@RequestBody JSONObject param) {
        if (null == param) {
            return ResponseResult.error("请输入密码");
        }
        String password = param.getString("password");
        if (StringUtils.isBlank(password)) {
            return ResponseResult.error("请输入密码");
        }
        SysUserInfo sysUserInfo = SingletonLoginUtils.getSysUserInfo();
        if (null == sysUserInfo) {
            return ResponseResult.error("用户信息异常");
        }
        if (!PasswordUtils.getPassword(password, sysUserInfo.getAccount()).equals(sysUserInfo.getPassword())) {
            return ResponseResult.error("密码错误");
        }
        return ResponseResult.success();
    }
}
