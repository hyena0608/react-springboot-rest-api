package com.devcourse.blind.domain.category.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryTitle {
    BLABLA("블라블라"),
    STOCK_INVESTMENT("주식_투자"),
    DATE_RELATIONSHIP("썸_연애"),
    CRYPTOCURRENCY("암호화폐"),
    PROPERTY("부동산"),
    CHANGE_JOB_CAREER("이직_커리어"),
    HEALTH_DIET("헬스_다이어트"),
    TRAVEL_FOOD("여행_먹방"),
    OFFICE_LIFE("회사생활"),
    CAR("자동차"),
    HOBBY("직장인 취미생활"),
    SHOPPING("지름_쇼핑"),
    COMPANION_ANIMAL("반려동물"),
    CORONA_VIRUS("코로나 바이러스"),
    MARRIED_LIFE("결혼생활"),
    SPORT("스포츠"),
    INFANT_CARE("육아"),
    FASHION_BEAUTY("패션_뷰티"),
    GAME("게임"),
    HUMOR("유우머"),
    TV_ENTERTAINMENT("TV_연애");

    private final String title;
}
