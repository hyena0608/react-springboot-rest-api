package com.devcourse.blind.domain.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryParamColumnMapper {
    CATEGORY_ID("id", "post_category_id"),
    CATEGORY_TITLE("categoryTitle", "post_category_title"),
    CATEGORY_CREATED_AT("createdAt", "post_category_created_at"),
    CATEGORY_UPDATED_AT("updatedAt", "post_category_updated_at"),
    CATEGORY_DELETED_AT("deletedAt", "post_category_deleted_at");

    private final String param;
    private final String column;
}
