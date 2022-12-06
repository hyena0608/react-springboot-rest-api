package com.devcourse.blind.domain.review.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewSql {

    INSERT_REVIEW("INSERT INTO " +
            "REVIEW(review_corporation_title, " +
            "review_total_point, " +
            "review_career_improvement_point, " +
            "review_work_life_balance_point, " +
            "review_salary_welfare_point, " +
            "review_corporate_culture_point, " +
            "review_management_point, " +
            "review_one_sentence_comment, " +
            "review_pros, " +
            "review_cons, " +
            "review_corporation_id, " +
            "review_member_id) " +
            "VALUES(:corporationTitle, " +
            ":totalPoint, " +
            ":careerImprovementPoint, " +
            ":workLifeBalancePoint, " +
            ":salaryWelfarePoint, " +
            ":corporateCulturePoint, " +
            ":managementPoint, " +
            ":oneSentenceComment, " +
            ":pros, " +
            ":cons, " +
            ":corporationId, " +
            ":memberId)"),
    SELECT_PAGING_REVIEWS_BY_CORPORATION_ID("SELECT * FROM REVIEW WHERE review_corporation_id = :corporationId ORDER BY {0} {1} LIMIT {2} OFFSET {3}"),
    SELECT_COUNT_BY_CORPORATION_ID("SELECT count(*) FROM REVIEW WHERE review_corporation_id = :corporationId"),
    SELECT_REVIEWS_LIMIT("SELECT * FROM REVIEW ORDER BY review_created_at LIMIT :limit "),
    SELECT_REVIEW_BY_MEMBER_CORPORATION("SELECT * FROM REVIEW WHERE review_member_id = :memberId AND review_corporation_id = :corporationId");

    private final String sql;
}

