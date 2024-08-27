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
        String uniqueFileName= UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath= Paths.get(storageDirectoryPath,uniqueFileName);

        Files.createDirectories(filePath.getParent());
        file.transferTo(filePath);

        return "/images/"+uniqueFileName;//retorna ruta relativa
    }
}
