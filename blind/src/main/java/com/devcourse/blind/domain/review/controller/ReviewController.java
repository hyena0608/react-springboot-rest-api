package com.devcourse.blind.domain.review.controller;

import com.devcourse.blind.base.controller.response.BaseResponseBody;
import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.review.controller.request.CreateReviewRequest;
import com.devcourse.blind.domain.review.controller.response.ReviewResponse;
import com.devcourse.blind.domain.review.controller.response.ReviewsResponse;
import com.devcourse.blind.domain.review.domain.Review;
import com.devcourse.blind.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devcourse.blind.domain.review.message.ReviewResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public BaseResponseBody<ReviewResponse> saveReview(@RequestBody @Valid CreateReviewRequest request) {
        Review review = Review.builder()
                .corporationTitle(request.corporationTitle())
                .careerImprovementPoint(request.careerImprovementPoint())
                .workLifeBalancePoint(request.workLifeBalancePoint())
                .salaryWelfarePoint(request.salaryWelfarePoint())
                .corporateCulturePoint(request.corporateCulturePoint())
                .managementPoint(request.managementPoint())
                .oneSentenceComment(request.oneSentenceComment())
                .pros(request.pros())
                .cons(request.cons())
                .corporation(new Corporation(request.corporationId()))
                .member(new Member(request.memberId()))
                .build();
        Review savedReview = reviewService.save(review);
        ReviewResponse parsedReview = ReviewResponse.from(savedReview);
        return BaseResponseBody.of(REVIEW_SAVE.getTitle(), REVIEW_SAVE.getContent(), parsedReview);
    }

    @GetMapping("/corporation")
    public BaseResponseBody<ReviewsResponse> getReviewsByCorporationId(@RequestParam @NotNull @Positive long corporationId,
                                                                       @RequestParam @NotNull @PositiveOrZero int page,
                                                                       @RequestParam @NotNull @Positive int size
    ) {
        Page<Review> postPage = reviewService.findReviewsByCorporationId(page, size, corporationId);
        List<ReviewResponse> findReviews = postPage.getContent()
                .stream()
                .map(ReviewResponse::from)
                .toList();
        ReviewsResponse reviews = ReviewsResponse.from(findReviews, postPage.getPageable(), corporationId);
        return BaseResponseBody.of(REVIEWS_BY_CORPORATION.getTitle(), REVIEWS_BY_CORPORATION.getContent(), reviews);
    }

    @GetMapping("/{reviewCount}")
    public BaseResponseBody<List<ReviewResponse>> findReviews(@PathVariable @NotNull @Positive int reviewCount) {
        List<ReviewResponse> reviews = reviewService.findReviews(reviewCount)
                .stream()
                .map(ReviewResponse::from)
                .toList();
        return BaseResponseBody.of(REVIEWS_FIND.getTitle(), REVIEWS_FIND.getContent(), reviews);
    }
}
