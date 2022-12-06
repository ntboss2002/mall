package com.zyxx.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.business.entity.NewsInfo;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-09-30
 */
public interface NewsInfoService extends IService<NewsInfo> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, NewsInfo newsInfo);

    /**
     * 新增
     */
    ResponseResult add(NewsInfo newsInfo);

    /**
     * 修改
     */
    ResponseResult update(NewsInfo newsInfo);

    /**
     * 删除
     */
    ResponseResult del(Integer id);
}
