package com.zyxx.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.business.entity.BannerInfo;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-09-28
 */
public interface BannerInfoService extends IService<BannerInfo> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, BannerInfo bannerInfo);

    /**
     * 新增
     */
    ResponseResult add(BannerInfo bannerInfo);

    /**
     * 修改
     */
    ResponseResult update(BannerInfo bannerInfo);

    /**
     * 删除
     */
    ResponseResult del(Integer id);
}
