package com.devcourse.blind.domain.like.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import com.devcourse.blind.domain.member.domain.Member;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Like extends BaseEntity {

    private final Long id;
    private LikeType likeType;
    private TargetType targetType;
    private Long targetId;

    private Member member;

    public static LikeBuilder builderFrom(Like like) {
        return Like.builder()
                .likeType(like.likeType)
                .targetType(like.targetType)
                .targetId(like.targetId)
                .member(like.member);
    }

    public boolean isExists() {
        return this.id != 0;
    }
}
