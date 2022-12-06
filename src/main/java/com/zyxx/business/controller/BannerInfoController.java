package com.zyxx.business.controller;


import com.zyxx.business.entity.BannerInfo;
import com.zyxx.business.service.BannerInfoService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.service.SysDictDetailService;
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
 * @since 2020-09-28
 */
@Controller
@RequestMapping("/business/banner-info")
public class BannerInfoController {

    @Autowired
    private BannerInfoService bannerInfoService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("banner_type"));
        return "business/bannerinfo/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("banner_type"));
        return "business/bannerinfo/add";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("banner_type"));
        model.addAttribute("data", bannerInfoService.getById(id));
        return "business/bannerinfo/update";
    }

    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, BannerInfo bannerInfo) {
        return bannerInfoService.list(page, limit, bannerInfo);
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody BannerInfo bannerInfo) {
        return bannerInfoService.add(bannerInfo);
    }

    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody BannerInfo bannerInfo) {
        return bannerInfoService.update(bannerInfo);
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public ResponseResult del(@PathVariable("id") Integer id) {
        return bannerInfoService.del(id);
    }
}
