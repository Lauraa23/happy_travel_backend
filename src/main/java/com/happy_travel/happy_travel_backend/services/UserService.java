package com.happy_travel.happy_travel_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;

@Service
public class UserService {    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) { 
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return userRepository.save(user); 
    } 
    public Optional<User> findByName(String name) { 
        return userRepository.findByName(name); 
    }

    
}
