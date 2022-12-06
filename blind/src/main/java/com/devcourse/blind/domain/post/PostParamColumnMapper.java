package com.devcourse.blind.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostParamColumnMapper {

    POST_ID("id", "post_id"),
    POST_TITLE("title", "post_title"),
    POST_CONTENT("content", "post_content"),
    POST_USERID("userId", "post_userId"),
    POST_USERNAME("username", "post_username"),
    POST_CORPORATION_TITLE("corporationTitle", "post_corporation_title"),
    POST_HIT("hitCount", "post_hit"),
    POST_LIKE("likeCount", "post_like"),
    POST_CREATED_AT("createdAt", "post_created_at"),
    POST_UPDATED_AT("updatedAt", "post_updated_at"),
    POST_DELETED_AT("deletedAt", "post_deleted_at"),
    FK_POST_CATEGORY_ID("categoryId", "post_category_id"),
    FK_MEMBER_ID("memberId", "post_member_id");

    private final String param;
    private final String column;
}
