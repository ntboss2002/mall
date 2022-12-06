package com.zyxx.business.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.business.entity.FeedBackInfo;
import com.zyxx.business.vo.FeedBackInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2020-09-29
 */
public interface FeedBackInfoMapper extends BaseMapper<FeedBackInfo> {

    /**
     * 分页查询
     */
    IPage<FeedBackInfoVO> listFeedBackInfoVO(Page<FeedBackInfoVO> page, @Param(Constants.WRAPPER) Wrapper<FeedBackInfoVO> queryWrapper);

    /**
     * APP--查询我的意见反馈
     */
    IPage<FeedBackInfoVO> listFeedBackInfoVOApi(Page<FeedBackInfoVO> page, @Param(Constants.WRAPPER) Wrapper<FeedBackInfoVO> queryWrapper);
}
