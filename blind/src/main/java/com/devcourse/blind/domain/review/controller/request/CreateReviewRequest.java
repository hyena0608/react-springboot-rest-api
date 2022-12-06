package com.devcourse.blind.domain.review.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;

public record CreateReviewRequest(@NotBlank String corporationTitle,
                                  @Range(min = 0, max = 5) int careerImprovementPoint,
                                  @Range(min = 0, max = 5) int workLifeBalancePoint,
                                  @Range(min = 0, max = 5) int salaryWelfarePoint,
                                  @Range(min = 0, max = 5) int corporateCulturePoint,
                                  @Range(min = 0, max = 5) int managementPoint,
                                  @NotBlank String oneSentenceComment,
                                  @NotBlank String pros,
                                  @NotBlank String cons,
                                  @NotNull @Positive Long corporationId,
                                  @NotNull @Positive Long memberId) {
}
