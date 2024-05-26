package com.xclone.userservice.application.service;

import com.xclone.userservice.requestDto.CreateTweetRequest;

public interface TweetService {
    void createTweet(CreateTweetRequest request);
}
