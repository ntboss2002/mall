package com.zyxx.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.business.entity.NoticeInfo;
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
public interface NoticeInfoService extends IService<NoticeInfo> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, NoticeInfo noticeInfo);

    /**
     * 新增
     */
    ResponseResult add(NoticeInfo noticeInfo);

    /**
     * 修改
     */
    ResponseResult update(NoticeInfo noticeInfo);

    /**
     * 删除
     */
    ResponseResult del(Integer id);
}
