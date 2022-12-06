package com.devcourse.blind.domain.corporation.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Corporation extends BaseEntity {

    private final Long id;
    private String title;
    private CorporationSizeType sizeType;
    private CorporationBusinessType businessType;
    private String headOfficeLocation;
    private int minAnnualSalary;
    private int maxAnnualSalary;
    private double reviewAveragePoint;

    public static CorporationBuilder builderFrom(Corporation corporation) {
        return Corporation.builder()
                .title(corporation.title)
                .sizeType(corporation.sizeType)
                .businessType(corporation.businessType)
                .headOfficeLocation(corporation.headOfficeLocation)
                .minAnnualSalary(corporation.minAnnualSalary)
                .maxAnnualSalary(corporation.maxAnnualSalary)
                .reviewAveragePoint(corporation.reviewAveragePoint);
    }

    public boolean isExists() {
        return this.id != 0L;
    }
}
