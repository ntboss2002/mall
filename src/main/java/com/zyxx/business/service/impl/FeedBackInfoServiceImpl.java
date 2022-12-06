package com.zyxx.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.business.entity.FeedBackInfo;
import com.zyxx.business.mapper.FeedBackInfoMapper;
import com.zyxx.business.service.FeedBackInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.business.vo.FeedBackInfoVO;
import com.zyxx.common.utils.LayTableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-09-29
 */
@Service
public class FeedBackInfoServiceImpl extends ServiceImpl<FeedBackInfoMapper, FeedBackInfo> implements FeedBackInfoService {

    @Autowired
    private FeedBackInfoMapper feedBackInfoMapper;

    @Override
    public LayTableResult list(Integer page, Integer limit, FeedBackInfoVO feedBackInfoVO) {
        QueryWrapper<FeedBackInfoVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.del_flag", 0);
        if (null != feedBackInfoVO.getType()) {
            queryWrapper.eq("a.type", feedBackInfoVO.getType());
        }
        if (StringUtils.isNotBlank(feedBackInfoVO.getCreateUserName())) {
            queryWrapper.eq("b.name", feedBackInfoVO.getCreateUserName());
        }
        queryWrapper.orderByDesc("a.create_time");
        return new LayTableResult(feedBackInfoMapper.listFeedBackInfoVO(new Page<>(page, limit), queryWrapper));
    }
}
