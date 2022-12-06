package com.devcourse.blind.domain.like;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeParamColumnMapper {

    LIKE_ID("id", "like_id"),
    LIKE_TYPE("likeType", "like_type"),
    LIKE_TARGET_TYPE("targetType", "like_target_type"),
    LIKE_TARGET_ID("targetId", "like_target_id"),
    LIKE_CREATED_AT("createdAt", "like_created_at"),
    LIKE_UPDATED_AT("updatedAt", "like_updated_at"),
    LIKE_DELETED_AT("deletedAt", "like_deleted_at"),
    FK_MEMBER_ID("memberId", "like_member_id");

    private final String param;
    private final String column;
}
