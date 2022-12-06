package com.zyxx.common.aspect;

import com.zyxx.common.annotation.SysLog;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.AddressUtils;
import com.zyxx.common.utils.IPUtils;
import com.zyxx.common.utils.SpringContextUtils;
import com.zyxx.sys.entity.SysHandleLog;
import com.zyxx.sys.entity.SysUserInfo;
import com.zyxx.sys.mapper.SysHandleLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 系统操作日志
 *
 * @author zxy
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {
    /**
     * 开始时间
     */
    private long startTime = 0L;
    /**
     * 结束时间
     */
    private long endTime = 0L;

    @Autowired
    private SysHandleLogMapper sysHandleLogMapper;

    /**
     * 切点
     */
    @Pointcut("execution( * com.zyxx.*.controller.*.*(..))")
    public void cutController() {

    }

    @Before("cutController()")
    public void doBeforeInServiceLayer() {
        startTime = System.currentTimeMillis();
    }

    @After("cutController()")
    public void doAfterInServiceLayer() {
    }

    @Around("cutController()")
    public Object recordSysLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        // 执行结束时间
        endTime = System.currentTimeMillis();
        // 请求耗时
        log.info("请求耗时：" + (int) (endTime - startTime));

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        SysLog syslog = targetMethod.getAnnotation(SysLog.class);
        // 判断方法是否存在 @SysLog 注解
        if (null != syslog) {
            SysUserInfo sysUserInfo = SingletonLoginUtils.getSysUserInfo();
            // 获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            // 操作日志
            SysHandleLog sysHandleLog = new SysHandleLog();
            // 操作用户
            sysHandleLog.setCreateUser(null != sysUserInfo ? sysUserInfo.getId() : 0);
            // 用户ip
            sysHandleLog.setIp(IPUtils.getIpAddress(request));
            // 用户位置
            sysHandleLog.setLocation(AddressUtils.getAddressByIP(sysHandleLog.getIp()));
            // 请求地址
            sysHandleLog.setUrl(request.getRequestURI());
            // 请求参数
            sysHandleLog.setParam(Arrays.toString(joinPoint.getArgs()));
            // 注解标识
            sysHandleLog.setModel(syslog.model());
            // 创建时间
            sysHandleLog.setCreateTime(new Date());
            // 请求耗时
            sysHandleLog.setSpendTime((int) (endTime - startTime));
            // 保存到数据库
            sysHandleLogMapper.insert(sysHandleLog);
        }
        return result;
    }

    @AfterReturning(returning = "ret", pointcut = "cutController()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime));
    }
}
