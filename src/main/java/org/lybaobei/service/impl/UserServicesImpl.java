package org.lybaobei.service.impl;

import java.util.List;
import org.lybaobei.entity.Users;
import org.lybaobei.mapper.UserMapper;
import org.lybaobei.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    private final UserMapper userMapper;

    @Autowired
    public UserServicesImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public List<Users> findAll() {
        return userMapper.selectList(null);
    }
}
