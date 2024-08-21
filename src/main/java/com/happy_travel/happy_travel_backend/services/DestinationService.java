package com.happy_travel.happy_travel_backend.services;

import com.happy_travel.happy_travel_backend.models.Destination;

import java.util.List;

import org.springframework.stereotype.Service;

import com.happy_travel.happy_travel_backend.repositories.DestinationRepository;

@Service
public class DestinationService {
    
    private final DestinationRepository destinationRepository;


    public DestinationService(DestinationRepository destinationRepository) {

        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getDestinations() {

       return destinationRepository.findAll();
    }

}
