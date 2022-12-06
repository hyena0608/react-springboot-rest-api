package com.devcourse.blind.domain.comment.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentExceptionMessage {

    COMMENT_INSERT_EXCEPTION("[ERROR] - 댓글 저장 중 오류가 발생하였습니다."),
    COMMENT_DELETE_EXCEPTION("[ERROR] - 댓글 삭제 중 오류가 발생하였습니다.");

    private final String message;
}
