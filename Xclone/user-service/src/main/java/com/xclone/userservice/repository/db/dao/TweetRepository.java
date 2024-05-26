package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
}
