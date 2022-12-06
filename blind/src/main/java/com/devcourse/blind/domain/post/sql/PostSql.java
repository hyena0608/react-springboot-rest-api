package com.devcourse.blind.domain.post.sql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostSql {

    INSERT_POST("INSERT INTO POST(post_title, post_content, post_userId, post_username, post_corporation_title, post_hit, post_like, post_created_at, post_updated_at, post_category_id, post_member_id) " +
            "VALUES(:title, :content, :userId, :username, :corporationTitle, :hitCount, :likeCount, :createdAt, :updatedAt, :categoryId, :memberId)"),

    SELECT_POST_BY_ID("SELECT * FROM POST WHERE post_id = :id"),
    SELECT_PAGING_POSTS_BY_CATEGORY_ID("SELECT * FROM POST WHERE post_category_id = :categoryId ORDER BY {0} {1} LIMIT {2} OFFSET {3}"),
    SELECT_POSTS_BY_TITLE("SELECT * FROM POST WHERE post_title"),
    SELECT_CATEGORIES_POSTS("SELECT * FROM (" +
            "SELECT p.post_id, p.post_title, p.post_hit, p.post_created_at, c.post_category_id, c.post_category_title, " +
            "RANK() OVER " +
            "(PARTITION BY p.post_category_id ORDER BY p.post_created_at DESC) AS RN " +
            "FROM POST p, POST_CATEGORY c " +
            "WHERE p.post_category_id = c.post_category_id " +
            ") AS RANKING " +
            "WHERE RANKING.RN <= 5;"),
    SELECT_ALL_COUNT("SELECT count(*) FROM POST"),
    SELECT_COUNT_BY_CATEGORY_ID("SELECT count(*) FROM POST WHERE post_category_id = :categoryId"),
    UPDATE_DELETE_AT_POST_BY_ID("UPDATE POST SET post_deleted_at = now() WHERE post_id = :id");

    private final String sql;
}
