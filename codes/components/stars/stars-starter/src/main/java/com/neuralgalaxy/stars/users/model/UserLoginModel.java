package com.neuralgalaxy.stars.users.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 用户登录信息
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@Data
@Tag(name = "userLogin", description = "用户登录信息")
public class UserLoginModel {

    String org;
    String username;
    String passwd;
}
