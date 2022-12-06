package com.devcourse.blind.domain.post.controller.response;

import com.devcourse.blind.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsCategoriesResponse {

    private final Long postId;
    private final String postTitle;
    private final int hitCount;
    private final LocalDateTime createdAt;

    private final Long categoryId;
    private final String categoryTitle;

    public static PostsCategoriesResponse from(Post post) {
        return PostsCategoriesResponse.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .hitCount(post.getHitCount())
                .createdAt(post.getCreatedAt())
                .categoryId(post.getCategory().getId())
                .categoryTitle(post.getCategory().getCategoryTitle().toString())
                .build();
    }
}
