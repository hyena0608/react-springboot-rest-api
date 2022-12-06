package com.devcourse.blind.domain.member.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateMemberRequest(@NotBlank String userId,
                                  @NotBlank String nickname,
                                  @NotBlank String username,
                                  @NotNull @Positive Long corporationId) {
}
