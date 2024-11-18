package com.practice.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    @Value("${spring.application.media.image.bucket}")
    private String bucket;

    @SneakyThrows
    public String saveImage(String filename, InputStream inputStream) {
        Path bucketPath = Path.of(bucket);
        Path pathToImage = Path.of(bucket, filename);

        Files.createDirectories(bucketPath);
        Files.copy(inputStream, pathToImage, StandardCopyOption.REPLACE_EXISTING);

        return pathToImage.toAbsolutePath().toString();
    }

    @SneakyThrows
    public byte[] getImage(String filename) {
        Path pathToImage = Path.of(bucket, filename);
        return Files.readAllBytes(pathToImage);
    }
}
