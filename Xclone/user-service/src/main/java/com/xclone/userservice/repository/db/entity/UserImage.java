package com.xclone.userservice.repository.db.entity;

import com.xclone.userservice.repository.db.helper.EntityHelper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = EntityHelper.USER_IMAGE_TABLE)
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    @Column
    @NotNull
    private String src;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = User_.USER_ID)
    private User user;

    public static UserImage from(@NonNull String imageSrc, User user) {
        return UserImage.builder()
                .src(imageSrc)
                .createdBy(user.getUserId().toString())
                .build();
    }
}
