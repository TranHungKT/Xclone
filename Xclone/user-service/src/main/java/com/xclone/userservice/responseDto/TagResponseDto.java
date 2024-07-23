package com.xclone.userservice.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xclone.userservice.repository.db.entity.Tag;
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
public class TagResponseDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("tagName")
    private String tagName;

    public static TagResponseDto convertToTagResponseDto(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getTagId())
                .tagName(tag.getTagName())
                .build();
    }
}
