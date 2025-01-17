package com.xclone.userservice.application.service;

import com.xclone.userservice.requestDto.CreateTweetRequest;
import com.xclone.userservice.requestDto.ReactTweetRequest;
import com.xclone.userservice.responseDto.TweetResponseDto;

import java.util.List;
import java.util.UUID;

public interface TweetService {
    void createTweet(CreateTweetRequest request);
    List<TweetResponseDto> getTweets();
    List<TweetResponseDto> getTweetsByTagName(String tagName);

    TweetResponseDto getTweetDetails(UUID id);
    void deleteTweet(UUID id);
    void reactTweet(UUID id, ReactTweetRequest request);
    void retweet(UUID id);
}
