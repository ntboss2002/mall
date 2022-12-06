package com.zyxx.business.controller;


import com.zyxx.business.entity.NoticeInfo;
import com.zyxx.business.service.NoticeInfoService;
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
@RequestMapping("/business/notice-info")
public class NoticeInfoController {

    @Autowired
    private NoticeInfoService noticeInfoService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("notice_type"));
        return "business/noticeinfo/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("notice_type"));
        return "business/noticeinfo/add";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("notice_type"));
        model.addAttribute("data", noticeInfoService.getById(id));
        return "business/noticeinfo/update";
    }

    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, NoticeInfo noticeInfo) {
        return noticeInfoService.list(page, limit, noticeInfo);
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody NoticeInfo noticeInfo) {
        return noticeInfoService.add(noticeInfo);
    }

    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody NoticeInfo noticeInfo) {
        return noticeInfoService.update(noticeInfo);
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public ResponseResult del(@PathVariable("id") Integer id) {
        return noticeInfoService.del(id);
    }
}
