package com.devcourse.blind.domain.like.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeExceptionMessage {

    LIKE_UPDATE_EXCEPTION("[ERROR] - 추천/비추천 업데이트 중 오류가 발생하였습니다."),
    LIKE_INSERT_DUPLICATED("[ERROR] - 추천/비추천 저장 중 중복 오류가 발생하였습니다."),
    LIKE_INSERT_MEMBER_EXCEPTION("[ERROR] - 추천/비추천 저장 중 회원 오류가 발생하였습니다."),
    LIKE_INSERT_TARGET_EXCEPTION("[ERROR] - 추천/비추천 저장 중 저장 타켓 번호에 오류가 발생하였습니다.");

    private final String message;
}
