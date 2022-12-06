package com.devcourse.blind.domain.like.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TargetType {
    POST(1),
    COMMENT(2);

    private final int code;

    public boolean isPost() {
        return this == POST;
    }

    public boolean isComment() {
        return this == COMMENT;
    }
}
