package com.zyxx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.consts.SystemConst;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysUserRole;
import com.zyxx.sys.mapper.SysUserRoleMapper;
import com.zyxx.sys.service.SysUserRoleService;
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
 * 用户-角色关系表 服务实现类
 * </p>
 *
 * @author zxy
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Set<String> listUserRoleByUserId(Integer userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_ROLE);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = sysUserRoleMapper.listUserRoleByUserId(userId);
        session.setAttribute(SystemConst.SYSTEM_USER_ROLE, set);
        return set;
    }

    @Override
    public ResponseResult auth(Integer id, String roles) {
        // 先删除
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, id);
        this.remove(queryWrapper);
        // 保存用户角色
        if (StringUtils.isNotBlank(roles)) {
            String[] roleArr = roles.split(",");
            List<SysUserRole> list = new ArrayList<>();
            SysUserRole sysUserRole = null;
            for (String item : roleArr) {
                sysUserRole = new SysUserRole();
                sysUserRole.setUserId(id);
                sysUserRole.setRoleId(Integer.parseInt(item));
                list.add(sysUserRole);
            }
            this.saveBatch(list);
        }
        return ResponseResult.success();
    }
}
