package com.xclone.userservice.repository.db.entity;

import com.xclone.userservice.repository.db.helper.EntityHelper;
import com.xclone.userservice.requestDto.CreateTweetRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = EntityHelper.TWEET_TABLE)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
public class Tweet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tweetId;

    @Column
    @NotNull
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = User_.USER_ID)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = EntityHelper.TWEET_LIKES_TABLE,
            joinColumns = @JoinColumn(name = Tweet_.TWEET_ID),
            inverseJoinColumns = @JoinColumn(name = User_.USER_ID)
    )
    private List<User> likes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = EntityHelper.RETWEETS_TABLE,
            joinColumns = @JoinColumn(name = Tweet_.TWEET_ID),
            inverseJoinColumns = @JoinColumn(name = User_.USER_ID)
            )
    private List<User> retweets;

    @OneToMany(mappedBy = TweetImage_.TWEET, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TweetImage> tweetImages = new HashSet<>();

    public static Tweet from(CreateTweetRequest request, User user) {
        return Tweet.builder()
                .text(request.getText())
                .user(user)
                .createdBy(user.getUserId().toString())
                .build();
    }
}
