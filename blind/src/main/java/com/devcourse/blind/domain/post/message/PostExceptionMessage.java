package com.devcourse.blind.domain.post.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostExceptionMessage {

    POST_INSERT_EXCEPTION("[ERROR] - 게시글 저장 중 오류가 발생하였습니다."),
    POST_DELETE_EXCEPTION("[ERROR] - 게시글 삭제 중 오류가 발생하였습니다.");

    private final String message;
}
