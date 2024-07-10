package com.minnow.dto;

import com.minnow.domain.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleAddOrUpdateParam extends SysRole {
    @ApiModelProperty("权限ID集合")
    private List<Long> menuIdList;
}
