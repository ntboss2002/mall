package com.zyxx.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyxx.common.redis.RedisConst;
import com.zyxx.common.redis.RedisUtil;
import com.zyxx.common.utils.DateUtils;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.ServerInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 **/
@Api(tags = "后台管理端--服务器监控")
@Controller
@RequestMapping("/sys/sys-server-info")
public class SysServerInfoController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("init")
    public String init() {
        return "sys/serverinfo/info";
    }

    @GetMapping("list")
    @ResponseBody
    public ResponseResult list() {
        Object object = redisUtil.get(RedisConst.Key.SYS_SERVER_INFO);
        if (null != object) {
            return ResponseResult.success(JSONObject.parseObject(String.valueOf(object)));
        }
        ServerInfo serverInfo = new ServerInfo();
        try {
            serverInfo.copyTo();
            // 放在redis里面，60秒过期时间
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", serverInfo);
            jsonObject.put("updateTime", DateUtils.getYmdHmsZh());
            redisUtil.set(RedisConst.Key.SYS_SERVER_INFO, jsonObject.toJSONString(), 120);
            return ResponseResult.success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.error("获取服务信息失败");
    }
}
