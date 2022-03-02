package com.neuralgalaxy.stars.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录信息
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@Data
@ApiModel(description = "用户登录信息")
public class UserLoginModel implements Serializable {

    @ApiModelProperty
    String org;

    @ApiModelProperty(value = "用户名", required = false)
    String username;

    @ApiModelProperty(value = "用户密码", required = false)
    String passwd;
}
