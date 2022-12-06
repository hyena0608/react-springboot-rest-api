package com.devcourse.blind.domain.comment.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentsResponse {

    private final Long postId;
    private final Pageable pageInfo;
    private final List<CommentResponse> comments;

    public static CommentsResponse from(List<CommentResponse> comments, Pageable pageInfo, Long postId) {
        return CommentsResponse.builder()
                .comments(comments)
                .pageInfo(pageInfo)
                .postId(postId)
                .build();
    }
}
