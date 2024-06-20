package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.TweetImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface TweetImageRepository extends JpaRepository<TweetImage, UUID> {
    Set<TweetImage> findAllByImageIdIn(Set<UUID> imageIds);
}
