package com.neuralgalaxy.stars.users.service;

import com.neuralgalaxy.stars.user.model.UserModel;
import com.neuralgalaxy.stars.users.dao.entity.UserEntity;
import com.neuralgalaxy.stars.user.service.UserService;
import com.neuralgalaxy.stars.users.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;

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

    private static final BeanCopier COPIER = BeanCopier.create(UserModel.class, UserEntity.class, false);
    private static final BeanCopier COPIER2 = BeanCopier.create(UserEntity.class, UserModel.class, false);

    private UserModel toModel(UserEntity entity) {
        UserModel userModel = new UserModel();
        COPIER2.copy(entity, userModel, null);
        return userModel;
    }

    @Override
    public UserModel getUser(long userId) {
        log.debug("get user {}", userId);
        UserEntity userEntity = userMapper.selectById(userId);
        return toModel(userEntity);
    }

    @Override
    public UserModel getUserByName(String name) {
        UserEntity entity = null;
        //email
        if (name.contains("@")) {
            entity = userMapper.selectOne((q) -> q.eq(UserEntity::getEmail, name));
        }
        //name
        else {
            entity = userMapper.selectByName(name);
        }
        return toModel(entity);
    }

}
