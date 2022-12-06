package com.devcourse.blind.domain.review.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionMessage {

    REVIEW_INSERT_EXCEPTION("[ERROR] - 기업 리뷰 저장 중 오류가 발생하였습니다."),
    REVIEW_INSERT_DUPLICATED_EXCEPTION("[ERROR] - 기업 리뷰 저장 중 중복 오류가 발생하였습니다."),

    REVIEW_DELETE_EXCEPTION("[ERROR] - 기업 리뷰 삭제 중 오류가 발생하였습니다."),
    REVIEW_SELECT_EXCEPTION("[ERROR] - 기업 리뷰 조회 중 오류가 발생하였습니다.");

    private final String message;
}
