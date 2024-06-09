package com.xclone.userservice.application.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.xclone.userservice.application.service.AmazonS3Service;
import com.xclone.userservice.application.service.UserService;
import com.xclone.userservice.repository.db.dao.ImageRepository;
import com.xclone.userservice.repository.db.entity.Image;
import com.xclone.userservice.responseDto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final AmazonS3 s3Client;
    private final ImageRepository imageRepository;
    private final UserService userService;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Override
    public ImageResponseDto uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(bucketName, fileName, file.getInputStream(), null);
        Image image = Image.from(getFileUrl(fileName), userService.getUser());

        Image savedImage = imageRepository.save(image);
        return ImageResponseDto.convertToImageResponseDto(savedImage);
    }

    private String getFileUrl(String keyName) {
        return s3Client.getUrl(bucketName, keyName).toString();
    }

    @Override
    public S3Object getFile(String keyName) {
        return s3Client.getObject(bucketName, keyName);
    }
}
