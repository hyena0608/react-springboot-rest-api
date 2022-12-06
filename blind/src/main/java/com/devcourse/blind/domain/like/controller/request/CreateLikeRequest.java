package com.devcourse.blind.domain.like.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateLikeRequest(@NotBlank String likeType,
                                @NotBlank String targetType,
                                @NotNull @Positive Long targetId,
                                @NotNull @Positive Long memberId) {
}
