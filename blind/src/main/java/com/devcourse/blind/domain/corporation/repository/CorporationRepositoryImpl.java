package com.devcourse.blind.domain.corporation.repository;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.corporation.domain.CorporationBusinessType;
import com.devcourse.blind.domain.corporation.domain.CorporationSizeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.devcourse.blind.domain.corporation.CorporationParamColumnMapper.*;
import static com.devcourse.blind.domain.corporation.sql.CorporationSql.*;
import static com.devcourse.blind.domain.like.message.LikeExceptionMessage.LIKE_UPDATE_EXCEPTION;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CorporationRepositoryImpl implements CorporationRepository {
    
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource toParamSource(Corporation corporation) {
        return new MapSqlParameterSource()
                .addValue(CORPORATION_TITLE.getParam(), corporation.getTitle())
                .addValue(CORPORATION_SIZE_TYPE.getParam(), corporation.getSizeType())
                .addValue(CORPORATION_BUSINESS_TYPE.getParam(), corporation.getBusinessType())
                .addValue(CORPORATION_HEAD_OFFICE_LOCATION.getParam(), corporation.getHeadOfficeLocation())
                .addValue(CORPORATION_MIN_ANNUAL_SALARY.getParam(), corporation.getMinAnnualSalary())
                .addValue(CORPORATION_MAX_ANNUAL_SALARY.getParam(), corporation.getMaxAnnualSalary())
                .addValue(CORPORATION_REVIEW_AVERAGE_POINT.getParam(), corporation.getReviewAveragePoint());
    }

    private final RowMapper<Corporation> corporationMapper = (resultSet, i) -> {
        Corporation corporation = Corporation.builder()
                .id(resultSet.getLong(CORPORATION_ID.getColumn()))
                .title(resultSet.getString(CORPORATION_TITLE.getColumn()))
                .sizeType(CorporationSizeType.valueOf(resultSet.getString(CORPORATION_SIZE_TYPE.getColumn())))
                .businessType(CorporationBusinessType.valueOf(resultSet.getString(CORPORATION_BUSINESS_TYPE.getColumn())))
                .headOfficeLocation(resultSet.getString(CORPORATION_HEAD_OFFICE_LOCATION.getColumn()))
                .minAnnualSalary(resultSet.getInt(CORPORATION_MIN_ANNUAL_SALARY.getColumn()))
                .maxAnnualSalary(resultSet.getInt(CORPORATION_MAX_ANNUAL_SALARY.getColumn()))
                .reviewAveragePoint(resultSet.getDouble(CORPORATION_REVIEW_AVERAGE_POINT.getColumn()))
                .build();
        LocalDateTime createdAt = resultSet.getTimestamp(CORPORATION_CREATED_AT.getColumn()).toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp(CORPORATION_UPDATED_AT.getColumn()).toLocalDateTime();
        corporation.setUpBaseEntityBy(createdAt, updatedAt);
        return corporation;
    };

    private final RowMapper<Corporation> corporationChartMapper = (resultSet, i) -> Corporation.builder()
            .id(resultSet.getLong(CORPORATION_ID.getColumn()))
            .title(resultSet.getString(CORPORATION_TITLE.getColumn()))
            .build();

    @Override
    public Corporation save(Corporation corporation) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int updatedCount = jdbcTemplate.update(INSERT_CORPORATION.getSql(), toParamSource(corporation), keyHolder);
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(LIKE_UPDATE_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return Corporation.builderFrom(corporation)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    @Override
    public List<Corporation> findByTitle(String title) {
        try {
            return jdbcTemplate.query(SELECT_CORPORATION_BY_TITLE.getSql(),
                    Collections.singletonMap(CORPORATION_TITLE.getParam(), title), corporationMapper);
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Corporation> findOrderByReviewPoint() {
        try {
            return jdbcTemplate.query(SELECT_CORPORATION_ORDER_BY_REVIEW_POINT.getSql(),
                    corporationChartMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public Corporation findById(Long corporationId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CORPORATION_BY_ID.getSql(),
                    Collections.singletonMap(CORPORATION_ID.getParam(), corporationId), corporationMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return new Corporation(0L);
    }

    @Override
    public Corporation updateTotalReviewPoint(Long corporationId, double reviewAveragePoint) {
        try {
            int updatedCount = jdbcTemplate.update(UPDATE_REVIEW_AVERAGE_POINT.getSql(),
                    Map.of(CORPORATION_ID.getParam(), corporationId,
                            CORPORATION_REVIEW_AVERAGE_POINT.getParam(), reviewAveragePoint));
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(LIKE_UPDATE_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return findById(corporationId);
    }

}
