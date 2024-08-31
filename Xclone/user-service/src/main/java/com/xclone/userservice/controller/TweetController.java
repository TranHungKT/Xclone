package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.TweetService;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import com.xclone.userservice.requestDto.ReactTweetRequest;
import com.xclone.userservice.responseDto.TweetResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<TweetResponseDto>> getTweets() {
        return new ResponseEntity<>(tweetService.getTweets(), HttpStatus.OK);
    }

    @GetMapping("tags")
    public ResponseEntity<List<TweetResponseDto>> getTweetsByTagName(
            @RequestParam("tagName") String tagName
    ) {
        return new ResponseEntity<>(tweetService.getTweetsByTagName(tagName), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TweetResponseDto> getTweetDetails(@PathVariable @Valid final UUID id) {
        return new ResponseEntity<>(tweetService.getTweetDetails(id), HttpStatus.OK);
    }

    @PostMapping("react/{id}")
    public ResponseEntity<Void> reactTweet(@PathVariable @Valid final UUID id, @Valid @RequestBody ReactTweetRequest request) {
        tweetService.reactTweet(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reply/{tweetId}")
    public ResponseEntity<Void> replyTweet(@PathVariable @Valid final UUID tweetId, @Valid @RequestBody CreateTweetRequest request) {
        tweetService.replyTweet(tweetId, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable @Valid final UUID id) {
        tweetService.deleteTweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("retweet/{id}")
    public ResponseEntity<Void> retweet(@PathVariable @Valid final UUID id) {
        tweetService.retweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
