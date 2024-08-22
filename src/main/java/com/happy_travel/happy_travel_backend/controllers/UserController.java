package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("login")
    public String login(@RequestBody String entity) {
        return "Login from public endpoint";
    }
    
    @PostMapping("register")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
