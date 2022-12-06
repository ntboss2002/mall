package com.zyxx.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.business.entity.FeedBackInfo;
import com.zyxx.business.vo.FeedBackInfoVO;
import com.zyxx.common.utils.LayTableResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxy
 * @since 2020-09-29
 */
public interface FeedBackInfoService extends IService<FeedBackInfo> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, FeedBackInfoVO feedBackInfoVO);
}
