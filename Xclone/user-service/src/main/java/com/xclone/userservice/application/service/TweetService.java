package com.xclone.userservice.application.service;

import com.xclone.userservice.repository.db.entity.Tweet;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import com.xclone.userservice.responseDto.TweetResponseDto;

import java.util.List;

public interface TweetService {
    void createTweet(CreateTweetRequest request);
    List<TweetResponseDto> getTweets();
}
