package com.zyxx.common.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyxx.sys.entity.SysUserInfo;
import com.zyxx.sys.service.SysRolePermissionService;
import com.zyxx.sys.service.SysUserInfoService;
import com.zyxx.sys.service.SysUserRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录授权
 *
 * @Author zxy
 */
public class LoginRelam extends AuthorizingRealm {

    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 身份认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取基于用户名和密码的令牌：实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名查找到用户信息
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserInfo::getAccount, token.getUsername());
        SysUserInfo userInfo = sysUserInfoService.getOne(queryWrapper);
        // 没找到帐号
        if (null == userInfo) {
            throw new UnknownAccountException();
        }
        // 校验用户状态
        if (userInfo.getStatus().equals(1)) {
            throw new DisabledAccountException();
        }
        // 认证缓存信息
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getAccount()), getName());
    }

    /**
     * 角色授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUserInfo authorizingUser = (SysUserInfo) principalCollection.getPrimaryPrincipal();
        if (null != authorizingUser) {
            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            // 获得用户角色列表
            simpleAuthorizationInfo.addRoles(sysUserRoleService.listUserRoleByUserId(authorizingUser.getId()));
            // 获得权限列表
            simpleAuthorizationInfo.addStringPermissions(sysRolePermissionService.listRolePermissionByUserId(authorizingUser.getId()));
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 自定义加密规则
     *
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        // 自定义认证加密方式
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        // 设置自定义认证加密方式
        super.setCredentialsMatcher(customCredentialsMatcher);
    }
}
