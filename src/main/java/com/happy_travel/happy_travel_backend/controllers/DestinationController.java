package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.DestinationRepository;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;
import com.happy_travel.happy_travel_backend.services.DestinationService;

//import io.micrometer.common.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final DestinationService destinationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    public DestinationController(DestinationService destinationService, UserRepository userRepository, DestinationRepository destinationRepository) {
        this.destinationService = destinationService;
    }

    @GetMapping("/destinations")
    public List<Destination> getDestinations() {
        return destinationService.getDestinations();
    }

    @PostMapping("/destinations")
    public ResponseEntity<Destination> addDestination(@RequestParam("title") String title,
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            @RequestParam("imgUrl") MultipartFile imgUrl,
            @RequestParam("user") Integer userId) {

        // Aquí manejas la lógica para guardar la imagen en el servidor
        String fileName = StringUtils.cleanPath(imgUrl.getOriginalFilename());
        Path imagePath= Paths.get("src/main/resources/static/images/" + fileName);

        try {
            Files.copy(imgUrl.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // Cambia la URL de la imagen para incluir el dominio del backend
        String imageUrl = "http://localhost:3001/images/" + fileName;

        // Busca el usuario que está creando el destino
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crea un nuevo destino con los datos recibidos
        Destination destination = new Destination();
        destination.setTitle(title);
        destination.setLocation(location);
        destination.setDescription(description);
        destination.setImageUrl(imageUrl); // Guarda la URL relativa de la imagen
        destination.setUser(user);

        // Guarda el destino en la base de datos
        Destination savedDestination = destinationRepository.save(destination);

        return ResponseEntity.ok(savedDestination);
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
