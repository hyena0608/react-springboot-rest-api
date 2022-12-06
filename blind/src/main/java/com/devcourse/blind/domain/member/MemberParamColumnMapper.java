package com.devcourse.blind.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberParamColumnMapper {

    MEMBER_ID("id", "member_id"),
    MEMBER_USERID("userId", "member_userId"),
    MEMBER_NICKNAME("nickname", "member_nickname"),
    MEMBER_USERNAME("username", "member_username"),
    MEMBER_CREATED_AT("createdAt", "member_created_at"),
    MEMBER_UPDATED_AT("updatedAt", "member_updated_at"),
    MEMBER_DELETED_AT("deletedAt", "member_deleted_at"),
    FK_CORPORATION_ID("corporationId", "member_corporation_id");

    private final String param;
    private final String column;
}
