package com.xclone.userservice.repository.db.entity;

import com.xclone.userservice.repository.db.helper.EntityHelper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = EntityHelper.TWEET_IMAGE_TABLE)
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TweetImage extends BaseEntity {
    @Id
    private UUID imageId;

    @Column
    @NotNull
    private String src;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Tweet_.TWEET_ID)
    private Tweet tweet;

    public static TweetImage from(@NonNull String imageSrc, User user) {
        return TweetImage.builder()
                .imageId(UUID.randomUUID())
                .src(imageSrc)
                .createdBy(user.getUserId().toString())
                .build();
    }
}
