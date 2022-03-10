package com.neuralgalaxy.stars.users.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录信息
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@Data
public class UserLoginModel implements Serializable {

    String org;

    String username;

    String passwd;
}
