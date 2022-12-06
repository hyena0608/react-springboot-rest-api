package com.devcourse.blind.domain.member.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import com.devcourse.blind.domain.corporation.domain.Corporation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Member extends BaseEntity {

    private final Long id;
    private String userId;
    private String nickname;
    private String username;

    private Corporation corporation;

    public boolean isExists() {
        return this.id != 0L;
    }
}
