package com.devcourse.blind.domain.comment.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentResponseMessage {

    COMMENT_SAVE("댓글 저장 요청 결과입니다."),
    COMMENTS_BY_POST_ID("댓글 페이징 요청 결과입니다."),
    COMMENT_DELETE("댓글 삭제 요청 결과입니다.");

    private final String title = "댓글";
    private final String content;
}
