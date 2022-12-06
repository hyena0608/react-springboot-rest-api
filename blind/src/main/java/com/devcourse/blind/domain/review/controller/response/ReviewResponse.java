package com.devcourse.blind.domain.review.controller.response;

import com.devcourse.blind.domain.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponse {

    private final Long id;
    private final String corporationTitle;
    private final double totalPoint;
    private final int careerImprovementPoint;
    private final int workLifeBalancePoint;
    private final int salaryWelfarePoint;
    private final int corporateCulturePoint;
    private final int managementPoint;
    private final String oneSentenceComment;
    private final String pros;
    private final String cons;
    private final LocalDateTime createdAt;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .corporationTitle(review.getCorporationTitle())
                .totalPoint(review.getTotalPoint())
                .careerImprovementPoint(review.getCareerImprovementPoint())
                .workLifeBalancePoint(review.getWorkLifeBalancePoint())
                .salaryWelfarePoint(review.getSalaryWelfarePoint())
                .corporateCulturePoint(review.getCorporateCulturePoint())
                .managementPoint(review.getManagementPoint())
                .oneSentenceComment(review.getOneSentenceComment())
                .pros(review.getPros())
                .cons(review.getCons())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
