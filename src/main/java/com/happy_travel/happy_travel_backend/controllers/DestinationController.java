package com.happy_travel.happy_travel_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.happy_travel.happy_travel_backend.models.Destination;
import com.happy_travel.happy_travel_backend.models.User;
import com.happy_travel.happy_travel_backend.repositories.DestinationRepository;
import com.happy_travel.happy_travel_backend.repositories.UserRepository;
import com.happy_travel.happy_travel_backend.services.DestinationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DestinationController {

    @Autowired
    private final DestinationService destinationService;

    @Autowired
    private DestinationRepository destinationRepository;

    @Value("${images.directory}")
    private String imagesDirectory;

    public DestinationController(DestinationService destinationService, UserRepository userRepository,
            DestinationRepository destinationRepository) {
        this.destinationService = destinationService;
    }

    @GetMapping("/destinations")
    public List<Destination> getDestinations() {
        return destinationService.getDestinations();
    }
    
    @GetMapping("/destinations/search")
    public List<Destination> searchDestinationsByTitle(@RequestParam("title") String title) {
        return destinationService.findDestinationsByTitle(title);
    }

    @GetMapping("/destinations/searchById")
    public Optional<Destination> searchDestinationsById(@RequestParam("id") int id) {
       return destinationService.findDestinationById(id);
    }

    @GetMapping("/destinations/filter")
    public List<Destination> searchDestinationsByLocation(@RequestParam("location") String location) {
        return destinationService.findDestinationsByLocation(location);
    }

    @PostMapping("/destinations")
    public ResponseEntity<Destination> addDestination(
            @RequestParam("title") String title,
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            @RequestParam("imageUrl") MultipartFile imageUrl) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Usuario autenticado

        // Aquí manejas la lógica para guardar la imagen en el servidor
        String fileName = StringUtils.cleanPath(imageUrl.getOriginalFilename());
        Path imagePath = Paths.get(imagesDirectory + fileName);

        try {
            Files.copy(imageUrl.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // Cambia la URL de la imagen para incluir el dominio del backend
        String imageUrlString = "http://localhost:3001/images/" + fileName;

        // Crea un nuevo destino con los datos recibidos
        Destination destination = new Destination();
        destination.setTitle(title);
        destination.setLocation(location);
        destination.setDescription(description);
        destination.setImageUrl(imageUrlString); // Guarda la URL relativa de la imagen
        destination.setUser(user);

        // Guarda el destino en la base de datos
        Destination savedDestination = destinationRepository.save(destination);

        return ResponseEntity.ok(savedDestination);
    }

    @DeleteMapping("/destinations")
    public ResponseEntity<Void> deleteDestinationById(@RequestParam("id") int id) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        // Buscar el destino por ID
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado"));

        // Verificar si el usuario autenticado es el creador del destino
        if (destination.getUser().getId() != authenticatedUser.getId()) {
            // Retornar 403 Forbidden si el usuario autenticado no es el creador
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Eliminar el destino si la verificación es exitosa
        destinationService.deleteDestinationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/destinations")
    public ResponseEntity<Destination> updateDestination(
            @RequestParam("id") int id,
            @RequestParam("title") String title,
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        // Buscar el destino por ID
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado"));

        // Verificar si el usuario autenticado es el creador del destino
        if (destination.getUser().getId() != authenticatedUser.getId()) {
            // Retornar 403 Forbidden si el usuario autenticado no es el creador
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        // Actualizar los campos del destino existente
        destination.setTitle(title);
        destination.setLocation(location);
        destination.setDescription(description);

        // Si se proporciona una nueva imagen, actualizarla
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fileName = StringUtils.cleanPath(imageUrl.getOriginalFilename());
            Path imagePath = Paths.get(imagesDirectory + fileName);
            try {
                Files.copy(imageUrl.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                String imageUrlString = "http://localhost:3001/images/" + fileName;
                destination.setImageUrl(imageUrlString);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        // Guardar el destino actualizado en la base de datos
        Destination updatedDestination = destinationRepository.save(destination);
        return new ResponseEntity<>(updatedDestination, HttpStatus.OK);
    }
}
