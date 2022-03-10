package com.neuralgalaxy.stars.users.dao.mapper;

import com.neuralgalaxy.commons.mysql.ExtensionMapper;
import com.neuralgalaxy.stars.users.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Mapper
public interface UserMapper extends ExtensionMapper<UserEntity> {

    UserEntity selectByName(@Param("username") String username);

}
