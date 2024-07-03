package com.example.controller;

import com.example.model.FileUpload;
import com.example.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@CrossOrigin(origins = {"http://localhost:4200/"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart("email") String email, @RequestPart("username") String username, @RequestPart("password") String password, @RequestPart("file") MultipartFile file) {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        log.info("Original file name: {}", file.getOriginalFilename()); //Returns the original filename of uploaded file as specified by the client
        log.info("form field: {}", file.getName()); //Returns the name of the form field in the multipart request that contained the file
        log.info("File size: {}", file.getSize());

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

        return ResponseEntity.ok().body("File uploaded successfully");
    }
}
