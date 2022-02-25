package com.neuralgalaxy.stars.user.service;


import com.neuralgalaxy.stars.user.model.UserLoginModel;
import com.neuralgalaxy.stars.user.model.UserModel;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220217
 */
public interface UserService {

    /**
     * 用户登录
     * @param login form
     * @return token
     */
    String login(UserLoginModel login);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserModel getUser(long userId);

    /**
     * 根据username/email获取用户信息
     *
     * @param name username/email
     * @return user
     */
    UserModel getUserByName(String name);
}
