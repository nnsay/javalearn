package com.neuralgalaxy.apps.dao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neuralgalaxy.apps.dao.entity.User;
import com.neuralgalaxy.apps.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService{
}
