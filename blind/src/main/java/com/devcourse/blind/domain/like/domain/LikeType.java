package com.devcourse.blind.domain.like.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LikeType {
    LIKE(1),
    DISLIKE(2);

    private final int code;
}
