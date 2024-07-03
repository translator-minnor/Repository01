package com.minnow.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
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
    private Integer id;

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
     * 权限：1管理员/2普通用户/3尊贵用户
     */
    @TableField(value = "role_id")
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}