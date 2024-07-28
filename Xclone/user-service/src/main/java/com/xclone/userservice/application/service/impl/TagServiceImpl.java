package com.xclone.userservice.application.service.impl;

import com.xclone.userservice.application.service.TagService;
import com.xclone.userservice.repository.db.dao.TagRepository;
import com.xclone.userservice.responseDto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Set<TagResponseDto> getTags() {
        var tags = tagRepository.findTop5ByOrderByTweetsQuantityDesc();
        return tags.stream().map(TagResponseDto::convertToTagResponseDto).collect(Collectors.toSet());
    }
}
