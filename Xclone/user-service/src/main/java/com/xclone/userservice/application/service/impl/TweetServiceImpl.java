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

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public void createTweet(CreateTweetRequest request) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(principal.getName()).orElseThrow(() -> ErrorHelper.buildBadRequestException("Email", "Can not find user"));

        tweetRepository.save(Tweet.from(request, user));
    }

    @Override
    public List<TweetResponseDto> getTweets(){
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(principal.getName()).orElseThrow(() -> ErrorHelper.buildBadRequestException("Email", "Can not find user"));

        List<Tweet> tweets = tweetRepository.findAllByUser(user);

        return tweets.stream().map(TweetResponseDto::convertToTweetResponseDto).toList();
    }
}
