package com.devcourse.blind.domain.corporation.controller;

import com.devcourse.blind.config.RestDocsConfiguration;
import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.corporation.service.CorporationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.devcourse.blind.domain.corporation.domain.CorporationBusinessType.BUSINESS_FACILITIES_MANAGEMENT_SERVICE_BUSINESS;
import static com.devcourse.blind.domain.corporation.domain.CorporationSizeType.FIVE_HUNDRED_TO_ONE_THOUSAND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class CorporationControllerTest {

    @MockBean
    private CorporationService corporationService;

    @Autowired
    private RestDocumentationResultHandler restDocs;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(restDocs)
                .build();
    }

    private Corporation corporation = Corporation.builder()
            .id(1L)
            .title("testTitle")
            .sizeType(FIVE_HUNDRED_TO_ONE_THOUSAND)
            .businessType(BUSINESS_FACILITIES_MANAGEMENT_SERVICE_BUSINESS)
            .headOfficeLocation("testHeadOfficeLocation")
            .minAnnualSalary(0)
            .maxAnnualSalary(0)
            .build();

    @Test
    void getCorporationsByTitle() throws Exception {
        when(corporationService.findByTitle(any())).thenReturn(List.of(corporation));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/corporations/{title}", "testTitle"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("title").description("기업명")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.[].id").description("기업 번호"),
                                fieldWithPath("stock.[].title").description("기업명"),
                                fieldWithPath("stock.[].sizeType").description("기업 규모"),
                                fieldWithPath("stock.[].businessType").description("기업 타입"),
                                fieldWithPath("stock.[].headOfficeLocation").description("본사 위치"),
                                fieldWithPath("stock.[].minAnnualSalary").description("최소 연봉"),
                                fieldWithPath("stock.[].maxAnnualSalary").description("최대 연봉"),
                                fieldWithPath("stock.[].reviewAveragePoint").description("기업 리뷰 평균 점수"),
                                fieldWithPath("stock.[].createdAt").description("생성 시간")
                        )));
    }

    @Test
    void getCorporationChartTest() throws Exception {
        when(corporationService.findOrderByReviewPoint()).thenReturn(List.of(corporation));

        mockMvc.perform(get("/corporations/chart"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.[].id").description("기업 번호"),
                                fieldWithPath("stock.[].title").description("기업명")
                        )
                ));
    }
}