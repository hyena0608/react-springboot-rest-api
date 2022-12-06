package com.devcourse.blind.domain.corporation.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CorporationResponseMessage {

    CORPORATION_SELECT_BY_TITLE("기업 타이틀 조회 요청 결과입니다."),
    CORPORATION_CHART("기업 리뷰 점수 내림차순 조회 요청 결과입니다.");

    private final String title = "기업";
    private final String content;
}
