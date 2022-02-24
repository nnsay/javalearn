package com.neuralgalaxy.stars.users.dao.mapper;

import com.neuralgalaxy.commons.mysql.ExtensionMapper;
import com.neuralgalaxy.stars.user.dto.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Mapper
public interface UserMapper extends ExtensionMapper<UserDo> {

    UserDo selectByName(@Param("username") String username);

    @Select("select * from Users where email = #{email}")
    UserDo selectByEmail(@Param("email") String email);
}
