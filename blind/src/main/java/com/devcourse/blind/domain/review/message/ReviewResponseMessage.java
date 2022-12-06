package com.devcourse.blind.domain.review.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewResponseMessage {

    REVIEW_SAVE("기업 리뷰 저장 요청 결과입니다."),
    REVIEWS_BY_CORPORATION("해당 기업 리뷰 목록 페이징 요청 결과입니다."),
    REVIEWS_FIND("기업 리뷰 목록 요청 결과입니다."),
    REVIEW_DELETE("기업 리뷰 삭제 요청 결과입니다.");

    private final String title = "기업 리뷰";
    private final String content;
}