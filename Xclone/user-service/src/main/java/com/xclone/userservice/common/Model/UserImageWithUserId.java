package com.xclone.userservice.common.Model;

import java.util.UUID;

public record UserImageWithUserId(UUID userId, UUID imageId, String src) {

}
