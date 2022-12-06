package com.devcourse.blind.domain.corporation.controller.response;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.corporation.domain.CorporationBusinessType;
import com.devcourse.blind.domain.corporation.domain.CorporationSizeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CorporationResponse {

    private final Long id;
    private final String title;
    private final CorporationSizeType sizeType;
    private final CorporationBusinessType businessType;
    private final String headOfficeLocation;
    private final int minAnnualSalary;
    private final int maxAnnualSalary;
    private final double reviewAveragePoint;
    private final LocalDateTime createdAt;

    public static CorporationResponse from(Corporation corporation) {
        return CorporationResponse.builder()
                .id(corporation.getId())
                .title(corporation.getTitle())
                .sizeType(corporation.getSizeType())
                .businessType(corporation.getBusinessType())
                .headOfficeLocation(corporation.getHeadOfficeLocation())
                .minAnnualSalary(corporation.getMinAnnualSalary())
                .maxAnnualSalary(corporation.getMaxAnnualSalary())
                .reviewAveragePoint(corporation.getReviewAveragePoint())
                .createdAt(corporation.getCreatedAt())
                .build();
    }
}
