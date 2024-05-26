package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.TweetService;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {
    private final TweetService tweetService;

    @PostMapping()
    public ResponseEntity<Void> createTweet(@Valid @RequestBody CreateTweetRequest request) {
        tweetService.createTweet(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
