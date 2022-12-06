package com.devcourse.blind.domain.post.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostResponseMessage {

    POST_SAVE("게시글 저장 요청 결과입니다."),
    POSTS_BY_CATEGORY("게시글 카테고리 페이징 요청 결과입니다."),
    POST_DELETE("게시글 삭제 요청 결과입니다."),
    CATEGORIES_POSTS_FIND("카테고리별 상위 게시글 요청 결과입니다.");

    private final String title= "게시글";
    private final String content;
}