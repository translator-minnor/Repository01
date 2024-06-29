package com.minnow.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * card
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "Card")
public class Card implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 信用卡密码
     */
    private String password;

    /**
     * 权限：1可用/2冻结/3挂失
     */
    private Integer status;

    /**
     * 卡内余额
     */
    private BigDecimal money;

    private static final long serialVersionUID = 1L;
}