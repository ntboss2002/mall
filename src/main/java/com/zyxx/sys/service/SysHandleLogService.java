package com.zyxx.sys.service;

import com.zyxx.common.utils.LayTableResult;
import com.zyxx.sys.entity.SysHandleLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyxx.sys.vo.SysHandleLogVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxy
 * @since 2020-08-31
 */
public interface SysHandleLogService extends IService<SysHandleLog> {

    /**
     * 分页查询
     */
    LayTableResult list(Integer page, Integer limit, SysHandleLogVO sysHandleLog);
}
