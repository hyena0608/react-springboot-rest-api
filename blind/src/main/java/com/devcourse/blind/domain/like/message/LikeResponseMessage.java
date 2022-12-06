package com.devcourse.blind.domain.like.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeResponseMessage {
    LIKE_SAVE("추천/비추천 저장 요청 결과입니다."),
    LIKE_UPDATE("추천/비추천 업데이트 요청 결과입니다.");

    private final String title = "추천/비추천";
    private final String content;
}
