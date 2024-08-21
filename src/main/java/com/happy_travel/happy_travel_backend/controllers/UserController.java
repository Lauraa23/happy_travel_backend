package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
    
        return userService.saveUser(user);

        
    }
}
