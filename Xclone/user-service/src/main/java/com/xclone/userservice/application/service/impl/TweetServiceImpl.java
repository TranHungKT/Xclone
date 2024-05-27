package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.TweetService;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.repository.db.dao.TweetRepository;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.Tweet;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import com.xclone.userservice.responseDto.TweetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public void createTweet(CreateTweetRequest request) {
        tweetRepository.save(Tweet.from(request, getUser()));
    }

    @Override
    public List<TweetResponseDto> getTweets(){
        List<Tweet> tweets = tweetRepository.findAllByUser(getUser());

        return tweets.stream().map(TweetResponseDto::convertToTweetResponseDto).toList();
    }

    @Override
    public TweetResponseDto getTweetDetails(UUID id){
        Tweet tweet = tweetRepository.findByTweetIdAndUser(id, getUser()).orElseThrow(() -> ErrorHelper.buildBadRequestException("Tweet", "Can not find tweet"));

        return TweetResponseDto.convertToTweetResponseDto(tweet);
    }

    private User getUser(){
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(principal.getName()).orElseThrow(() -> ErrorHelper.buildBadRequestException("Email", "Can not find user"));
    }
}
