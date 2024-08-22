package com.happy_travel.happy_travel_backend.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.happy_travel.happy_travel_backend.models.AuthResponse;
import com.happy_travel.happy_travel_backend.models.LoginRequest;
import com.happy_travel.happy_travel_backend.models.RegisterRequest;
import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {    
    private UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    /*public User saveUser(User user) { 
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return userRepository.save(user); 
    } */
    public AuthResponse register(RegisterRequest request) { 
        //request.setPassword(passwordEncoder.encode(request.getPassword())); 
        User user = User.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .name(request.getName())
            .build();
        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getTocken(user))
            .build();
    } 

    public AuthResponse login(LoginRequest request){
        return null;
    }

    public Optional<User> findByName(String name) { 
        return userRepository.findByName(name); 
    }

    
}
