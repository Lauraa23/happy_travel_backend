package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happy_travel.happy_travel_backend.config.jwt.JwtService;
import com.happy_travel.happy_travel_backend.models.AuthResponse;
import com.happy_travel.happy_travel_backend.models.LoginRequest;
import com.happy_travel.happy_travel_backend.models.RegisterRequest;
import com.happy_travel.happy_travel_backend.models.User;

import com.happy_travel.happy_travel_backend.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
    
    @PostMapping(value = "register")
    public AuthResponse registerUser(@RequestBody RegisterRequest request) {
        User newCreatedUser = userService.register(request);
        return AuthResponse.builder()
        .token(jwtService.getToken(newCreatedUser))
        .build();
    }
}
