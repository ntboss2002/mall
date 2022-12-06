package com.zyxx.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyxx.sys.entity.SysHandleLog;
import com.zyxx.sys.vo.SysHandleLogVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2020-08-31
 */
public interface SysHandleLogMapper extends BaseMapper<SysHandleLog> {

    /**
     * 分页查询
     */
    IPage<SysHandleLogVO> listSysHandleLogVO(Page<SysHandleLogVO> page, @Param(Constants.WRAPPER) Wrapper<SysHandleLogVO> queryWrapper);
}
