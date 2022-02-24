package com.neuralgalaxy.stars.user.service;

import com.neuralgalaxy.stars.user.dto.UserDo;

import java.util.Optional;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220217
 */
public interface UserService {

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserDo getUser(long userId);

    /**
     * 根据username/email获取用户信息
     *
     * @param name username/email
     * @return user
     */
    UserDo getUserByName(String name);
}
