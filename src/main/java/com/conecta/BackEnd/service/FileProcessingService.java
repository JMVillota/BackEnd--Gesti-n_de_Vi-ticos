package com.conecta.BackEnd.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileProcessingService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileProcessingService.class);
    private final String uploadDir = "uploads/";
    
    public int countPdfFilesInZip(MultipartFile zipFile) throws IOException {
        int pdfCount = 0;
        
        try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".pdf")) {
                    pdfCount++;
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            logger.error("Error al procesar el archivo ZIP: {}", e.getMessage());
            throw e;
        }
        
        return pdfCount;
    }
    
    public String saveZipFile(MultipartFile file, String viaticoId) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            String fileName = viaticoId + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            
            return filePath.toString();
            
        } catch (IOException e) {
            logger.error("Error al guardar el archivo ZIP: {}", e.getMessage());
            throw e;
        }
    }
}