package com.devcourse.blind.domain.review.repository;

import com.devcourse.blind.domain.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepository {
    Review save(Review review);
    Page<Review> findAllByCorporationId(Pageable page, Long corporationId);
    List<Review> findReviews(int reviewCount);
    Review findReviewByMemberCorporation(Long memberId, Long corporationId);
    int findReviewCountsByCorporationId(Long corporationId);
}
