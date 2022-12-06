package com.devcourse.blind.domain.review.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.member.domain.Member;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @NotNull
    @Positive
    private final Long id;

    private String corporationTitle;

    @Range(min = 0, max = 5)
    private double totalPoint;

    @Range(min = 0, max = 5)
    private int careerImprovementPoint;

    @Range(min = 0, max = 5)
    private int workLifeBalancePoint;

    @Range(min = 0, max = 5)
    private int salaryWelfarePoint;

    @Range(min = 0, max = 5)
    private int corporateCulturePoint;

    @Range(min = 0, max = 5)
    private int managementPoint;

    private String oneSentenceComment;

    private String pros;

    private String cons;

    private Corporation corporation;

    private Member member;

    public static ReviewBuilder builderFrom(Review review) {
        return Review.builder()
                .corporationTitle(review.corporationTitle)
                .totalPoint(review.totalPoint)
                .careerImprovementPoint(review.careerImprovementPoint)
                .workLifeBalancePoint(review.workLifeBalancePoint)
                .salaryWelfarePoint(review.salaryWelfarePoint)
                .corporateCulturePoint(review.corporateCulturePoint)
                .managementPoint(review.managementPoint)
                .oneSentenceComment(review.oneSentenceComment)
                .pros(review.pros)
                .cons(review.cons)
                .corporation(review.corporation)
                .member(review.member);
    }

    public int sumPoints() {
        return careerImprovementPoint +
                workLifeBalancePoint +
                salaryWelfarePoint +
                corporateCulturePoint +
                managementPoint;
    }

    public boolean isExists() {
        return this.id != 0L;
    }
}
