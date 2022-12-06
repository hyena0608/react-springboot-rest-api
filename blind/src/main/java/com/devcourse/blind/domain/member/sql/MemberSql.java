package com.devcourse.blind.domain.member.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberSql {
    INSERT_MEMBER("INSERT INTO MEMBER(member_userId, member_nickname, member_username, member_created_at, member_updated_at, member_corporation_id) " +
            "VALUES(:userId, :nickname, :username, :createdAt, :updatedAt, :corporationId)"),
    SELECT_MEMBER_BY_ID("SELECT * FROM MEMBER WHERE member_id = :id");

    private final String sql;
}
