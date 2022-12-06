package com.devcourse.blind.domain.corporation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CorporationParamColumnMapper {

    CORPORATION_ID("id", "corporation_id"),
    CORPORATION_TITLE("title", "corporation_title"),
    CORPORATION_SIZE_TYPE("sizeType", "corporation_size_type"),
    CORPORATION_BUSINESS_TYPE("businessType", "corporation_business_type"),
    CORPORATION_HEAD_OFFICE_LOCATION("headOfficeLocation", "corporation_head_office_location"),
    CORPORATION_MIN_ANNUAL_SALARY("minAnnualSalary", "corporation_min_annual_salary"),
    CORPORATION_MAX_ANNUAL_SALARY("maxAnnualSalary", "corporation_max_annual_salary"),
    CORPORATION_REVIEW_AVERAGE_POINT("reviewAveragePoint", "corporation_review_average_point"),
    CORPORATION_CREATED_AT("createdAt", "corporation_created_at"),
    CORPORATION_UPDATED_AT("updatedAt", "corporation_updated_at"),
    CORPORATION_DELETED_AT("deletedAt", "corporation_deleted_at");

    private final String param;
    private final String column;
}
