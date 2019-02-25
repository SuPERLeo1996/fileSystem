package com.server.file.controller;

import com.server.file.model.User;
import com.server.file.model.UserExample;
import com.server.file.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: Leo
 * @Date: 2019/1/27
 * @Description:
 */
@Controller
@RequestMapping("/server/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register/user", method = RequestMethod.POST)
    public String userRegister(@RequestBody User user) {
        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            userService.register(user);
            return "registerSuccess";
        }
        return null;
    }
}
