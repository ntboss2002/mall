package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.consts.SystemConst;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysRolePermission;
import com.zyxx.sys.mapper.SysRolePermissionMapper;
import com.zyxx.sys.service.SysRolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色-权限关系表 服务实现类
 * </p>
 *
 * @author zxy
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper permissionMapper;

    @Override
    public ResponseResult auth(Integer id, String perms) {
        // 先删除
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRolePermission::getRoleId, id);
        remove(queryWrapper);
        // 保存角色权限
        if (StringUtils.isNotBlank(perms)) {
            String[] roleArr = perms.split(",");
            List<SysRolePermission> list = new ArrayList<>();
            SysRolePermission sysRolePermission = null;
            for (String item : roleArr) {
                sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(id);
                sysRolePermission.setPermissionId(Integer.parseInt(item));
                list.add(sysRolePermission);
            }
            this.saveBatch(list);
        }
        return ResponseResult.success();
    }

    @Override
    public Set<String> listRolePermissionByUserId(Integer userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_PERMISSION);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = permissionMapper.listRolePermissionByUserId(userId);
        session.setAttribute(SystemConst.SYSTEM_USER_PERMISSION, set);
        return set;
    }
}
