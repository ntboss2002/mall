package com.zyxx.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyxx.common.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务信息表
 * </p>
 *
 * @author zxy
 * @since 2020-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_quartz")
@ApiModel(value="SysQuartz对象", description="定时任务信息表")
public class SysQuartz extends Model<SysQuartz> {


    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "任务类名")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "cron表达式")
    @TableField("cron_expression")
    private String cronExpression;

    @ApiModelProperty(value = "参数")
    @TableField("param")
    private String param;

    @ApiModelProperty(value = "描述")
    @TableField("descript")
    private String descript;

    @ApiModelProperty(value = "启动状态（0--启动1--停止）")
    @TableField("quartz_status")
    @Dict(dictCode = "quartz_status")
    private Integer quartzStatus;

    @ApiModelProperty(value = "状态（0--正常1--停用）")
    @TableField("status")
    @Dict(dictCode = "quartz_data_status")
    private Integer status;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
