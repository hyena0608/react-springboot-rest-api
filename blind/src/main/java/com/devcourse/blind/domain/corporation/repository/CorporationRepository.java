package com.devcourse.blind.domain.corporation.repository;

import com.devcourse.blind.domain.corporation.domain.Corporation;

import java.util.List;

public interface CorporationRepository {
    Corporation save(Corporation corporation);
    List<Corporation> findByTitle(String title);
    List<Corporation> findOrderByReviewPoint();
    Corporation findById(Long corporationId);
    Corporation updateTotalReviewPoint(Long corporationId, double reviewAveragePoint);
}
