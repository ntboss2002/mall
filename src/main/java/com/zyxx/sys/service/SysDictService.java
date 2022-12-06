package com.zyxx.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.common.utils.LayTableResult;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysDict;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, SysDict sysDict);

    /**
     * 新增
     */
    ResponseResult add(SysDict sysDict);

    /**
     * 编辑
     */
    ResponseResult update(SysDict sysDict);

    /**
     * 删除
     */
    ResponseResult delete(Integer id);

    /**
     * 禁/启用
     */
    ResponseResult updateStatus(Integer id, Integer status);
}
