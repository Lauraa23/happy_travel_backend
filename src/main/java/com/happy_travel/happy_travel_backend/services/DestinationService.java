package com.happy_travel.happy_travel_backend.services;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.happy_travel.happy_travel_backend.repositories.DestinationRepository;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;

@Service
public class DestinationService {
    
    @Value("${images.directory}")
    private String imagesDirectory;
    private DestinationRepository destinationRepository;
    private UserRepository userRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Destination saveDestination(int userId, Destination destination) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        destination.setUser(user);
        return destinationRepository.save(destination);
    }

    public List<Destination> getDestinationsByUser(User user) {
        return destinationRepository.findByUser(user);
    }

    public List<Destination> getDestinations() {
       return destinationRepository.findAll();
    }

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public List<Destination> findDestinationsByTitle(String title) {
        return destinationRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Destination> findDestinationsByLocation(String location) {
        return destinationRepository.findByLocationContainingIgnoreCase(location);
    }

    public void deleteDestinationByTitle(String title) {
        List<Destination> destinations = destinationRepository.findByTitleContainingIgnoreCase(title);
        if(destinations.isEmpty()) {
            throw new RuntimeException("Destination not found");
        } 
        for (Destination destination : destinations) {
             // Elimina la imagen del sistema de archivos
             String imageUrl = destination.getImageUrl();
             if (imageUrl != null) {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
                Path imagePath = Paths.get(imagesDirectory + fileName);
                try {
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Opcional: Manejar la excepción adecuadamente
                }
            }
            destinationRepository.delete(destination);
        }
    }

    public Destination updateDestination(String title, Destination updatedDestination) {
        List<Destination> destinations = destinationRepository.findByTitleContainingIgnoreCase(title);
        
        if (destinations.isEmpty()) {
            throw new RuntimeException("Destination not found");
        }

        Destination destination = destinations.get(0);
        
        destination.setTitle(updatedDestination.getTitle());
        destination.setLocation(updatedDestination.getLocation());
        destination.setDescription(updatedDestination.getDescription());
        destination.setImageUrl(updatedDestination.getImageUrl());
        
        return destinationRepository.save(destination);
    }

}
