package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.TweetService;
import com.xclone.userservice.common.Enum.ReactTweetType;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.repository.db.dao.TweetImageRepository;
import com.xclone.userservice.repository.db.dao.TweetRepository;
import com.xclone.userservice.repository.db.entity.Tweet;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import com.xclone.userservice.requestDto.ReactTweetRequest;
import com.xclone.userservice.responseDto.TweetResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final TweetImageRepository tweetImageRepository;

    @Override
    public void createTweet(CreateTweetRequest request) {
        var images = tweetImageRepository.findAllByImageIdIn(request.getImageIds());

        var imagesWithTweetId = images.stream().filter(image -> !Objects.isNull(image.getTweet())).toList();
        if (!imagesWithTweetId.isEmpty()) {
            throw ErrorHelper.buildBadRequestException("ImageIDs", "some image is already attach with other tweet");
        }

        Tweet tweet = Tweet.from(request, UserServiceImpl.getUser());
        images.forEach(image -> image.setTweet(tweet));
        tweetRepository.save(tweet);
    }

    @Override
    public List<TweetResponseDto> getTweets() {
        List<Tweet> tweets = tweetRepository.findAllByUser(UserServiceImpl.getUser());

        return tweets.stream().map(TweetResponseDto::convertToTweetResponseDto).toList();
    }

    @Override
    public TweetResponseDto getTweetDetails(UUID id) {
        Tweet tweet = tweetRepository.findByTweetIdAndUser(id, UserServiceImpl.getUser()).orElseThrow(() -> ErrorHelper.buildBadRequestException("Tweet", "Can not find tweet"));

        return TweetResponseDto.convertToTweetResponseDto(tweet);
    }

    @Override
    public void deleteTweet(UUID id) {
        Tweet tweet = tweetRepository.findByTweetIdAndUser(id, UserServiceImpl.getUser()).orElseThrow(() -> ErrorHelper.buildBadRequestException("Tweet", "Can not find tweet"));

        tweetRepository.deleteByTweetId(tweet.getTweetId());
    }

    @Override
    public void reactTweet(UUID id, ReactTweetRequest request) {
        if (!ReactTweetType.getSetOfReactTweetType().contains(request.getReactType())) {
            throw ErrorHelper.buildBadRequestException("reactType", "reactType is not valid");
        }

        var user = UserServiceImpl.getUser();
        var tweet = tweetRepository.findByTweetId(id).orElseThrow(() -> ErrorHelper.buildBadRequestException("tweetId", "Tweet do not exist"));
        var likedUsers = tweet.getLikes();

        if (Objects.equals(request.getReactType(), ReactTweetType.LIKE.getValue()) &&
                (ObjectUtils.isEmpty(likedUsers) || !likedUsers.contains(user))) {
            likedUsers.add(user);
        } else if (Objects.equals(request.getReactType(), ReactTweetType.DISLIKE.getValue())) {
            likedUsers.remove(user);
        }
        tweetRepository.save(tweet);
    }
}
