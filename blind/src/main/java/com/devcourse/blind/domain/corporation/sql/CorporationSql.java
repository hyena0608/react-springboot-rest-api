package com.devcourse.blind.domain.corporation.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CorporationSql {

    INSERT_CORPORATION("INSERT INTO CORPORATION(corporation_title, corporation_size_type, corporation_business_type, corporation_head_office_location, corporation_min_annual_salary, corporation_max_annual_salary) VALUES(:title, :sizeType, :businessType, :headOfficeLocation, :minAnnualSalary, :maxAnnualSalary)"),
    SELECT_CORPORATION_BY_TITLE("SELECT * FROM CORPORATION WHERE corporation_title = :title"),
    SELECT_CORPORATION_ORDER_BY_REVIEW_POINT("SELECT corporation_id, corporation_title, sum(review_total_point) " +
            "FROM CORPORATION, REVIEW " +
            "WHERE corporation_id = review_corporation_id " +
            "GROUP BY corporation_id " +
            "ORDER BY avg(review_total_point) DESC " +
            "LIMIT 10"),
    SELECT_CORPORATION_BY_ID("SELECT * FROM CORPORATION WHERE corporation_id = :id"),
    UPDATE_REVIEW_AVERAGE_POINT("UPDATE CORPORATION SET corporation_review_average_point = :reviewAveragePoint WHERE corporation_id = :id");

    private final String sql;
}
