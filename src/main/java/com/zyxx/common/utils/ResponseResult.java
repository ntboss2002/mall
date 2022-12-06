package com.zyxx.common.utils;

import com.zyxx.common.enums.BaseEnums;
import com.zyxx.common.enums.StatusEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单例模式返回接口相应数据
 *
 * @Author zxy
 */
@Data
public class ResponseResult implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    private static ResponseResult resultData(Integer code, String msg, Object data) {
        ResponseResult res = new ResponseResult();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    /**
     * 成功
     */
    public static ResponseResult success() {
        return resultData(StatusEnums.OK.getCode(), StatusEnums.OK.getMsg(), null);
    }

    /**
     * 成功
     */
    public static ResponseResult success(String msg) {
        return resultData(StatusEnums.OK.getCode(), msg, null);
    }

    /**
     * 成功
     */
    public static ResponseResult success(Object data) {
        return resultData(StatusEnums.OK.getCode(), StatusEnums.OK.getMsg(), data);
    }

    /**
     * 成功
     */
    public static ResponseResult success(String msg, Object data) {
        return resultData(StatusEnums.OK.getCode(), msg, data);
    }

    /**
     * 失败
     */
    public static ResponseResult error() {
        return resultData(StatusEnums.ERROR.getCode(), StatusEnums.ERROR.getMsg(), null);
    }

    /**
     * 失败
     */
    public static ResponseResult error(Integer code) {
        return resultData(code, null, null);
    }

    /**
     * 失败
     */
    public static ResponseResult error(String msg) {
        return resultData(StatusEnums.ERROR.getCode(), msg, null);
    }

    /**
     * 失败
     */
    public static ResponseResult error(Object data) {
        return resultData(StatusEnums.ERROR.getCode(), StatusEnums.ERROR.getMsg(), data);
    }

    /**
     * 失败
     */
    public static ResponseResult error(Integer code, String msg, Object data) {
        return resultData(code, msg, data);
    }

    /**
     * 失败
     */
    public static ResponseResult error(BaseEnums enums) {
        return resultData(enums.getCode(), enums.getMsg(), null);
    }
}