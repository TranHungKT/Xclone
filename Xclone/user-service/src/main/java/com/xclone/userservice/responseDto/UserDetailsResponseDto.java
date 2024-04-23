package com.xclone.userservice.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xclone.userservice.repository.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @JsonProperty("updatedBy")
    private String updatedBy;

    public static UserDetailsResponseDto convertToUserDetailsResponseDto(User user) {
        return UserDetailsResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .location(user.getLocation())
                .about(user.getAbout())
                .website(user.getWebsite())
                .confirmed(user.isConfirmed())
                .updatedDt(user.getUpdatedDt())
                .updatedBy(user.getUpdatedBy())
                .build();
    }
}

