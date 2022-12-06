package com.zyxx.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.business.entity.NoticeInfo;
import com.zyxx.business.mapper.NoticeInfoMapper;
import com.zyxx.business.service.NoticeInfoService;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-09-28
 */
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements NoticeInfoService {

    @Override
    public LayTableResult list(Integer page, Integer limit, NoticeInfo noticeInfo) {
        LambdaQueryWrapper<NoticeInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (null != noticeInfo.getType()) {
            queryWrapper.eq(NoticeInfo::getType, noticeInfo.getType());
        }
        queryWrapper.orderByDesc(NoticeInfo::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(NoticeInfo noticeInfo) {
        if (null == noticeInfo) {
            return ResponseResult.error("数据错误");
        }
        if (StringUtils.isBlank(noticeInfo.getTitle())) {
            return ResponseResult.error("请输入公告标题");
        }
        if (null == noticeInfo.getType()) {
            return ResponseResult.error("请选择类型");
        }
        if (1 == noticeInfo.getType() && StringUtils.isBlank(noticeInfo.getContent())) {
            return ResponseResult.error("请输入指定内容");
        }
        if (2 == noticeInfo.getType() && StringUtils.isBlank(noticeInfo.getLink())) {
            return ResponseResult.error("请输入指定链接");
        }
        noticeInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(noticeInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(NoticeInfo noticeInfo) {
        if (null == noticeInfo || null == noticeInfo.getId() || 0 == noticeInfo.getId()) {
            return ResponseResult.error("数据错误");
        }
        if (StringUtils.isBlank(noticeInfo.getTitle())) {
            return ResponseResult.error("请输入公告标题");
        }
        if (null == noticeInfo.getType()) {
            return ResponseResult.error("请选择类型");
        }
        if (1 == noticeInfo.getType() && StringUtils.isBlank(noticeInfo.getContent())) {
            return ResponseResult.error("请输入指定内容");
        }
        if (2 == noticeInfo.getType() && StringUtils.isBlank(noticeInfo.getLink())) {
            return ResponseResult.error("请输入指定链接");
        }
        noticeInfo.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(noticeInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult del(Integer id) {
        if (null == id || 0 == id) {
            return ResponseResult.error("数据错误");
        }
        this.removeById(id);
        return ResponseResult.success();
    }
}
