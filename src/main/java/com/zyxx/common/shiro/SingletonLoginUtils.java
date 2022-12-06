package com.zyxx.common.shiro;

import com.zyxx.sys.entity.SysUserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录用户通用处理工具类
 *
 * @Author zxy
 */
public class SingletonLoginUtils {

    private static final Logger logger = LoggerFactory.getLogger(SingletonLoginUtils.class);

    private SingletonLoginUtils() {
        throw new AssertionError();
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    public static SysUserInfo getSysUserInfo() {
        try {
            Subject subject = SecurityUtils.getSubject();
            SysUserInfo user = (SysUserInfo) subject.getPrincipal();
            if (user != null) {
                return user;
            }
        } catch (UnavailableSecurityManagerException e) {
            logger.error("SingletonLoginUtils.getUser:{}", e);
        }
        return null;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static Integer getSysUserId() {
        try {
            Subject subject = SecurityUtils.getSubject();
            SysUserInfo userInfo = (SysUserInfo) subject.getPrincipal();
            if (null != userInfo) {
                return userInfo.getId();
            }
        } catch (UnavailableSecurityManagerException e) {
            logger.error("SingletonLoginUtils.getUser:{}", e);
        }
        return null;
    }
}
