package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.Image;
import com.xclone.userservice.repository.db.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Set<Image> findAllByImageIdIn(Set<UUID> imageIds);
    Image findByTweet(Tweet tweet);
}
