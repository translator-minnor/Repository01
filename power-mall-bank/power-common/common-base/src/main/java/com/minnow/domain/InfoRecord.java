package com.minnow.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * info_record
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "InfoRecord")
public class InfoRecord implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private Date date;

    private static final long serialVersionUID = 1L;
}