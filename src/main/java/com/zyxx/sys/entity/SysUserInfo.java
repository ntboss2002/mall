package com.zyxx.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyxx.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zxy
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_info")
@ApiModel(value = "SysUserInfo对象", description = "用户信息表")
public class SysUserInfo extends Model<SysUserInfo> {


    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "登录账号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "登录密码")
    @TableField("password")
    private String password;

    @Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @Excel(name = "性别", width = 15, dicCode = "sex")
    @ApiModelProperty(value = "性别（0--未知1--男2--女）")
    @TableField("sex")
    @Dict(dictCode = "user_sex")
    private Integer sex;

    @Excel(name = "状态", width = 15, dicCode = "status")
    @ApiModelProperty(value = "状态（0--正常1--冻结）")
    @TableField("status")
    @Dict(dictCode = "user_status")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    @TableField("update_user")
    private Integer updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
