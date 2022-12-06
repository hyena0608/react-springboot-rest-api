package com.devcourse.blind.domain.corporation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CorporationBusinessType {
    AGRICULTURE_FISHERY_INDUSTRY("농업"),
    FORESTRY_INDUSTRY("임업"),
    FISHERY_INDUSTRY("어업"),
    MINING_INDUSTRY("광업"),
    MANUFACTURING_INDUSTRY("제조업"),
    ELECTRICITY_CONDITIONING_SUPPLY("전기 조절 공급업"),
    GAS_CONDITIONING_SUPPLY("가스 조절 공급업"),
    AIR_CONDITIONING_SUPPLY("공기 조절 공급업"),
    SEWERAGE_WASTE_DISPOSAL("수도, 하수 및 폐기물 처리, 원료 재생업"),
    RAW_MATERIAL_REGENERATION_BUSINESS("원료 재생업"),
    CONSTRUCTION_INDUSTRY("건설업"),
    WHOLESALE_RETAIL_BUSINESS("도매 및 소매업"),
    TRANSPORT_WAREHOUSING_BUSINESS("운수 및 창고업"),
    LODGINGS_RESTAURANT_BUSINESS("숙박 및 음식점업"),
    TELECOMMUNICATION_OF_INFORMATION_BUSINESS("정보통신업"),
    FINANCE_INSURANCE_BUSINESS("금융 및 보험업"),
    REAL_ESTATE_BUSINESS("부동산업"),
    SCIENCE_TECHNOLOGY_SERVICE_BUSINESS("전문, 과학 및 기술 서비스업"),
    BUSINESS_FACILITIES_MANAGEMENT_SERVICE_BUSINESS("사업시설 관리 서비스업"),
    BUSINESS_SUPPORT_SERVICE_BUSINESS("사업 지원 서비스업"),
    LEASE_BUSINESS("임대 서비스업"),
    PUBLIC_ADMINISTRATION_DEFENCE_AND_SOCIAL_SECURITY("공공 행정, 국방 및 사회보장 행정"),
    EDUCATIONAL_SERVICE_BUSINESS("교육 서비스업"),
    HEALTH_AND_SOCIAL_WELFARE_SERVICE_BUSINESS("보건업 및 사회복지 서비스업"),
    ART_AND_SPORT_AND_LEISURE_SERVICE_BUSINESS("예술, 스포츠 및 여가관련 서비스업"),
    UNION_AND_ORGANIZATIONS_AND_PERSONAL_SERVICE_BUSINESS("협회 및 단체, 수리 및 기타 개인 서비스업");

    private final String title;
}
