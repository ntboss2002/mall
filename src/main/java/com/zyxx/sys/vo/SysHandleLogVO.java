package com.zyxx.sys.vo;

import com.zyxx.sys.entity.SysHandleLog;
import lombok.Data;

/**
 * @ClassName SysHandleLogVO
 * @Description
 * @Author zxy
 * @Date 2020-09-21 11:08:08
 **/
@Data
public class SysHandleLogVO extends SysHandleLog {

    private String createUserName;
}
