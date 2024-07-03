package com.minnow.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登录结果对象")
public class LoginResult {
    @ApiModelProperty("令牌token")
    private String accessToken;

    @ApiModelProperty("有效时长")
    private Long expireIn;
}
