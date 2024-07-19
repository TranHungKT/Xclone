package com.xclone.userservice.repository.db.entity.Id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserImageId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;
    private String imageId;
}
