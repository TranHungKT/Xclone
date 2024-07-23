package com.xclone.userservice.application.service;

import com.xclone.userservice.responseDto.TagResponseDto;

import java.util.Set;

public interface TagService {
    Set<TagResponseDto> getTags();
}
