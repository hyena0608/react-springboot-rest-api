package com.devcourse.blind.domain.member.controller.response;

import com.devcourse.blind.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private final Long id;
    private final String userId;
    private final String nickname;
    private final String username;
    private final LocalDateTime createdAt;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
