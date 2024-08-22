package com.happy_travel.happy_travel_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happy_travel.happy_travel_backend.models.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    //User findByName(int name);
    Optional<User> findByName(String name);
}
