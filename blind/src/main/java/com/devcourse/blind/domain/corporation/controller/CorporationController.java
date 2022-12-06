package com.devcourse.blind.domain.corporation.controller;

import com.devcourse.blind.base.controller.response.BaseResponseBody;
import com.devcourse.blind.domain.corporation.controller.response.CorporationChartResponse;
import com.devcourse.blind.domain.corporation.controller.response.CorporationResponse;
import com.devcourse.blind.domain.corporation.service.CorporationService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.devcourse.blind.domain.corporation.message.CorporationResponseMessage.CORPORATION_CHART;
import static com.devcourse.blind.domain.corporation.message.CorporationResponseMessage.CORPORATION_SELECT_BY_TITLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corporations")
public class CorporationController {

    private final CorporationService corporationService;

    @GetMapping("/{title}")
    public BaseResponseBody<List<CorporationResponse>> getCorporationsByTitle(@PathVariable @NotBlank String title) {
        List<CorporationResponse> corporations = corporationService.findByTitle(title)
                .stream()
                .map(CorporationResponse::from)
                .toList();
        return BaseResponseBody.of(CORPORATION_SELECT_BY_TITLE.getTitle(), CORPORATION_SELECT_BY_TITLE.getContent(), corporations);
    }

    @GetMapping("/chart")
    public BaseResponseBody<List<CorporationChartResponse>> getCorporationChart() {
        List<CorporationChartResponse> corporationChart = corporationService.findOrderByReviewPoint()
                .stream()
                .map(CorporationChartResponse::from)
                .toList();
        return BaseResponseBody.of(CORPORATION_CHART.getTitle(), CORPORATION_CHART.getContent(), corporationChart);
    }
}
