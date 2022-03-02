package com.neuralgalaxy.stars.auth.service;

import com.neuralgalaxy.stars.auth.model.UserTokenModel;

/**
 * 用户权限设置
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
public interface AuthService {

    /**
     * 解析token数据为用户操作
     *
     * @param token jwt token
     * @return 用户模型
     */
    UserTokenModel decodeToken(String token);

    /**
     * 检查用户是否含有权限
     * @param id 用户ID
     * @param applicationName 项目名称
     * @param authority 权限
     * @return 是否包含权限
     */
    boolean hasAuthority(long id, String applicationName, String authority);
}
