package com.vu.springapi.controller;

import com.vu.springapi.dto.response.ApiResponse;
import com.vu.springapi.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    /**
     * Upload single file
     * @param file MultipartFile
     * @return URL của file đã upload
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_UPDATE_DATA')")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileStorageService.storeFile(file);
        return ApiResponse.<String>builder()
                .message("File uploaded successfully")
                .result(fileUrl)
                .build();
    }
}

