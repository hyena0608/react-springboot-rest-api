package com.devcourse.blind.domain.corporation.controller.response;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CorporationChartResponse {

    private final Long id;
    private final String title;

    public static CorporationChartResponse from(Corporation corporation) {
        return CorporationChartResponse.builder()
                .id(corporation.getId())
                .title(corporation.getTitle())
                .build();
    }
}
