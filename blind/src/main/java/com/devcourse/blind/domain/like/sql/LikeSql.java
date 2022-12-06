package com.devcourse.blind.domain.like.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeSql {
    SELECT_LIKE_BY_ID("SELECT * FROM LIKES WHERE like_id = :id"),
    SELECT_LIKE_BY_TARGET("SELECT * FROM LIKES WHERE like_member_id = :memberId AND like_target_id = :targetId AND like_target_type = :targetType"),
    INSERT_LIKE("INSERT INTO LIKES(like_type, like_target_type, like_target_id, like_member_id) VALUES(:likeType, :targetType, :targetId, :memberId)"),
    UPDATE_LIKETYPE("UPDATE LIKES SET like_type = :likeType WHERE like_id = :id");

    private final String sql;
}
