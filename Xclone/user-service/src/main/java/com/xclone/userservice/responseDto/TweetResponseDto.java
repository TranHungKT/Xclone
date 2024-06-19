package com.xclone.userservice.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xclone.userservice.repository.db.entity.TweetImage;
import com.xclone.userservice.repository.db.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @JsonProperty("images")
    private List<TweetImageResponseDto> images;

    public static TweetResponseDto convertToTweetResponseDto(Tweet response) {
        return TweetResponseDto.builder()
                .id(response.getTweetId())
                .text(response.getText())
                .date(Optional.ofNullable(response.getUpdatedDt()).orElse(response.getCreatedDt()))
                .images(TweetImageResponseDto.convertToTweetImageResponseDto(response.getTweetImages()))
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class TweetImageResponseDto {
        @JsonProperty("imageId")
        private UUID imageId;

        @JsonProperty("imageSrc")
        private String imageSrc;

        public static List<TweetImageResponseDto> convertToTweetImageResponseDto(Set<TweetImage> tweetImages) {
            return tweetImages.stream().map(
                    image -> TweetImageResponseDto.builder()
                            .imageId(image.getImageId())
                            .imageSrc(image.getSrc())
                            .build()
            ).toList();
        }
    }
}


