package com.devcourse.blind.domain.comment.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentSql {

    INSERT_COMMENT("INSERT INTO COMMENT(comment_content, comment_username, comment_corporation_title, comment_post_id, comment_member_id) " +
            "VALUES(:content, :username, :corporationTitle, :postId, :memberId)"),
    SELECT_COMMENT_BY_POST_ID("SELECT * FROM COMMENT WHERE comment_post_id = :postId ORDER BY {0} {1} LIMIT {2} OFFSET {3}"),
    SELECT_COUNT_BY_POST_ID("SELECT count(*) FROM COMMENT WHERE comment_post_id = :postId"),
    UPDATE_DELETE_AT_BY_COMMENT_ID("UPDATE COMMENT SET comment_deleted_at = now() WHERE comment_id = :id");

    private final String sql;
}

