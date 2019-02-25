package com.server.file.service;

import com.server.file.dao.UserMapper;
import com.server.file.model.User;
import com.server.file.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Auther: Leo
 * @Date: 2019/2/25
 * @Description:
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void register(User user){
        User u = userMapper.findUserByUserName(user.getUsername());
        if (u == null){
            user.setId(UUID.randomUUID().toString().replaceAll("-",""));
            userMapper.insertSelective(user);
        }

    }
}
