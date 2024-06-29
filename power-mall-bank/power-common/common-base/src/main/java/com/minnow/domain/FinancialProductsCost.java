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
 * financial_products_cost
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "FinancialProductsCost")
public class FinancialProductsCost implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 所属产品id
     */
    private Integer productId;

    /**
     * 每笔价格
     */
    private BigDecimal price;

    /**
     * 生效日期
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}