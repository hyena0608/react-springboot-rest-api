package com.devcourse.blind.domain.corporation.service;

import com.devcourse.blind.domain.corporation.domain.Corporation;

import java.util.List;

public interface CorporationService {
    Corporation save(Corporation corporation);
    List<Corporation> findByTitle(String title);
    List<Corporation> findOrderByReviewPoint();
}
