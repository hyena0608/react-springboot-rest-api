package com.devcourse.blind.base.controller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponseBody<T> {

    private final String title;
    private final String content;
    private final T stock;

    public static <T> BaseResponseBody<T> of(String title, String content, T stock) {
        return new BaseResponseBody<>(title, content, stock);
    }
}
