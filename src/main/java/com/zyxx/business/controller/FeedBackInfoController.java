package com.zyxx.business.controller;


import com.zyxx.business.service.FeedBackInfoService;
import com.zyxx.business.vo.FeedBackInfoVO;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.service.SysDictDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-09-29
 */
@Controller
@RequestMapping("/business/feed-back-info")
public class FeedBackInfoController {

    @Autowired
    private FeedBackInfoService feedBackInfoService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @GetMapping("init")
    public String init(Model model) {
        model.addAttribute("type", sysDictDetailService.listSysDictDetailByDictCode(""));
        return "business/feedbackinfo/list";
    }

    @PostMapping("list")
    @ResponseBody
    public LayTableResult list(Integer page, Integer limit, FeedBackInfoVO feedBackInfoVO) {
        return feedBackInfoService.list(page, limit, feedBackInfoVO);
    }
}
