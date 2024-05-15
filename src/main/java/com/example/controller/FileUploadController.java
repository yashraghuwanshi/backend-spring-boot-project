package com.example.controller;

import com.example.model.FileUpload;
import com.example.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200/"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public FileUpload uploadFile(
            @RequestPart("email") String email,
            @RequestPart("username") String username,
            @RequestPart("password") String password,
            @RequestPart("file") MultipartFile file
    ) {

        FileUpload fileUpload = new FileUpload();

        try {
            fileUpload.setEmail(email);
            fileUpload.setUsername(username);
            fileUpload.setPassword(password);
            fileUpload.setFileData(file.getBytes());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        fileUploadService.uploadFile(fileUpload);

        return fileUpload;

    }
}
