package com.happy_travel.happy_travel_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happy_travel.happy_travel_backend.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
