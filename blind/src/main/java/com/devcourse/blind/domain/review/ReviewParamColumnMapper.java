package com.devcourse.blind.domain.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewParamColumnMapper {
    REVIEW_ID("id", "review_id"),
    REVIEW_CORPORATION_TITLE("corporationTitle", "review_corporation_title"),
    REVIEW_TOTAL_POINT("totalPoint", "review_total_point"),
    REVIEW_CAREER_IMPROVEMENT_POINT("careerImprovementPoint", "review_career_improvement_point"),
    REVIEW_WORK_LIFE_BALANCE_POINT("workLifeBalancePoint", "review_work_life_balance_point"),
    REVIEW_SALARY_WELFARE_POINT("salaryWelfarePoint", "review_salary_welfare_point"),
    REVIEW_CORPORATE_CULTURE_POINT("corporateCulturePoint", "review_corporate_culture_point"),
    REVIEW_MANAGEMENT_POINT("managementPoint", "review_management_point"),
    REVIEW_ONE_SENTENCE_COMMENT("oneSentenceComment", "review_one_sentence_comment"),
    REVIEW_PROS("pros", "review_pros"),
    REVIEW_CONS("cons", "review_cons"),
    REVIEW_CREATED_AT("createdAt", "review_created_at"),
    REVIEW_UPDATED_AT("updatedAt", "review_updated_at"),
    REVIEW_DELETED_AT("deletedAt", "review_deleted_at"),
    FK_CORPORATION_ID("corporationId", "review_corporation_id"),
    FK_MEMBER_ID("memberId", "review_member_id");

    private final String param;
    private final String column;
}
