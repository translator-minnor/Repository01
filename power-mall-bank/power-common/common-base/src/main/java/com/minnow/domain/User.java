package com.minnow.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "User")
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @TableField(value = "password",updateStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 权限：1管理员/2普通用户/3尊贵用户
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 创建者ID
     */
    @TableField(value = "create_user_id")
    @ApiModelProperty(value="创建者ID")
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}