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
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String userId;
    private final String username;
    private final String corporationTitle;
    private final int hitCount;
    private final int likeCount;
    private final LocalDateTime createdAt;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUserId())
                .username(post.getUsername())
                .corporationTitle(post.getCorporationTitle())
                .hitCount(post.getHitCount())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
