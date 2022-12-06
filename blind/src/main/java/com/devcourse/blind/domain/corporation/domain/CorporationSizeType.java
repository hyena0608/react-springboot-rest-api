package com.devcourse.blind.domain.corporation.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CorporationSizeType {
    ZERO_TO_ONE_HUNDRED(0, 100),
    ONE_HUNDRED_TO_TWO_HUNDRED(101, 200),
    TWO_HUNDRED_TO_FIVE_HUNDRED(201, 500),
    FIVE_HUNDRED_TO_ONE_THOUSAND(501, 1000),
    OVER_ONE_THOUSAND(1001, Integer.MAX_VALUE);

    private final int minSize;
    private final int maxSize;
}
