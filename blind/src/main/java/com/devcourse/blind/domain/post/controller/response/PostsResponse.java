package com.devcourse.blind.domain.post.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsResponse {

    private final Long categoryId;
    private final Pageable pageInfo;
    private final List<PostResponse> posts;

    public static PostsResponse from(List<PostResponse> posts, Pageable pageInfo, Long categoryId) {
        return PostsResponse.builder()
                .posts(posts)
                .pageInfo(pageInfo)
                .categoryId(categoryId)
                .build();
    }
}
