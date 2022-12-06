package com.devcourse.blind.domain.category.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategorySql {

    INSERT_CATEGORIES("INSERT INTO POST_CATEGORY(post_category_title) VALUES(:categoryTitle)");

    private final String sql;
}
