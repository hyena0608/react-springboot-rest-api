package com.devcourse.blind.domain.member.repository;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.member.sql.MemberSql;
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

import java.util.Collections;

import static com.devcourse.blind.domain.like.message.LikeExceptionMessage.LIKE_UPDATE_EXCEPTION;
import static com.devcourse.blind.domain.member.MemberParamColumnMapper.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private SqlParameterSource toParamSource(Member member) {
        return new MapSqlParameterSource()
                .addValue(MEMBER_USERID.getParam(), member.getUserId())
                .addValue(MEMBER_NICKNAME.getParam(), member.getNickname())
                .addValue(MEMBER_USERNAME.getParam(), member.getUsername())
                .addValue(MEMBER_CREATED_AT.getParam(), member.getCreatedAt())
                .addValue(MEMBER_UPDATED_AT.getParam(), member.getUpdatedAt())
                .addValue(FK_CORPORATION_ID.getParam(), member.getCorporation().getId());
    }

    private final RowMapper<Member> memberMapper = (resultSet, i) -> Member.builder()
            .id(resultSet.getLong(MEMBER_ID.getColumn()))
            .userId(resultSet.getString(MEMBER_USERID.getColumn()))
            .username(resultSet.getString(MEMBER_USERNAME.getColumn()))
            .nickname(resultSet.getString(MEMBER_NICKNAME.getColumn()))
            .corporation(new Corporation(resultSet.getLong(FK_CORPORATION_ID.getColumn())))
            .build();

    @Override
    public Member save(Member member) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int updatedCount = jdbcTemplate.update(MemberSql.INSERT_MEMBER.getSql(), toParamSource(member), keyHolder);
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(LIKE_UPDATE_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        Member savedMember = Member.builder()
                .id(keyHolder.getKey().longValue())
                .userId(member.getUserId())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .corporation(member.getCorporation())
                .build();
        savedMember.setUpBaseEntityBy(member);
        return savedMember;
    }

    @Override
    public Member findById(long memberId) {
        try {
            return jdbcTemplate.queryForObject(MemberSql.SELECT_MEMBER_BY_ID.getSql(),
                    Collections.singletonMap(MEMBER_ID.getParam(), memberId), memberMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return new Member(0L);
    }
}
