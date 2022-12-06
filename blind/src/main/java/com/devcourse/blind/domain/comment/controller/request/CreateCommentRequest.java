package com.devcourse.blind.domain.comment.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCommentRequest(@NotBlank String content,
                                   @NotBlank String username,
                                   @NotBlank String corporationTitle,
                                   @NotNull @Positive Long postId,
                                   @NotNull @Positive Long memberId) {
}
