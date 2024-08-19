package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.common.Model.UserImageWithUserId;
import com.xclone.userservice.repository.db.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserImageRepository extends JpaRepository<UserImage, UUID> {
    Optional<UserImage> findAllByImageId(UUID imageId);

    @Query(value = """
                SELECT ui FROM UserImage ui JOIN User ON ui.user.userId = :userId
            """)
    Optional<UserImage> findByUserId(UUID userId);

    @Query(value = """
                SELECT NEW com.xclone.userservice.common.Model.UserImageWithUserId(ui.user.userId, ui.imageId, ui.src) FROM UserImage ui JOIN User u ON ui.user.userId = u.userId WHERE u.userId IN :userIds
            """)
    List<UserImageWithUserId> findByUserIdIn(Collection<UUID> userIds);
}
