package com.happy_travel.happy_travel_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.models.User;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    List<Destination> findByUser(User user);

    List<Destination> findByTitleContainingIgnoreCase(String title);
} 
