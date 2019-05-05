package com.server.file.service;

import com.alibaba.fastjson.JSON;
import com.server.file.DTO.AccountDTO;
import com.server.file.dao.UserMapper;
import com.server.file.exception.CommonException;
import com.server.file.model.User;
import com.server.file.model.UserExample;
import com.server.file.util.MD5Util;
import com.server.file.util.RedisUtil;
import com.server.file.util.StringCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public String login(String username,String password){
        User user;
        if (StringCheckUtil.isEmail(username)){
            user = userMapper.findUserByEmail(username);
        }else {
            user = userMapper.findUserByUserName(username);
        }
        if (user == null){
            throw new CommonException("用户不存在");
        }
        AccountDTO accountDTO = new AccountDTO();
        if (MD5Util.getSaltverifyMD5(password,user.getPassword())){
            String token = UUID.randomUUID().toString().replace("-","");
            accountDTO.setUsername(username);
            accountDTO.setPassword(password);
            accountDTO.setEmail(user.getEmail());
            accountDTO.setPhone(user.getPhone());
            accountDTO.setToken(token);
            redisUtil.set(token, JSON.toJSONString(accountDTO),86400);
            return token;
        }else {
            throw new CommonException("密码错误");
        }
    }

    public AccountDTO getLoginInfo(String token){
        if (redisUtil.hasKey(token)){
            AccountDTO accountDTO = JSON.parseObject(redisUtil.get(token).toString(),AccountDTO.class);
            return accountDTO;
        }else {
            return null;
        }
    }

    public void register(User user){
        if (redisUtil.hasKey("checkCode:"+ user.getEmail())){
            String code = (String) redisUtil.get("checkCode:"+ user.getEmail());
            if (code.equals(user.getCode())){
                UserExample userExample = new UserExample();
                userExample.createCriteria().andUsernameEqualTo(user.getUsername());
                long count = userMapper.countByExample(userExample);
                if (count == 0){
                    user.setId(UUID.randomUUID().toString().replaceAll("-",""));
                    user.setPassword(MD5Util.getSaltMD5(user.getPassword()));
                    userMapper.insertSelective(user);
                }else {
                    throw new CommonException("用户已存在!");
                }
            }else {
                throw new CommonException("邮箱验证码错误!");
            }
        }else {
            throw new CommonException("邮箱验证码失效");
        }
    }
}
