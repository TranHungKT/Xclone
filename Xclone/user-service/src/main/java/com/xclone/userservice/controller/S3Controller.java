package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.AmazonS3Service;
import com.xclone.userservice.requestDto.UploadImageMetadataRequest;
import com.xclone.userservice.responseDto.ImageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class S3Controller {
    private final AmazonS3Service s3Service;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ImageResponseDto> uploadFile(@Valid @RequestPart("file") MultipartFile file,
                                                       @Valid @RequestPart("metadata") UploadImageMetadataRequest request) throws IOException {
       return new ResponseEntity<>(s3Service.uploadFile(file, request), HttpStatus.OK);
    }
}
