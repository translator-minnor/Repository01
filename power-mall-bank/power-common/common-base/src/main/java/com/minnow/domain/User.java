package com.minnow.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 权限：1管理员/2普通用户/3尊贵用户
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}