package com.devcourse.blind.domain.like.repository;

import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;
import com.devcourse.blind.domain.like.domain.TargetType;
import com.devcourse.blind.domain.member.domain.Member;
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
import java.util.Map;

import static com.devcourse.blind.domain.like.LikeParamColumnMapper.*;
import static com.devcourse.blind.domain.like.message.LikeExceptionMessage.LIKE_UPDATE_EXCEPTION;
import static com.devcourse.blind.domain.like.sql.LikeSql.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LikeRepositoryImpl implements LikeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource toParamSource(Like like) {
        return new MapSqlParameterSource()
                .addValue(LIKE_TYPE.getParam(), like.getLikeType().toString())
                .addValue(LIKE_TARGET_TYPE.getParam(), like.getTargetType().toString())
                .addValue(LIKE_TARGET_ID.getParam(), like.getTargetId())
                .addValue(FK_MEMBER_ID.getParam(), like.getMember().getId());
    }

    private final RowMapper<Like> likeMapper = (resultSet, i) -> {
        LikeType likeType = LikeType.valueOf(resultSet.getString(LIKE_TYPE.getColumn()));
        TargetType targetType = TargetType.valueOf(resultSet.getString(LIKE_TARGET_TYPE.getColumn()));
        Like like = Like.builder()
                .id(resultSet.getLong(LIKE_ID.getColumn()))
                .likeType(likeType)
                .targetType(targetType)
                .targetId(resultSet.getLong(LIKE_TARGET_ID.getColumn()))
                .member(new Member(resultSet.getLong(FK_MEMBER_ID.getColumn())))
                .build();

        LocalDateTime createdAt = resultSet.getTimestamp(LIKE_CREATED_AT.getColumn()).toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp(LIKE_UPDATED_AT.getColumn()).toLocalDateTime();
        like.setUpBaseEntityBy(createdAt, updatedAt);
        return like;
    };

    @Override
    public Like save(Like like) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(INSERT_LIKE.getSql(), toParamSource(like), keyHolder);
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        Like savedLike = Like.builderFrom(like)
                .id(keyHolder.getKey().longValue())
                .build();
        savedLike.setUpBaseEntityBy(like);
        return savedLike;
    }

    @Override
    public Like updateLikeTypeById(Long likeId, LikeType likeType) {
        try {
            int updatedCount = jdbcTemplate.update(UPDATE_LIKETYPE.getSql(),
                    Map.of(LIKE_ID.getParam(), likeId, LIKE_TYPE.getParam(), likeType.toString()));
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(LIKE_UPDATE_EXCEPTION.getMessage());
            }
            return jdbcTemplate.queryForObject(SELECT_LIKE_BY_ID.getSql(),
                    toUpdateLikeTypeParamSource(likeId, likeType), likeMapper);
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return Like.builder()
                .id(likeId)
                .build();
    }

    @Override
    public Like findBy(Long memberId, Long targetId, TargetType targetType) {
        try {
            return jdbcTemplate.queryForObject(SELECT_LIKE_BY_TARGET.getSql(),
                    Map.of(FK_MEMBER_ID.getParam(), memberId,
                            LIKE_TARGET_ID.getParam(), targetId,
                            LIKE_TARGET_TYPE.getParam(), targetType.toString()),
                    likeMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return new Like(0L);
    }

    private SqlParameterSource toUpdateLikeTypeParamSource(Long likeId, LikeType likeType) {
        return new MapSqlParameterSource()
                .addValue(LIKE_ID.getParam(), likeId)
                .addValue(LIKE_TYPE.getParam(), likeType.toString());
    }
}
