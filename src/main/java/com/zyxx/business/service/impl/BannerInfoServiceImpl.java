package com.zyxx.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.business.entity.BannerInfo;
import com.zyxx.business.mapper.BannerInfoMapper;
import com.zyxx.business.service.BannerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class BannerInfoServiceImpl extends ServiceImpl<BannerInfoMapper, BannerInfo> implements BannerInfoService {

    @Override
    public LayTableResult list(Integer page, Integer limit, BannerInfo bannerInfo) {
        LambdaQueryWrapper<BannerInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (null != bannerInfo.getType()) {
            queryWrapper.eq(BannerInfo::getType, bannerInfo.getType());
        }
        queryWrapper.orderByDesc(BannerInfo::getCreateTime);
        return new LayTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(BannerInfo bannerInfo) {
        if (null == bannerInfo) {
            return ResponseResult.error("数据错误");
        }
        if (StringUtils.isBlank(bannerInfo.getUrl())) {
            return ResponseResult.error("请上传图片");
        }
        if (null == bannerInfo.getType()) {
            return ResponseResult.error("请选择类型");
        }
        if (1 == bannerInfo.getType() && StringUtils.isBlank(bannerInfo.getContent())) {
            return ResponseResult.error("请输入指定内容");
        }
        if (2 == bannerInfo.getType() && StringUtils.isBlank(bannerInfo.getLink())) {
            return ResponseResult.error("请输入指定链接");
        }
        bannerInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(bannerInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(BannerInfo bannerInfo) {
        if (null == bannerInfo || null == bannerInfo.getId() || 0 == bannerInfo.getId()) {
            return ResponseResult.error("数据错误");
        }
        if (StringUtils.isBlank(bannerInfo.getUrl())) {
            return ResponseResult.error("请上传图片");
        }
        if (null == bannerInfo.getType()) {
            return ResponseResult.error("请选择类型");
        }
        if (1 == bannerInfo.getType() && StringUtils.isBlank(bannerInfo.getContent())) {
            return ResponseResult.error("请输入指定内容");
        }
        if (2 == bannerInfo.getType() && StringUtils.isBlank(bannerInfo.getLink())) {
            return ResponseResult.error("请输入指定链接");
        }
        bannerInfo.setUpdateUser(SingletonLoginUtils.getSysUserId());
        this.updateById(bannerInfo);
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
