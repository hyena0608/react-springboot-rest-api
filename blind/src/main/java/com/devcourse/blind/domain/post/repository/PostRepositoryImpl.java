package com.devcourse.blind.domain.post.repository;

import com.devcourse.blind.domain.category.domain.Category;
import com.devcourse.blind.domain.category.domain.CategoryTitle;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.devcourse.blind.domain.CategoryColumnParamMapper.CATEGORY_ID;
import static com.devcourse.blind.domain.CategoryColumnParamMapper.CATEGORY_TITLE;
import static com.devcourse.blind.domain.post.PostParamColumnMapper.*;
import static com.devcourse.blind.domain.post.message.PostExceptionMessage.POST_DELETE_EXCEPTION;
import static com.devcourse.blind.domain.post.message.PostExceptionMessage.POST_INSERT_EXCEPTION;
import static com.devcourse.blind.domain.post.sql.PostSql.*;
import static org.springframework.data.domain.Sort.Order;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private static final Logger logger = LoggerFactory.getLogger(PostRepositoryImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Post> postMapper = (resultSet, i) -> {
        Post post = Post.builder()
                .id(resultSet.getLong(POST_ID.getColumn()))
                .title(resultSet.getString(POST_TITLE.getColumn()))
                .content(resultSet.getString(POST_CONTENT.getColumn()))
                .hitCount(resultSet.getInt(POST_HIT.getColumn()))
                .likeCount(resultSet.getInt(POST_LIKE.getColumn()))
                .userId(resultSet.getString(POST_USERID.getColumn()))
                .username(resultSet.getString(POST_USERNAME.getColumn()))
                .corporationTitle(resultSet.getString(POST_CORPORATION_TITLE.getColumn()))
                .member(new Member(resultSet.getLong(FK_MEMBER_ID.getColumn())))
                .category(new Category(resultSet.getLong(FK_POST_CATEGORY_ID.getColumn())))
                .build();

        LocalDateTime createdAt = resultSet.getTimestamp(POST_CREATED_AT.getColumn()).toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp(POST_UPDATED_AT.getColumn()).toLocalDateTime();
        post.setUpBaseEntityBy(createdAt, updatedAt);
        return post;
    };

    private final RowMapper<Post> categoriesPostsMapper = (resultSet, i) -> Post.builder()
            .id(resultSet.getLong(POST_ID.getColumn()))
            .title(resultSet.getString(POST_TITLE.getColumn()))
            .hitCount(resultSet.getInt(POST_HIT.getColumn()))
            .category(Category.builder()
                    .id(resultSet.getLong(CATEGORY_ID.getColumn()))
                    .categoryTitle(CategoryTitle.valueOf(resultSet.getString(CATEGORY_TITLE.getColumn())))
                    .build())
            .build();

    private SqlParameterSource toParamSource(Post post) {
        return new MapSqlParameterSource()
                .addValue(POST_TITLE.getParam(), post.getTitle())
                .addValue(POST_CONTENT.getParam(), post.getContent())
                .addValue(POST_HIT.getParam(), post.getHitCount())
                .addValue(POST_LIKE.getParam(), post.getLikeCount())
                .addValue(POST_USERID.getParam(), post.getUserId())
                .addValue(POST_USERNAME.getParam(), post.getUsername())
                .addValue(POST_CORPORATION_TITLE.getParam(), post.getCorporationTitle())
                .addValue(POST_CREATED_AT.getParam(), post.getCreatedAt())
                .addValue(POST_UPDATED_AT.getParam(), post.getUpdatedAt())
                .addValue(FK_MEMBER_ID.getParam(), post.getMember().getId())
                .addValue(FK_POST_CATEGORY_ID.getParam(), post.getCategory().getId());
    }

    @Override
    public Post save(Post post) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int updatedCount = jdbcTemplate.update(INSERT_POST.getSql(), toParamSource(post), keyHolder);
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(POST_INSERT_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        Post savedPost = Post.builderFrom(post)
                .id(keyHolder.getKey().longValue())
                .build();
        savedPost.setUpBaseEntityBy(post);
        return savedPost;
    }

    @Override
    public Post findByPostId(long postId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_POST_BY_ID.getSql(),
                    Collections.singletonMap(POST_ID.getParam(), postId), postMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return new Post(0L);
    }

    @Override
    public Page<Post> findByCategoryId(Pageable page, long categoryId) {
        Order order = Order.by(POST_CREATED_AT.getColumn());
        List<Post> posts = Collections.emptyList();
        try {
            posts = jdbcTemplate.query(MessageFormat.format(SELECT_PAGING_POSTS_BY_CATEGORY_ID.getSql(),
                            order.getProperty(), order.getDirection(), page.getPageSize(), page.getOffset()),
                    Collections.singletonMap(FK_POST_CATEGORY_ID.getParam(), categoryId), postMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return new PageImpl<>(posts, page, countByCategoryId(categoryId));
    }

    @Override
    public List<Post> findByPostTitle(String postTitle) {
        try {
            return jdbcTemplate.query(SELECT_POSTS_BY_TITLE.getSql(),
                    Collections.singletonMap(POST_TITLE.getParam(), postTitle), postMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Post> findPostsCategories() {
        try {
            return jdbcTemplate.query(SELECT_CATEGORIES_POSTS.getSql(), categoriesPostsMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean delete(long postId) {
        try {
            int updatedCount = jdbcTemplate.update(UPDATE_DELETE_AT_POST_BY_ID.getSql(),
                    Collections.singletonMap(POST_ID.getParam(), postId));
            if (updatedCount != 1) {
                throw new DataAccessResourceFailureException(POST_DELETE_EXCEPTION.getMessage());
            }
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return false;
        }
        return true;
    }

    private int countByCategoryId(long categoryId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_COUNT_BY_CATEGORY_ID.getSql(),
                    Collections.singletonMap(FK_POST_CATEGORY_ID.getParam(), categoryId), Integer.class);
        } catch (DataAccessException | NullPointerException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return 0;
    }
}
