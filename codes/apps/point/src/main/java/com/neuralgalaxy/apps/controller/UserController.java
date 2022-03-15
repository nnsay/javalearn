package com.neuralgalaxy.apps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neuralgalaxy.apps.dao.mapper.UserMapper;
import com.neuralgalaxy.apps.dao.entity.User;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public List<User> getUserList() {
        QueryWrapper<User> query = Wrappers.<User>query();
        query.gt("id", 1).isNotNull("firstName");
        return userMapper.selectList(query);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
        return userMapper.selectById(userId);
    }

}
