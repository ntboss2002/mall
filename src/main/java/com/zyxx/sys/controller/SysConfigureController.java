package com.zyxx.sys.controller;


import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysConfigure;
import com.zyxx.sys.service.SysConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统设置 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-10-20
 */
@Controller
@RequestMapping("/sys/sys-configure")
public class SysConfigureController {

    @Autowired
    private SysConfigureService sysConfigureService;

    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("data", sysConfigureService.saveSysConfigure());
        return "sys/sysconfigure/info";
    }

    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody SysConfigure sysConfigure) {
        return sysConfigureService.update(sysConfigure);
    }
}
