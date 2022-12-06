package com.devcourse.blind.domain.member.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberResponseMessage {

    POST_SAVE("회원", "회원 저장 요청 결과입니다.");

    private final String title;
    private final String content;
}
