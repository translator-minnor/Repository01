package com.minnow.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * financial_products
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "FinancialProducts")

public class FinancialProducts implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品介绍
     */
    private String briefInfo;

    /**
     * 产品卡号
     */
    private Integer cardNo;

    private static final long serialVersionUID = 1L;
}