package com.minnow.vo;

import com.minnow.domain.PermMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuAndPermsVo {

    @ApiModelProperty("菜单集合")
    private Set<PermMenu> menuList;

    @ApiModelProperty("权限集合")
    private Set<String> authorities;
}
