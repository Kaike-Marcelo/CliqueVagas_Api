package com.pi.clique_vagas_api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static byte[] loadFileFromPath(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error loading file from path: " + filePath, e);
        }
    }

}
