package com.zyxx.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author zxy
 * @since 2020-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_configure")
@ApiModel(value = "SysConfigure对象", description = "系统设置")
public class SysConfigure extends Model<SysConfigure> {


    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "系统名称(简称)")
    @TableField("system_name")
    private String systemName;

    @ApiModelProperty(value = "系统名称(全称)")
    @TableField("system_full_name")
    private String systemFullName;

    @ApiModelProperty(value = "系统logo")
    @TableField("system_logo")
    private String systemLogo;

    @ApiModelProperty(value = "网站标题")
    @TableField("website_title")
    private String websiteTitle;

    @ApiModelProperty(value = "网站图标")
    @TableField("website_icon")
    private String websiteIcon;

    @ApiModelProperty(value = "网站关键字")
    @TableField("website_keywords")
    private String websiteKeywords;

    @ApiModelProperty(value = "网站描述")
    @TableField("website_description")
    private String websiteDescription;

    @ApiModelProperty(value = "网站备案号")
    @TableField("record_no")
    private String recordNo;

    @ApiModelProperty(value = "网站图版权信息")
    @TableField("copyright")
    private String copyright;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建者")
    @TableField("create_user")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private Integer updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
