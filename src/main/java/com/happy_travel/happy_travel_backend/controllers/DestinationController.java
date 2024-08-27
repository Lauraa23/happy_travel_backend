package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.services.DestinationService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
public class DestinationController {
    
    private final DestinationService destinationService;


    public DestinationController(DestinationService destinationService) {

        this.destinationService = destinationService;
    }

    @GetMapping("/destinations")
    public List<Destination> getDestinations() {
        return destinationService.getDestinations();
    }
    
    @PostMapping("/destinations")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination newDestination) {

        Destination createdDestination = destinationService.addDestination(newDestination);

        return new ResponseEntity<>(createdDestination, HttpStatus.CREATED);
    }
   
    @GetMapping("/destinations/search")
    public List<Destination> searchDestinationsByTitle(@RequestParam("title") String title) {
        return destinationService.findDestinationsByTitle(title);
    }

    @GetMapping("/destinations/filter")
    public List<Destination> searchDestinationsByLocation(@RequestParam("location") String location) {
        return destinationService.findDestinationsByLocation(location);
    }

    @DeleteMapping("/destinations")
    public ResponseEntity<Void> deleteDestinationByTitle(@RequestParam("title") String title) {
        try {
            destinationService.deleteDestinationByTitle(title);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/destinations")
    public ResponseEntity<Destination> updateDestination(
        @RequestParam("title") String title,
        @RequestBody Destination updatedDestination) {

            try {
                Destination destination = destinationService.updateDestination(title, updatedDestination);
                return new ResponseEntity<>(destination, HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
}
