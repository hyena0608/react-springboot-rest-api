package com.devcourse.blind.domain.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentParamColumnMapper {
    COMMENT_ID("id", "comment_id"),
    COMMENT_CONTENT("content", "comment_content"),
    COMMENT_USERNAME("username", "comment_username"),
    COMMENT_CORPORATION_TITLE("corporationTitle", "comment_corporation_title"),
    COMMENT_LIKE_COUNT("likeCount", "comment_like"),
    COMMENT_CREATED_AT("createdAt", "comment_created_at"),
    COMMENT_UPDATED_AT("updatedAt", "comment_updated_at"),
    COMMENT_DELETED_AT("deletedAt", "comment_deleted_at"),
    FK_POST_ID("postId", "comment_post_id"),
    FK_MEMBER_ID("memberId", "comment_member_id");

    private final String param;
    private final String column;
}
