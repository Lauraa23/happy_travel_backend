package com.happy_travel.happy_travel_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy_travel.happy_travel_backend.models.AuthResponse;
import com.happy_travel.happy_travel_backend.models.LoginRequest;
import com.happy_travel.happy_travel_backend.models.RegisterRequest;
import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;


@Service
//@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User register(RegisterRequest request) { 
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .build();

        User resultingUser = userRepository.save(user);
        System.out.println(resultingUser);
        return user;
    } 

    public AuthResponse login(LoginRequest request){
        return null;
    }

    
}
