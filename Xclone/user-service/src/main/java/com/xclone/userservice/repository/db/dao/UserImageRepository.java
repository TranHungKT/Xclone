package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserImageRepository extends JpaRepository<UserImage, UUID> {
    Optional<UserImage> findAllByImageId(UUID imageId);
}
