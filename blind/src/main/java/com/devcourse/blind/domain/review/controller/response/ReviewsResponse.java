package com.devcourse.blind.domain.review.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewsResponse {

    private final Long corporationId;
    private final Pageable pageInfo;
    private final List<ReviewResponse> reviews;

    public static ReviewsResponse from(List<ReviewResponse> reviews, Pageable pageInfo, Long corporationId) {
        return ReviewsResponse.builder()
                .reviews(reviews)
                .pageInfo(pageInfo)
                .corporationId(corporationId)
                .build();
    }
}
