package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.services.DestinationService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





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
    
    @PostMapping("/destinations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination newDestination) {

        Destination createdDestination = destinationService.addDestination(newDestination);

        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }
   
    @GetMapping("/destinations/search")
    @PreAuthorize("permitAll()")
    public List<Destination> searchDestinationsByTitle(@RequestParam("title") String title) {
        return destinationService.findDestinationsByTitle(title);
    }
    

    
}
