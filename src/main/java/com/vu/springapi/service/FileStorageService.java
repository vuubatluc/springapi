package com.vu.springapi.service;

import com.vu.springapi.exception.AppException;
import com.vu.springapi.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("File storage location: {}", this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Upload file và trả về đường dẫn URL
     */
    public String storeFile(MultipartFile file) {
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_EMPTY);
        }

        // Lấy tên file gốc
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Kiểm tra file có ký tự không hợp lệ
            if (originalFileName.contains("..")) {
                throw new AppException(ErrorCode.FILE_INVALID);
            }

            // Validate file type (chỉ cho phép ảnh)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new AppException(ErrorCode.FILE_NOT_IMAGE);
            }

            // Validate file size (max 5MB)
            long maxFileSize = 5 * 1024 * 1024; // 5MB
            if (file.getSize() > maxFileSize) {
                throw new AppException(ErrorCode.FILE_TOO_LARGE);
            }

            // Tạo tên file unique bằng UUID + extension
            String fileExtension = getFileExtension(originalFileName);
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // Copy file vào thư mục storage
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("File uploaded successfully: {}", fileName);

            // Trả về đường dẫn URL (relative path)
            return "/uploads/" + fileName;

        } catch (IOException ex) {
            log.error("Could not store file {}. Error: {}", originalFileName, ex.getMessage());
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * Xóa file
     */
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        try {
            // Extract filename from URL
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            Files.deleteIfExists(filePath);
            log.info("File deleted successfully: {}", fileName);

        } catch (IOException ex) {
            log.error("Could not delete file {}. Error: {}", fileUrl, ex.getMessage());
        }
    }

    /**
     * Lấy extension của file
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}

