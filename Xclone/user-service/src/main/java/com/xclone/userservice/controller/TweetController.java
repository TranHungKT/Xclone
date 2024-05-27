package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.TweetService;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import com.xclone.userservice.responseDto.TweetResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    @GetMapping()
    public ResponseEntity<List<TweetResponseDto>> getTweets(){
        return new ResponseEntity<>(tweetService.getTweets(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TweetResponseDto> getTweetDetails(@PathVariable @Valid final UUID id){
        return new ResponseEntity<>(tweetService.getTweetDetails(id), HttpStatus.OK);
    }
}
