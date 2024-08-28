package com.happy_travel.happy_travel_backend.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private String storageDirectoryPath="src/main/resources/static/images";

    public String saveImage(MultipartFile file) throws IOException{
        // Validación de tipo de archivo
        String contentType = file.getContentType();
        if (!isValidContentType(contentType)) {
            throw new IOException("Invalid file type. Only JPEG and PNG are allowed.");
        }

        // Validación de tamaño de archivo (máximo 5MB)
        long maxFileSize = 5 * 1024 * 1024; // 5MB
        if (file.getSize() > maxFileSize) {
            throw new IOException("File size exceeds the 5MB limit.");
        }

        // Verifica si el directorio existe, si no, lo crea
        Path storageDirectory = Paths.get(storageDirectoryPath);
        if (!Files.exists(storageDirectory)) {
            Files.createDirectories(storageDirectory);
        }
        String uniqueFileName= UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath= storageDirectory.resolve(uniqueFileName);

        file.transferTo(filePath.toFile());
        return "/images/"+uniqueFileName;//retorna ruta relativa
    }

    private boolean isValidContentType(String contentType) {
        return "image/jpeg".equals(contentType) || "image/png".equals(contentType);
    }
    
}
