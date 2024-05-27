package com.xclone.userservice.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xclone.userservice.repository.db.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetResponseDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("date")
    private LocalDateTime date;

    public static TweetResponseDto convertToTweetResponseDto(Tweet response) {
        return TweetResponseDto.builder()
                .id(response.getTweetId())
                .text(response.getText())
                .date(Optional.ofNullable(response.getUpdatedDt()).orElse(response.getCreatedDt()))
                .build();
    }
}


