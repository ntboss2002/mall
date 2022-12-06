package com.zyxx.business.controller;


import com.zyxx.business.entity.NewsInfo;
import com.zyxx.business.service.NewsInfoService;
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
 * @since 2020-09-30
 */
@Controller
@RequestMapping("/business/news-info")
public class NewsInfoController {

    @Autowired
    private NewsInfoService newsInfoService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("news_type"));
        return "business/newsinfo/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("news_type"));
        return "business/newsinfo/add";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode("news_type"));
        model.addAttribute("data", newsInfoService.getById(id));
        return "business/newsinfo/update";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("data", newsInfoService.getById(id));
        return "business/newsinfo/detail";
    }

    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, NewsInfo newsInfo) {
        return newsInfoService.list(page, limit, newsInfo);
    }

    @PostMapping("add")
    @ResponseBody
    public ResponseResult add(@RequestBody NewsInfo newsInfo) {
        return newsInfoService.add(newsInfo);
    }

    @PostMapping("update")
    @ResponseBody
    public ResponseResult update(@RequestBody NewsInfo newsInfo) {
        return newsInfoService.update(newsInfo);
    }

    @GetMapping("del/{id}")
    @ResponseBody
    public ResponseResult del(@PathVariable("id") Integer id) {
        return newsInfoService.del(id);
    }
}
