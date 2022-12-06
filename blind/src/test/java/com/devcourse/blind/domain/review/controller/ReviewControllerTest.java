package com.devcourse.blind.domain.review.controller;

import com.devcourse.blind.config.RestDocsConfiguration;
import com.devcourse.blind.domain.review.domain.Review;
import com.devcourse.blind.domain.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

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

    private Review review = Review.builder()
            .id(1L)
            .corporationTitle("testCorporationTitle")
            .totalPoint(2)
            .careerImprovementPoint(2)
            .workLifeBalancePoint(2)
            .salaryWelfarePoint(2)
            .corporateCulturePoint(2)
            .managementPoint(2)
            .oneSentenceComment("testOneSentenceComment")
            .pros("testPros")
            .cons("testCons")
            .build();

    @Test
    void saveReviewTest() throws Exception {
        when(reviewService.save(any())).thenReturn(review);
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"corporationTitle\": \"testCorporationTitle\", \n" +
                                "\"totalPoint\": 2,  \n" +
                                "\"careerImprovementPoint\": 2,  \n" +
                                "\"workLifeBalancePoint\": 2,  \n" +
                                "\"salaryWelfarePoint\": 2,  \n" +
                                "\"corporateCulturePoint\": 2,  \n" +
                                "\"managementPoint\": 2,  \n" +
                                "\"oneSentenceComment\": \"testOneSentenceComment\",  \n" +
                                "\"pros\": \"testPros\",  \n" +
                                "\"cons\": \"testCons\",  \n" +
                                "\"corporationId\": 1,  \n" +
                                "\"memberId\": 1}"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("corporationTitle").description("기업명"),
                                fieldWithPath("totalPoint").description("기업 총 점수"),
                                fieldWithPath("careerImprovementPoint").description("기업 커리어 향상 점수"),
                                fieldWithPath("workLifeBalancePoint").description("기업 업무와 삶의 균형 점수"),
                                fieldWithPath("salaryWelfarePoint").description("기업 급여 및 복지 점수"),
                                fieldWithPath("corporateCulturePoint").description("기업 사내 문화 점수"),
                                fieldWithPath("managementPoint").description("기업 경영진 점수"),
                                fieldWithPath("oneSentenceComment").description("기업 한 줄 평가"),
                                fieldWithPath("pros").description("기업 장점"),
                                fieldWithPath("cons").description("기업 단점"),
                                fieldWithPath("corporationId").description("기업 번호"),
                                fieldWithPath("memberId").description("기업 리뷰 작성 회원 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.id").description("리뷰 번호"),
                                fieldWithPath("stock.corporationTitle").description("기업명"),
                                fieldWithPath("stock.totalPoint").description("기업 총 점수"),
                                fieldWithPath("stock.careerImprovementPoint").description("기업 커리어 향상 점수"),
                                fieldWithPath("stock.workLifeBalancePoint").description("기업 업무와 삶의 균형 점수"),
                                fieldWithPath("stock.salaryWelfarePoint").description("기업 급여 및 복지 점수"),
                                fieldWithPath("stock.corporateCulturePoint").description("기업 사내 문화 점수"),
                                fieldWithPath("stock.managementPoint").description("기업 경영진 점수"),
                                fieldWithPath("stock.oneSentenceComment").description("기업 한 줄 평가"),
                                fieldWithPath("stock.pros").description("기업 장점"),
                                fieldWithPath("stock.cons").description("기업 단점"),
                                fieldWithPath("stock.createdAt").description("생성시간")
                        )
                ));
    }

    @Test
    void getReviewsByCorporationIdTest() throws Exception {
        when(reviewService.findReviewsByCorporationId(anyInt(), anyInt(), anyLong()))
                .thenReturn(new PageImpl<>(List.of(review), PageRequest.of(0, 2), 1));

        mockMvc.perform(get("/reviews/corporation?corporationId=1&page=0&size=2"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        queryParameters(
                                parameterWithName("corporationId").description("리뷰 기업 번호"),
                                parameterWithName("page").description("리뷰 페이지 번호"),
                                parameterWithName("size").description("리뷰 페이징 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.corporationId").description("리뷰 기업 번호"),
                                fieldWithPath("stock.pageInfo").description("리뷰 페이징 정보"),
                                fieldWithPath("stock.pageInfo.sort").description("리뷰 페이징 정렬 정보"),
                                fieldWithPath("stock.pageInfo.sort.empty").description("리뷰 페이징 정렬 데이터 상태"),
                                fieldWithPath("stock.pageInfo.sort.unsorted").description("리뷰 페이징 정렬 상태 X"),
                                fieldWithPath("stock.pageInfo.sort.sorted").description("리뷰 페이징 정렬 상태 O"),
                                fieldWithPath("stock.pageInfo.offset").description("리뷰 오프셋"),
                                fieldWithPath("stock.pageInfo.pageNumber").description("리뷰 페이지 번호"),
                                fieldWithPath("stock.pageInfo.pageSize").description("리뷰 페이징 사이즈"),
                                fieldWithPath("stock.pageInfo.unpaged").description("리뷰 페이징 상태 X"),
                                fieldWithPath("stock.pageInfo.paged").description("리뷰 페이징 상태 O"),
                                fieldWithPath("stock.reviews.[].id").description("리뷰 번호"),
                                fieldWithPath("stock.reviews.[].corporationTitle").description("리뷰 기업명"),
                                fieldWithPath("stock.reviews.[].totalPoint").description("리뷰 총 점수"),
                                fieldWithPath("stock.reviews.[].careerImprovementPoint").description("커리어 향상 점수"),
                                fieldWithPath("stock.reviews.[].workLifeBalancePoint").description("업무와 삶의 균형 점수"),
                                fieldWithPath("stock.reviews.[].salaryWelfarePoint").description("급여 및 복지 점수"),
                                fieldWithPath("stock.reviews.[].corporateCulturePoint").description("사내 문화 점수"),
                                fieldWithPath("stock.reviews.[].managementPoint").description("경영진 점수"),
                                fieldWithPath("stock.reviews.[].oneSentenceComment").description("한 줄 평가"),
                                fieldWithPath("stock.reviews.[].pros").description("기업 장점"),
                                fieldWithPath("stock.reviews.[].cons").description("기업 단점"),
                                fieldWithPath("stock.reviews.[].createdAt").description("생성 시간")
                        )));
    }

    @Test
    void getReviewsTest() throws Exception {
        when(reviewService.findReviews(anyInt())).thenReturn(List.of(review));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/reviews/{reviewCount}", 10))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("reviewCount").description("요청할 리뷰 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.[].id").description("리뷰 번호"),
                                fieldWithPath("stock.[].corporationTitle").description("리뷰 기업명"),
                                fieldWithPath("stock.[].totalPoint").description("리뷰 총 점수"),
                                fieldWithPath("stock.[].careerImprovementPoint").description("커리어 향상 점수"),
                                fieldWithPath("stock.[].workLifeBalancePoint").description("업무와 삶의 균형 점수"),
                                fieldWithPath("stock.[].salaryWelfarePoint").description("급여 및 복지 점수"),
                                fieldWithPath("stock.[].corporateCulturePoint").description("사내 문화 점수"),
                                fieldWithPath("stock.[].managementPoint").description("경영진 점수"),
                                fieldWithPath("stock.[].oneSentenceComment").description("한 줄 평가"),
                                fieldWithPath("stock.[].pros").description("기업 장점"),
                                fieldWithPath("stock.[].cons").description("기업 단점"),
                                fieldWithPath("stock.[].createdAt").description("생성 시간")
                        )));
    }
}