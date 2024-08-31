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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = EntityHelper.TWEET_TABLE)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
    @EqualsAndHashCode.Exclude
    private User user;

    @ManyToMany(mappedBy = User_.LIKED_TWEETS)
    @EqualsAndHashCode.Exclude
    private Set<User> likes;

    @ManyToMany(mappedBy = User_.RETWEETS)
    @EqualsAndHashCode.Exclude
    private Set<User> retweets;

    @OneToMany(mappedBy = TweetImage_.TWEET, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<TweetImage> tweetImages = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = EntityHelper.TWEET_TAG_TABLE,
            joinColumns = @JoinColumn(name = Tweet_.TWEET_ID),
            inverseJoinColumns = @JoinColumn(name = Tag_.TAG_ID)
    )
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(name = Tweet_.REPLIES,
            joinColumns = @JoinColumn(name = Tweet_.TWEET_ID),
            inverseJoinColumns = @JoinColumn(name = "reply_id"))
    private List<Tweet> replies;

    public static Tweet from(CreateTweetRequest request, User user) {
        return Tweet.builder()
                .text(request.getText())
                .user(user)
                .createdBy(user.getUserId().toString())
                .build();
    }
}
