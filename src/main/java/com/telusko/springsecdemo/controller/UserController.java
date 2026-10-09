package com.telusko.springsecdemo.controller;

import com.telusko.springsecdemo.model.User;
import com.telusko.springsecdemo.service.JwtService;
import com.telusko.springsecdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    @PostMapping("register")
    public User register(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Authenticate user (logic not shown)
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));
        boolean isAuthenticated = true; // Replace with actual authentication logic
        if (isAuthenticated) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "Login Failed";
        }
    }
}
