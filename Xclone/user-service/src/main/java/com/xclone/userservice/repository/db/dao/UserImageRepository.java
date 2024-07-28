package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserImageRepository extends JpaRepository<UserImage, UUID> {
    Optional<UserImage> findAllByImageId(UUID imageId);

    @Query(value = """
                SELECT ui from UserImage ui JOIN User on ui.user.userId = :userId
            """)
    Optional<UserImage> findByUserId(UUID userId);
}
