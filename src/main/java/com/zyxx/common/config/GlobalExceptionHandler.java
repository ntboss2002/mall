package com.zyxx.common.config;

import com.zyxx.common.enums.StatusEnums;
import com.zyxx.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @Author zxy
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * shiro 未授权异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseResult unauthorizedException(UnauthorizedException e) {
        log.error("Exception: {}", "未授权", e.getMessage());
        return ResponseResult.error(StatusEnums.FORBIDDEN_ERROR);
    }
}
