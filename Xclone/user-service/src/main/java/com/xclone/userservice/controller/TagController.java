package com.xclone.userservice.controller;

import com.xclone.userservice.application.service.TagService;
import com.xclone.userservice.responseDto.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Set<TagResponseDto>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }
}
