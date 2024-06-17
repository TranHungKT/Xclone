package com.xclone.userservice.application.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.xclone.userservice.application.service.AmazonS3Service;
import com.xclone.userservice.common.Enum.ImageType;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.common.Model.BaseImage;
import com.xclone.userservice.repository.db.dao.TweetImageRepository;
import com.xclone.userservice.repository.db.entity.TweetImage;
import com.xclone.userservice.requestDto.UploadImageMetadataRequest;
import com.xclone.userservice.responseDto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final AmazonS3 s3Client;
    private final TweetImageRepository tweetImageRepository;


    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Override
    public ImageResponseDto uploadFile(MultipartFile file, UploadImageMetadataRequest request) throws IOException {
        if (!ImageType.getSetOfImageType().contains(request.getType())) {
            throw ErrorHelper.buildBadRequestException("ImageIDs", "image type is not valid");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(bucketName, fileName, file.getInputStream(), null);

        if (Objects.equals(request.getType(), ImageType.TWEET_IMAGE_TYPE.getValue())) {
            TweetImage tweetImage = TweetImage.from(getFileUrl(fileName), UserServiceImpl.getUser());

            TweetImage savedTweetImage = tweetImageRepository.save(tweetImage);
            return ImageResponseDto.convertToImageResponseDto(
                    BaseImage.builder()
                            .id(savedTweetImage.getImageId())
                            .src(savedTweetImage.getSrc())
                            .build());
        }

        return null;
    }

    private String getFileUrl(String keyName) {
        return s3Client.getUrl(bucketName, keyName).toString();
    }
}
