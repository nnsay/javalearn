package com.neuralgalaxy.stars.users.service;

import com.neuralgalaxy.stars.user.dto.UserDo;
import com.neuralgalaxy.stars.user.service.UserService;
import com.neuralgalaxy.stars.users.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 用户服务
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220217
 */
@Slf4j
@DubboService
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDo getUser(long userId) {
        log.debug("get user {}", userId);
        return userMapper.selectById(userId);
    }

    @Override
    public UserDo getUserByName(String name) {
        UserDo userDo = null;
        //email
        if (name.contains("@")) {
            //DEMO userDo = userMapper.selectOne((q) -> q.eq(UserDo::getEmail, name));
            userDo = userMapper.selectByEmail(name);
        }
        //name
        else {
            userDo = userMapper.selectByName(name);
        }
        return userDo;
    }
}
