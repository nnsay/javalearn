package com.neuralgalaxy.stars.users.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.neuralgalaxy.stars.user.dto.UserDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220224
 */
@ApiModel(value = "UserVo", description = "用户信息表")
public class UserVo extends UserDo {

    @Override
    @JSONField(serialize = false)
    @ApiModelProperty(hidden = true)
    public String getPassword() {
        return super.getPassword();
    }
}
