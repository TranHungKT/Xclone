package com.xclone.userservice.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xclone.userservice.common.Model.BaseImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageResponseDto {
    @JsonProperty("imageSrc")
    private String imageSrc;

    @JsonProperty("imageId")
    private UUID imageId;

    public static ImageResponseDto convertToImageResponseDto(BaseImage tweetImage) {
        return ImageResponseDto.builder()
                .imageId(tweetImage.getId())
                .imageSrc(tweetImage.getSrc())
                .build();
    }
}
