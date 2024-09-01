package com.xclone.userservice.repository.db.entity;

import com.xclone.userservice.application.service.impl.UserServiceImpl;
import com.xclone.userservice.repository.db.helper.EntityHelper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = EntityHelper.TAG_TABLE)
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Id
    private UUID tagId;

    private String tagName;
    private Long tweetsQuantity;

    @ManyToMany(mappedBy = Tweet_.TAGS)
    @EqualsAndHashCode.Exclude
    private Set<Tweet> tweets;

    public static Tag from(String tagName, Tweet tweet, String createdBy) {
        return Tag.builder()
                .tagId(UUID.randomUUID())
                .tagName(tagName)
                .tweetsQuantity(1L)
                .tweets(Set.of(tweet))
                .createdBy(createdBy)
                .build();
    }
}
