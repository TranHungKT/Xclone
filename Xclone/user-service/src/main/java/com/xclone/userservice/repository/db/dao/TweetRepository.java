package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.Tweet;
import com.xclone.userservice.repository.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    List<Tweet> findAllByUser(User user);

    Optional<Tweet> findByTweetIdAndUser(UUID id, User user);

    @Query("SELECT t FROM Tweet t JOIN TweetImage i ON t.tweetId = i.tweet.tweetId WHERE t.tweetId = :id AND t.user = :user")
    Optional<Tweet> findByTweetIdAndUserWithImage(UUID id, User user);

    void deleteByTweetId(UUID id);
}
