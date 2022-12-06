package com.devcourse.blind.domain.review.service;

import com.devcourse.blind.domain.review.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    Review save(Review review);
    Page<Review> findReviewsByCorporationId(int page, int size, long corporationId);
    List<Review> findReviews(int reviewCount);
}
