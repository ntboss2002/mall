package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysDictDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 */
public interface SysDictDetailService extends IService<SysDictDetail> {

    /**
     * 分页查询
     */
    LayTableResult list(String dictCode);

    /**
     * 新增
     */
    ResponseResult add(SysDictDetail sysDictDetail);

    /**
     * 编辑
     */
    ResponseResult update(SysDictDetail sysDictDetail);

    /**
     * 删除
     */
    ResponseResult delete(Long id);

    /**
     * 根据字典编码，和字典值查询字典数据
     */
    String getDictDataByTypeAndValue(String dictCode, String code);

    /**
     * 根据字典编码查询字典配置信息
     * 提供给下拉框使用
     */
    List<SysDictDetail> listSysDictDetailByDictCode(String dictCode);
}
