package com.devcourse.blind.domain.comment.repository;

import com.devcourse.blind.domain.comment.domain.Comment;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.devcourse.blind.domain.comment.CommentParamColumnMapper.*;
import static com.devcourse.blind.domain.comment.message.CommentExceptionMessage.COMMENT_DELETE_EXCEPTION;
import static com.devcourse.blind.domain.comment.message.CommentExceptionMessage.COMMENT_INSERT_EXCEPTION;
import static com.devcourse.blind.domain.comment.sql.CommentSql.*;
import static org.springframework.data.domain.Sort.Order;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Comment> commentMapper = (resultSet, i) -> {
        Comment comment = Comment.builder()
                .id(resultSet.getLong(COMMENT_ID.getColumn()))
                .content(resultSet.getString(COMMENT_CONTENT.getColumn()))
                .username(resultSet.getString(COMMENT_CORPORATION_TITLE.getColumn()))
                .corporationTitle(resultSet.getString(COMMENT_CORPORATION_TITLE.getColumn()))
                .likeCount(resultSet.getInt(COMMENT_LIKE_COUNT.getColumn()))
                .post(new Post(resultSet.getLong(FK_POST_ID.getColumn())))
                .member(new Member(resultSet.getLong(FK_MEMBER_ID.getColumn())))
                .build();

        LocalDateTime createdAt = resultSet.getTimestamp(COMMENT_CREATED_AT.getColumn()).toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp(COMMENT_UPDATED_AT.getColumn()).toLocalDateTime();
        comment.setUpBaseEntityBy(createdAt, updatedAt);
        return comment;
    };

    private SqlParameterSource toParamSource(Comment comment) {
        return new MapSqlParameterSource()
                .addValue(COMMENT_CONTENT.getParam(), comment.getContent())
                .addValue(COMMENT_USERNAME.getParam(), comment.getUsername())
                .addValue(COMMENT_CORPORATION_TITLE.getParam(), comment.getCorporationTitle())
                .addValue(FK_POST_ID.getParam(), comment.getPost().getId())
                .addValue(FK_MEMBER_ID.getParam(), comment.getMember().getId());
    }

    @Override
    public Comment save(Comment comment) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int updatedCount = jdbcTemplate.update(INSERT_COMMENT.getSql(), toParamSource(comment), keyHolder);
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(COMMENT_INSERT_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return Comment.builderFrom(comment)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    @Override
    public Comment findById(long commendId) {
        return null;
    }

    @Override
    public Page<Comment> findAllByPostId(Pageable page, long postId) {
        Order order = Order.by(COMMENT_CREATED_AT.getColumn());
        List<Comment> comments = Collections.emptyList();
        try {
            comments = jdbcTemplate.query(MessageFormat.format(SELECT_COMMENT_BY_POST_ID.getSql(),
                            order.getProperty(), order.getDirection(), page.getPageSize(), page.getOffset()),
                    Collections.singletonMap(FK_POST_ID.getParam(), postId), commentMapper);
        } catch (EmptyResultDataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return new PageImpl<>(comments, page, countByPostId(postId));
    }

    @Override
    public boolean deleteById(long commentId) {
        try {
            int deletedCount = jdbcTemplate.update(UPDATE_DELETE_AT_BY_COMMENT_ID.getSql(),
                    Collections.singletonMap(COMMENT_ID.getParam(), commentId));
            if (deletedCount != 1) {
                throw new DataAccessResourceFailureException(COMMENT_DELETE_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
            return false;
        }
        return true;
    }

    private int countByPostId(long postId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_COUNT_BY_POST_ID.getSql(),
                    Collections.singletonMap(FK_POST_ID.getParam(), postId), Integer.class);
        } catch (DataAccessException | NullPointerException e) {
            log.error("Fail - {}", e.getMessage());
        }
        return 0;
    }
}
