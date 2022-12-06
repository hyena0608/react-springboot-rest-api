package com.devcourse.blind.domain.corporation.service;

import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.corporation.repository.CorporationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorporationServiceImpl implements CorporationService {

    private final CorporationRepository corporationRepository;

    @Override
    public Corporation save(Corporation corporation) {
        return corporationRepository.save(corporation);
    }

    @Override
    public List<Corporation> findByTitle(String title) {
        return corporationRepository.findByTitle(title);
    }

    @Override
    public List<Corporation> findOrderByReviewPoint() {
        return corporationRepository.findOrderByReviewPoint();
    }
}
