package com.minnow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员查询条件参数对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserQueryParam {
    private String username;

    private Long id;
}
