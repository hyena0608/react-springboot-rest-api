package com.devcourse.blind.domain.like.controller;

import com.devcourse.blind.base.controller.response.BaseResponseBody;
import com.devcourse.blind.domain.like.controller.request.CreateLikeRequest;
import com.devcourse.blind.domain.like.controller.request.UpdateLikeRequest;
import com.devcourse.blind.domain.like.controller.response.LikeResponse;
import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;
import com.devcourse.blind.domain.like.domain.TargetType;
import com.devcourse.blind.domain.like.service.LikeService;
import com.devcourse.blind.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.devcourse.blind.domain.like.message.LikeResponseMessage.LIKE_SAVE;
import static com.devcourse.blind.domain.like.message.LikeResponseMessage.LIKE_UPDATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public BaseResponseBody<LikeResponse> saveLike(@RequestBody CreateLikeRequest request) {
        LikeType likeType = LikeType.valueOf(request.likeType());
        TargetType targetType = TargetType.valueOf(request.targetType());
        Like like = Like.builder()
                .likeType(likeType)
                .targetType(targetType)
                .targetId(request.targetId())
                .member(new Member(request.memberId()))
                .build();
        Like savedLike = likeService.save(like);
        LikeResponse parsedLike = LikeResponse.from(savedLike);
        return BaseResponseBody.of(LIKE_SAVE.getTitle(), LIKE_SAVE.getContent(), parsedLike);
    }

    @PatchMapping
    public BaseResponseBody<LikeResponse> updateLike(@RequestBody UpdateLikeRequest request) {
        Long likeId = request.likeId();
        LikeType likeType = LikeType.valueOf(request.likeType());
        Like updatedLike = likeService.updateLikeTypeById(likeId, likeType);
        LikeResponse parsedLike = LikeResponse.from(updatedLike);
        return BaseResponseBody.of(LIKE_UPDATE.getTitle(), LIKE_UPDATE.getContent(), parsedLike);
    }
}
