package com.happy_travel.happy_travel_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) { 
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return userRepository.save(user); 
    } 
    public User findByUsername(String username) { 
        return userRepository.findByUsername(username); 
    }

    
}
