package com.devcourse.blind.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryColumnParamMapper {

    CATEGORY_ID("id", "post_category_id"),
    CATEGORY_TITLE("categoryTitle", "post_category_title");

    private final String param;
    private final String column;
}
