package com.xclone.userservice.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.repository.db.entity.UserImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsResponseDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("location")
    private String location;

    @JsonProperty("about")
    private String about;

    @JsonProperty("website")
    private String website;

    @JsonProperty("confirmed")
    private Boolean confirmed;

    @JsonProperty("updatedDt")
    private LocalDateTime updatedDt;

    @JsonProperty("avatar")
    private AvatarResponseDto avatar;

    @JsonProperty("updatedBy")
    private String updatedBy;

    public static UserDetailsResponseDto convertToUserDetailsResponseDto(User user, UserImage userImage) {
        return UserDetailsResponseDto.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .location(user.getLocation())
                .about(user.getAbout())
                .website(user.getWebsite())
                .confirmed(user.isConfirmed())
                .updatedDt(user.getUpdatedDt())
                .updatedBy(user.getUpdatedBy())
                .avatar(Objects.isNull(userImage) ? null : AvatarResponseDto.convertToAvatarResponseDto(userImage) )
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class AvatarResponseDto {
        @JsonProperty("imageId")
        private UUID imageId;

        @JsonProperty("imageSrc")
        private String imageSrc;

        public static AvatarResponseDto convertToAvatarResponseDto(UserImage userImage) {
            var a = AvatarResponseDto.builder()
                    .imageId(userImage.getImageId())
                    .imageSrc(userImage.getSrc())
                    .build();
            return a;
        }

    }
}

