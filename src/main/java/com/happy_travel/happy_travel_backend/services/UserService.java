package com.happy_travel.happy_travel_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.happy_travel.happy_travel_backend.models.AuthResponse;
import com.happy_travel.happy_travel_backend.models.LoginRequest;
import com.happy_travel.happy_travel_backend.models.RegisterRequest;
import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    /*public UserService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }*/
    public User register(RegisterRequest request) { 
        String encodedPassword= passwordEncoder.encode(request.getPassword());
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(encodedPassword)
            .build();

        //User resultingUser = userRepository.save(user);
        //System.out.println(resultingUser);
        return userRepository.save(user);
    } 

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    
}
