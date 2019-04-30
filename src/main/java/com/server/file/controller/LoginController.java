package com.server.file.controller;

import com.server.file.DTO.AccountDTO;
import com.server.file.DTO.ResultTO;
import com.server.file.exception.CommonException;
import com.server.file.exception.LoginException;
import com.server.file.model.User;
import com.server.file.service.LoginService;
import com.server.file.service.MailService;
import com.server.file.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@RestController
@RequestMapping("/account")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultTO login(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountDTO accountDTO){
//        String token = request.getHeader("access_token");
        ResultTO resultTO = new ResultTO();
        if (StringUtils.isEmpty(accountDTO.getUsername())){
            throw new CommonException("用户名不能为空");
        }
        if (StringUtils.isEmpty(accountDTO.getPassword())){
            throw new CommonException("密码不能为空");
        }
        String token = loginService.login(accountDTO.getUsername(),accountDTO.getPassword());
        if (StringUtils.isNotEmpty(token)){
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            return resultTO;
        }else {
            throw new LoginException("登录失败");
        }
    }

    @RequestMapping(value = "/login/info/get",method = RequestMethod.GET)
    public ResultTO getLoginInfo(HttpServletRequest request){
        String token = request.getHeader("access_token");
        ResultTO resultTO = new ResultTO();
        AccountDTO accountDTO = loginService.getLoginInfo(token);
        resultTO.setResult(accountDTO);
        return resultTO;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultTO register(@RequestBody User user){
        ResultTO resultTO = new ResultTO();
        loginService.register(user);
        return resultTO;
    }

    @RequestMapping(value = "/send/mail",method = RequestMethod.GET)
    public ResultTO sendMail(@RequestParam("email") String email){
        ResultTO resultTO = new ResultTO();
        if (redisUtil.hasKey("mail:"+ email)){
            throw new CommonException("60s内不可重发邮件");
        }else {
            String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
            redisUtil.set("mail:"+ email,checkCode,60);
            String message = "您的注册验证码为："+checkCode+" (验证码15分钟有效)";
            try {
                mailService.sendSimpleMail(email, "注册验证码", message);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            if (redisUtil.hasKey("checkCode:"+ email)){
                redisUtil.del("checkCode:"+ email);
            }
            redisUtil.set("checkCode:"+ email,checkCode,900);
            return resultTO;
        }
    }


}
