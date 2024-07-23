package com.xclone.userservice.repository.db.dao;

import com.xclone.userservice.repository.db.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Set<Tag> findAllByTagNameIn(Collection<String> tagNames);
    Set<Tag> findAllByTagName(String tagName);
    Set<Tag> findTop5ByOrderByTweetsQuantityDesc();
}
