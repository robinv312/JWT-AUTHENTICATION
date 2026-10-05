package com.telusko.springsecdemo.controller;

import com.telusko.springsecdemo.model.User;
import com.telusko.springsecdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    @PostMapping("register")
    public User register(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userService. register(user);
    }
}
