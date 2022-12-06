package com.zyxx.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyxx.common.consts.SystemConst;
import com.zyxx.common.shiro.SingletonLoginUtils;
import com.zyxx.common.utils.ResponseResult;
import com.zyxx.sys.entity.SysPermissionInfo;
import com.zyxx.sys.mapper.SysPermissionInfoMapper;
import com.zyxx.sys.pojo.LayuiTreePojo;
import com.zyxx.sys.pojo.MenuPojo;
import com.zyxx.sys.pojo.PermissionPojo;
import com.zyxx.sys.service.SysPermissionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2020-07-04
 */
@Service
public class SysPermissionInfoServiceImpl extends ServiceImpl<SysPermissionInfoMapper, SysPermissionInfo> implements SysPermissionInfoService {

    @Autowired
    private SysPermissionInfoMapper sysPermissionInfoMapper;

    @Override
    public List<SysPermissionInfo> list() {
        LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysPermissionInfo::getType).orderByAsc(SysPermissionInfo::getPid).orderByAsc(SysPermissionInfo::getSort);
        return this.list(queryWrapper);
    }

    @Override
    public ResponseResult add(JSONObject param) {
        SysPermissionInfo sysPermissionInfo = JSONObject.parseObject(param.getString("data"), SysPermissionInfo.class);
        if (StringUtils.isBlank(sysPermissionInfo.getName())) {
            return ResponseResult.error("请输入菜单名称");
        }
        if (0 != sysPermissionInfo.getType() && StringUtils.isBlank(sysPermissionInfo.getSign())) {
            return ResponseResult.error("请输入权限标识");
        }
        if (StringUtils.isNotBlank(sysPermissionInfo.getSign())) {
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysPermissionInfo::getSign, sysPermissionInfo.getSign());
            List<SysPermissionInfo> sysPermissionInfos = this.list(queryWrapper);
            if (null != sysPermissionInfos && !sysPermissionInfos.isEmpty()) {
                return ResponseResult.error("该权限标识已经存在，请检查后提交");
            }
        }
        sysPermissionInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
        this.save(sysPermissionInfo);
        PermissionPojo permissionPojo = JSONObject.parseObject(param.getString("pojo"), PermissionPojo.class);
        if (null != permissionPojo) {
            // 按钮集合
            List<SysPermissionInfo> btnList = new ArrayList<>();
            SysPermissionInfo btnObj = null;
            if (null != permissionPojo.getShow() && 1 == permissionPojo.getShow()) {
                btnObj = new SysPermissionInfo();
                btnObj.setPid(sysPermissionInfo.getId());
                btnObj.setName("查看");
                btnObj.setType(2);
                btnObj.setSign(sysPermissionInfo.getSign().substring(0, sysPermissionInfo.getSign().lastIndexOf(":")) + ":show");
                btnObj.setSort(0);
                sysPermissionInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getAdd() && 1 == permissionPojo.getAdd()) {
                btnObj = new SysPermissionInfo();
                btnObj.setPid(sysPermissionInfo.getId());
                btnObj.setName("新增");
                btnObj.setType(2);
                btnObj.setSign(sysPermissionInfo.getSign().substring(0, sysPermissionInfo.getSign().lastIndexOf(":")) + ":add");
                btnObj.setSort(1);
                sysPermissionInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getEdit() && 1 == permissionPojo.getEdit()) {
                btnObj = new SysPermissionInfo();
                btnObj.setPid(sysPermissionInfo.getId());
                btnObj.setName("修改");
                btnObj.setType(2);
                btnObj.setSign(sysPermissionInfo.getSign().substring(0, sysPermissionInfo.getSign().lastIndexOf(":")) + ":edit");
                btnObj.setSort(2);
                sysPermissionInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getDel() && 1 == permissionPojo.getDel()) {
                btnObj = new SysPermissionInfo();
                btnObj.setPid(sysPermissionInfo.getId());
                btnObj.setName("删除");
                btnObj.setType(2);
                btnObj.setSign(sysPermissionInfo.getSign().substring(0, sysPermissionInfo.getSign().lastIndexOf(":")) + ":del");
                btnObj.setSort(3);
                sysPermissionInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getExport() && 1 == permissionPojo.getExport()) {
                btnObj = new SysPermissionInfo();
                btnObj.setPid(sysPermissionInfo.getId());
                btnObj.setName("导出");
                btnObj.setType(2);
                btnObj.setSign(sysPermissionInfo.getSign().substring(0, sysPermissionInfo.getSign().lastIndexOf(":")) + ":export");
                btnObj.setSort(4);
                sysPermissionInfo.setCreateUser(SingletonLoginUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (CollectionUtil.isNotEmpty(btnList)) {
                this.saveBatch(btnList);
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysPermissionInfo sysPermissionInfo) {
        if (StringUtils.isBlank(sysPermissionInfo.getName())) {
            return ResponseResult.error("请输入菜单名称");
        }
        if (1 != sysPermissionInfo.getType() && StringUtils.isBlank(sysPermissionInfo.getSign())) {
            return ResponseResult.error("请输入权限标识");
        }
        if (StringUtils.isNotBlank(sysPermissionInfo.getSign())) {
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ne(SysPermissionInfo::getId, sysPermissionInfo.getId());
            queryWrapper.eq(SysPermissionInfo::getSign, sysPermissionInfo.getSign());
            List<SysPermissionInfo> sysPermissionInfos = this.list(queryWrapper);
            if (null != sysPermissionInfos && !sysPermissionInfos.isEmpty()) {
                return ResponseResult.error("该权限标识已经存在，请检查后提交");
            }
        }
        sysPermissionInfo.setIcon("fa " + sysPermissionInfo.getIcon());
        this.updateById(sysPermissionInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Integer id) {
        SysPermissionInfo sysPermissionInfo = this.getById(id);
        removeById(id);
        // 如果还有子集
        if (2 > sysPermissionInfo.getType()) {
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysPermissionInfo::getPid, id);
            List<SysPermissionInfo> list = this.list(queryWrapper);
            if (null != list && !list.isEmpty()) {
                // 如果是一级
                if (0 == sysPermissionInfo.getType()) {
                    for (SysPermissionInfo item : list) {
                        queryWrapper.eq(SysPermissionInfo::getPid, item.getId());
                        this.remove(queryWrapper);
                    }
                } else {
                    // 批量删除
                    this.remove(queryWrapper);
                }
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Integer id, Integer status) {
        SysPermissionInfo sysPermissionInfo = this.getById(id);
        sysPermissionInfo.setStatus(status);
        this.updateById(sysPermissionInfo);
        // 如果还有子集
        if (2 > sysPermissionInfo.getType()) {
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysPermissionInfo::getPid, id);
            queryWrapper.ne(SysPermissionInfo::getStatus, status);
            List<SysPermissionInfo> list = this.list(queryWrapper);
            if (null != list && !list.isEmpty()) {
                // 如果是一级
                if (0 == sysPermissionInfo.getType()) {
                    List<SysPermissionInfo> child = new ArrayList<>();
                    for (SysPermissionInfo item : list) {
                        item.setStatus(status);
                        queryWrapper.eq(SysPermissionInfo::getPid, item.getId());
                        child.addAll(list(queryWrapper));
                    }
                    list.addAll(child);
                } else {
                    for (SysPermissionInfo item : list) {
                        item.setStatus(status);
                    }
                }
                // 批量修改状态
                this.updateBatchById(list);
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateSort(Integer id, Integer sort) {
        if (null == id || 0 == id || null == sort) {
            return ResponseResult.error("数据错误");
        }
        SysPermissionInfo sysPermissionInfo = this.getById(id);
        if (null == sysPermissionInfo) {
            return ResponseResult.error("数据错误");
        }
        if (sort.equals(sysPermissionInfo.getSort())) {
            return ResponseResult.success();
        }
        sysPermissionInfo.setSort(sort);
        this.updateById(sysPermissionInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult listPermissionInfoByPid(Integer type, Integer pid) {
        LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPermissionInfo::getType, type);
        queryWrapper.eq(SysPermissionInfo::getStatus, 0);
        if (null != pid && 0 < pid) {
            queryWrapper.eq(SysPermissionInfo::getPid, pid);
        }
        queryWrapper.orderByAsc(SysPermissionInfo::getSort);
        return ResponseResult.success(this.list(queryWrapper));
    }

    @Override
    public String initMenu(HttpSession session) {
        Integer userId = SingletonLoginUtils.getSysUserId();
        if (null == userId || 0 == userId) {
            return null;
        }
        List<MenuPojo> list = sysPermissionInfoMapper.listPermissionInfoByUserId(userId);
        if (null == list || list.isEmpty()) {
            ResponseResult.error("用户信息异常");
        }
        // 需要返回的list
        List<MenuPojo> resList = new ArrayList<>();
        // 遍历list
        Iterator<MenuPojo> iter = list.iterator();
        // 先取出第一层
        MenuPojo menuPojo = null;
        while (iter.hasNext()) {
            menuPojo = iter.next();
            if (0 == menuPojo.getType()) {
                menuPojo.setChild(new ArrayList<>());
                resList.add(menuPojo);
                iter.remove();
            }
        }
        if (null == list || list.isEmpty() || null == resList || resList.isEmpty()) {
            return null;
        }
        List<MenuPojo> child = null;
        for (MenuPojo item : list) {
            for (MenuPojo temp : resList) {
                if (item.getPid().equals(temp.getId())) {
                    child = temp.getChild();
                    child.add(item);
                    temp.setChild(child);
                    break;
                }
            }
        }
        menuPojo = new MenuPojo();
        menuPojo.setTitle("");
        menuPojo.setIcon("");
        menuPojo.setHref("");
        menuPojo.setChild(resList);
        child = new ArrayList<>();
        child.add(menuPojo);
        // 返回的JSON
        JSONObject resJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "首页");
        jsonObject.put("href", "welcome");
        resJson.put("homeInfo", jsonObject);
        resJson.put("menuInfo", child);
        // 将用户权限信息缓存到session中
        session.setAttribute(SystemConst.SYSTEM_USER_MENU, resJson);
        return resJson.toJSONString();
    }

    @Override
    public String listPermissionForTree(Integer roleId) {
        JSONObject resJson = new JSONObject();
        List<LayuiTreePojo> resList = new ArrayList<>();
        List<LayuiTreePojo> list = sysPermissionInfoMapper.listPermissionForTree(roleId);
        if (null == list || list.isEmpty()) {
            resJson.put("data", null);
            return resJson.toJSONString();
        }
        List<LayuiTreePojo> list1 = new ArrayList<>();
        List<LayuiTreePojo> list2 = new ArrayList<>();
        for (LayuiTreePojo item : list) {
            // 一级菜单
            if (0 == item.getType()) {
                item.setChildren(new ArrayList<>());
                resList.add(item);
            }
            // 二级菜单
            if (1 == item.getType()) {
                item.setChildren(new ArrayList<>());
                list1.add(item);
            }
            // 三级菜单
            if (2 == item.getType()) {
                list2.add(item);
            }
        }
        List<LayuiTreePojo> child = null;
        if (null == list1 || list1.isEmpty()) {
            resJson.put("data", resList);
            return resJson.toJSONString();
        } else if (null == list2 || list2.isEmpty()) {
            for (LayuiTreePojo item : resList) {
                for (LayuiTreePojo temp : list1) {
                    if (item.getId().equals(temp.getPid())) {
                        item.setChecked(false);
                        child = item.getChildren();
                        child.add(temp);
                        item.setChildren(child);
                    }
                }
            }
            resJson.put("data", resList);
            return resJson.toJSONString();
        }
        for (LayuiTreePojo item : list1) {
            for (LayuiTreePojo temp : list2) {
                if (item.getId().equals(temp.getPid())) {
                    item.setChecked(false);
                    child = item.getChildren();
                    child.add(temp);
                    item.setChildren(child);
                }
            }
        }
        for (LayuiTreePojo item : resList) {
            for (LayuiTreePojo temp : list1) {
                if (item.getId().equals(temp.getPid())) {
                    item.setChecked(false);
                    child = item.getChildren();
                    child.add(temp);
                    item.setChildren(child);
                }
            }
        }
        resJson.put("data", resList);
        return resJson.toJSONString();
    }

}
