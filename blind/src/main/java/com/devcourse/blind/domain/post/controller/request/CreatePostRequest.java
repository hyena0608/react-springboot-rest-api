package com.devcourse.blind.domain.post.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePostRequest(@NotBlank String title,
                                @NotBlank String content,
                                @NotBlank String userId,
                                @NotBlank String username,
                                @NotBlank String corporationTitle,
                                @NotNull @Positive Long categoryId,
                                @NotNull @Positive Long memberId) {
}
