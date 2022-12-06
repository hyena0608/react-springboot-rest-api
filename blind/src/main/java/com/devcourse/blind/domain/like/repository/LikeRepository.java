package com.devcourse.blind.domain.like.repository;

import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;
import com.devcourse.blind.domain.like.domain.TargetType;

public interface LikeRepository {
    Like save(Like like);
    Like updateLikeTypeById(Long likeId, LikeType likeType);
    Like findBy(Long memberId, Long targetId, TargetType targetType);
}
