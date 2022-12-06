package com.devcourse.blind.domain.like.service;

import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;

public interface LikeService {
    Like save(Like like);
    Like updateLikeTypeById(Long likeId, LikeType likeType);
}
