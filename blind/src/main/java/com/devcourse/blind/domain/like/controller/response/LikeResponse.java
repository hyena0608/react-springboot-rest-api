package com.devcourse.blind.domain.like.controller.response;

import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;
import com.devcourse.blind.domain.like.domain.TargetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeResponse {

    private final Long id;
    private final LikeType likeType;
    private final TargetType targetType;
    private final Long targetId;

    public static LikeResponse from(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .likeType(like.getLikeType())
                .targetType(like.getTargetType())
                .targetId(like.getTargetId())
                .build();
    }
}
