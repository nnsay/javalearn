package com.neuralgalaxy.stars.users.service;

import com.neuralgalaxy.commons.asserts.Asserts;
import com.neuralgalaxy.commons.asserts.GlobalErrors;
import com.neuralgalaxy.commons.utilities.Copier;
import com.neuralgalaxy.commons.visitor.jwt.VisitorSerializer;
import com.neuralgalaxy.stars.user.model.UserLoginModel;
import com.neuralgalaxy.stars.user.model.UserModel;
import com.neuralgalaxy.stars.users.UserErrors;
import com.neuralgalaxy.stars.users.config.UserConfiguration;
import com.neuralgalaxy.stars.users.dao.entity.UserEntity;
import com.neuralgalaxy.stars.user.service.UserService;
import com.neuralgalaxy.stars.users.dao.mapper.UserMapper;
import com.neuralgalaxy.stars.auth.model.UserTokenModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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

    @Autowired
    VisitorSerializer serializer;

    @Autowired
    UserConfiguration config;

    private static final Copier<UserModel, UserEntity> COPIER = Copier.create(UserModel::new, UserEntity::new);

    @Override
    public String login(UserLoginModel login) {
        Asserts.notEmpty(login.getUsername(), GlobalErrors.BAD_REQUEST);
        Asserts.isTrue(!config.isLoginMustWithOrgName() || StringUtils.hasText(login.getOrg()), GlobalErrors.BAD_REQUEST);

        if (config.isLoginMustWithOrgName()) {
            //do something
        }

        UserEntity user = null;
        if (login.getUsername().contains("@")) {
            user = userMapper.selectOne(w -> w.eq(UserEntity::getEmail, login.getUsername()));
        } else {
            user = userMapper.selectByName(login.getUsername());
        }
        Asserts.notNull(user, UserErrors.NOT_FOUND);

        //it just for test demo
        Asserts.isTrue("testiest".equals(login.getPasswd()), UserErrors.NOT_FOUND);

        UserTokenModel visitor = new UserTokenModel();
        visitor.setId(user.getId());
        visitor.setOrgId(user.getOrgId());
        return serializer.encode(visitor);
    }

    @Override
    public UserModel getUser(long userId) {
        log.debug("get user {}", userId);
        UserEntity userEntity = userMapper.selectById(userId);
        return COPIER.toModel(userEntity);
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
        return COPIER.toModel(entity);
    }

}
