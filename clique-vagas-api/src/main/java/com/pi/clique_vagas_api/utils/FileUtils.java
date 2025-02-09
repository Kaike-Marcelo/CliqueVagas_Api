package com.pi.clique_vagas_api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String getFileExtension(String contentType) {
        String originalFilename = contentType.replaceAll("[\\\\/:*?\"<>|]", "");
        String fileExtension = "";
        int i = originalFilename.lastIndexOf('.');
        if (i > 0) {
            fileExtension = originalFilename.substring(i);
        }
        return fileExtension;
    }

    public static boolean isValidContentTypeImageOrPdf(String contentType) {
        return contentType.equals("application/pdf") ||
                contentType.startsWith("image/");
    }

    public static boolean isValidContentTypeImage(String contentType) {
        return contentType.startsWith("image/");
    }

    public static String saveFileInDirectory(MultipartFile file, Long userId, String directory, String prefix) {
        Path filePath = null;
        try {

            String originalFilename = file.getOriginalFilename();
            String fileExtension = FileUtils.getFileExtension(originalFilename);

            String fileName = (prefix + System.currentTimeMillis() + fileExtension);

            Path userDir = Paths.get(directory + userId);
            Files.createDirectories(userDir);
            filePath = userDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return filePath.toString();

        } catch (IOException e) {
            if (filePath != null) {
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file after failure", ex);
                }
            }
            throw new RuntimeException("Error creating directory or writing file", e);
        }
    }

    public static void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + filePath, e);
        }
    }

    public static byte[] loadFileFromPath(String filePath) {
        try {
            if (filePath == null || filePath.isEmpty())
                return null;

            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error loading file from path: " + filePath, e);
        }
    }

}
