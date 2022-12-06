package com.devcourse.blind.domain.like.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateLikeRequest(@NotNull @Positive Long likeId,
                                @NotBlank String likeType) {
}
