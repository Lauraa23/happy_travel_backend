package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.services.DestinationService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class DestinationController {
    
    private final DestinationService destinationService;


    public DestinationController(DestinationService destinationService) {

        this.destinationService = destinationService;
    }

    @GetMapping("/destinations")
    @PreAuthorize("permitAll()")
    public List<Destination> getDestinations() {
        return destinationService.getDestinations();
    }
    

}
