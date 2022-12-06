package com.devcourse.blind.domain.review.service;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.corporation.repository.CorporationRepository;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.member.repository.MemberRepository;
import com.devcourse.blind.domain.review.domain.Review;
import com.devcourse.blind.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.devcourse.blind.domain.review.message.ReviewExceptionMessage.REVIEW_INSERT_DUPLICATED_EXCEPTION;
import static com.devcourse.blind.domain.review.message.ReviewExceptionMessage.REVIEW_INSERT_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;

    @Override
    public Review save(Review review) {
        Long memberId = review.getMember().getId();
        Long corporationId = review.getCorporation().getId();
        Member findMember = memberRepository.findById(memberId);
        Corporation findCorporation = corporationRepository.findById(corporationId);
        validateExistsFrom(findMember, findCorporation);

        Review findReview = reviewRepository.findReviewByMemberCorporation(memberId, corporationId);
        validateDuplicated(findReview);

        double calculatedReviewAveragePoint = calculateReviewAveragePoint(review, corporationId, findCorporation);
        corporationRepository.updateTotalReviewPoint(corporationId, calculatedReviewAveragePoint);
        return reviewRepository.save(review);
    }

    private static void validateDuplicated(Review findReview) {
        if (findReview.isExists()) {
            throw new IllegalStateException(REVIEW_INSERT_DUPLICATED_EXCEPTION.getMessage());
        }
    }

    private static void validateExistsFrom(Member findMember, Corporation findCorporation) {
        if (!findMember.isExists() || !findCorporation.isExists()) {
            throw new IllegalStateException(REVIEW_INSERT_EXCEPTION.getMessage());
        }
    }

    private double calculateReviewAveragePoint(Review review, Long corporationId, Corporation findCorporation) {
        int reviewCounts = reviewRepository.findReviewCountsByCorporationId(corporationId);
        double reviewAveragePoint = findCorporation.getReviewAveragePoint();
        double calculatedReviewAveragePoint = (reviewAveragePoint * reviewCounts + review.sumPoints()) / 5.0;
        return calculatedReviewAveragePoint;
    }

    @Override
    public Page<Review> findReviewsByCorporationId(int page, int size, long corporationId) {
        return reviewRepository.findAllByCorporationId(PageRequest.of(page, size), corporationId);
    }

    @Override
    public List<Review> findReviews(int reviewCount) {
        return reviewRepository.findReviews(reviewCount);
    }
}
