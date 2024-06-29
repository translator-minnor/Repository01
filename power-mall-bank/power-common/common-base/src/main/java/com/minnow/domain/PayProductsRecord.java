package com.minnow.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pay_products_record
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "PayProductsRecord")
public class PayProductsRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 所属产品id
     */
    private Integer productId;

    /**
     * 交易用户卡号id
     */
    private Integer cardId;

    /**
     * 交易用户id
     */
    private Integer userId;

    /**
     * 交易类型：1购买/2出售
     */
    private Integer payStatus;

    /**
     * 交易量
     */
    private Integer payNum;

    /**
     * 交易金额
     */
    private BigDecimal sumMoney;

    /**
     * 交易时间
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}