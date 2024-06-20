package com.xclone.userservice.application.service;

import com.xclone.userservice.requestDto.UploadImageMetadataRequest;
import com.xclone.userservice.responseDto.ImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Service {
    ImageResponseDto uploadFile(MultipartFile file, UploadImageMetadataRequest request) throws IOException;
}
