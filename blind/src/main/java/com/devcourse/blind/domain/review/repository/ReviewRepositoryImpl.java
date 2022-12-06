package com.devcourse.blind.domain.review.repository;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.review.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.devcourse.blind.domain.review.ReviewParamColumnMapper.*;
import static com.devcourse.blind.domain.review.message.ReviewExceptionMessage.REVIEW_INSERT_EXCEPTION;
import static com.devcourse.blind.domain.review.message.ReviewExceptionMessage.REVIEW_SELECT_EXCEPTION;
import static com.devcourse.blind.domain.review.sql.ReviewSql.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryImpl implements ReviewRepository {


    private final NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource toParamSource(Review review) {
        return new MapSqlParameterSource()
                .addValue(REVIEW_CORPORATION_TITLE.getParam(), review.getCorporationTitle())
                .addValue(REVIEW_TOTAL_POINT.getParam(), review.getTotalPoint())
                .addValue(REVIEW_CAREER_IMPROVEMENT_POINT.getParam(), review.getCareerImprovementPoint())
                .addValue(REVIEW_WORK_LIFE_BALANCE_POINT.getParam(), review.getWorkLifeBalancePoint())
                .addValue(REVIEW_SALARY_WELFARE_POINT.getParam(), review.getSalaryWelfarePoint())
                .addValue(REVIEW_CORPORATE_CULTURE_POINT.getParam(), review.getCorporateCulturePoint())
                .addValue(REVIEW_MANAGEMENT_POINT.getParam(), review.getManagementPoint())
                .addValue(REVIEW_ONE_SENTENCE_COMMENT.getParam(), review.getOneSentenceComment())
                .addValue(REVIEW_PROS.getParam(), review.getPros())
                .addValue(REVIEW_CONS.getParam(), review.getCons())
                .addValue(FK_CORPORATION_ID.getParam(), review.getCorporation().getId())
                .addValue(FK_MEMBER_ID.getParam(), review.getMember().getId());
    }

    private final RowMapper<Review> reviewMapper = (resultSet, i) -> Review.builder()
            .id(resultSet.getLong(REVIEW_ID.getColumn()))
            .corporationTitle(resultSet.getString(REVIEW_CORPORATION_TITLE.getColumn()))
            .totalPoint(resultSet.getInt(REVIEW_TOTAL_POINT.getColumn()))
            .careerImprovementPoint(resultSet.getInt(REVIEW_CAREER_IMPROVEMENT_POINT.getColumn()))
            .workLifeBalancePoint(resultSet.getInt(REVIEW_WORK_LIFE_BALANCE_POINT.getColumn()))
            .salaryWelfarePoint(resultSet.getInt(REVIEW_SALARY_WELFARE_POINT.getColumn()))
            .corporateCulturePoint(resultSet.getInt(REVIEW_CORPORATE_CULTURE_POINT.getColumn()))
            .managementPoint(resultSet.getInt(REVIEW_CORPORATE_CULTURE_POINT.getColumn()))
            .oneSentenceComment(resultSet.getString(REVIEW_ONE_SENTENCE_COMMENT.getColumn()))
            .pros(resultSet.getString(REVIEW_PROS.getColumn()))
            .cons(resultSet.getString(REVIEW_CONS.getColumn()))
            .corporation(new Corporation(resultSet.getLong(FK_CORPORATION_ID.getColumn())))
            .member(new Member(resultSet.getLong(FK_MEMBER_ID.getColumn())))
            .build();

    @Override
    public Review save(Review review) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Review saveReview = Review.builderFrom(review)
                .totalPoint(review.sumPoints() / 5.0)
                .build();
        try {
            int savedCount = jdbcTemplate.update(INSERT_REVIEW.getSql(), toParamSource(saveReview), keyHolder);
            if (savedCount != 1) {
                throw new DataAccessResourceFailureException(REVIEW_INSERT_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return Review.builderFrom(saveReview)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    @Override
    public Page<Review> findAllByCorporationId(Pageable page, Long corporationId) {
        Sort.Order order = Sort.Order.by(REVIEW_CREATED_AT.getColumn());
        List<Review> reviews = Collections.emptyList();
        try {
            reviews = jdbcTemplate.query(MessageFormat.format(SELECT_PAGING_REVIEWS_BY_CORPORATION_ID.getSql(),
                            order.getProperty(), order.getDirection(), page.getPageSize(), page.getOffset()),
                    Collections.singletonMap(FK_CORPORATION_ID.getParam(), corporationId), reviewMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        if (reviews.isEmpty()) {
            throw new IllegalStateException(REVIEW_SELECT_EXCEPTION.getMessage());
        }
        return new PageImpl<>(reviews, page, findReviewCountsByCorporationId(corporationId));
    }

    @Override
    public List<Review> findReviews(int reviewCount) {
        try {
            return jdbcTemplate.query(SELECT_REVIEWS_LIMIT.getSql(),
                    Collections.singletonMap("limit", reviewCount), reviewMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Review findReviewByMemberCorporation(Long memberId, Long corporationId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_REVIEW_BY_MEMBER_CORPORATION.getSql(),
                    Map.of(FK_MEMBER_ID.getParam(), memberId,
                            FK_CORPORATION_ID.getParam(), corporationId), reviewMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return new Review(0L);
    }

    @Override
    public int findReviewCountsByCorporationId(Long corporationId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_COUNT_BY_CORPORATION_ID.getSql(),
                    Collections.singletonMap(FK_CORPORATION_ID.getParam(), corporationId), Integer.class);
        } catch (DataAccessException | NullPointerException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return 0;
    }
}
