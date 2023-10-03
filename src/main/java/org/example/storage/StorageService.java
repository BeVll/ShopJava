package org.example.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void init();
    void removeFile(String removeFile);
    Path load(String fileName);
    String saveImage(MultipartFile file);
}
