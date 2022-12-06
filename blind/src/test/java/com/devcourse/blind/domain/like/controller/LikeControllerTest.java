package com.devcourse.blind.domain.like.controller;

import com.devcourse.blind.config.RestDocsConfiguration;
import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;
import com.devcourse.blind.domain.like.domain.TargetType;
import com.devcourse.blind.domain.like.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class LikeControllerTest {

    @MockBean
    private LikeService likeService;

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

    private Like like = Like.builder()
            .id(1L)
            .targetType(TargetType.POST)
            .targetId(1L)
            .likeType(LikeType.LIKE)
            .build();

    @Test
    void saveLikeTest() throws Exception {
        when(likeService.save(any())).thenReturn(like);
        mockMvc.perform(post("/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"likeType\": \"LIKE\",  \n" +
                                "\"targetType\": \"POST\",  \n" +
                                "\"targetId\": 1,  \n" +
                                "\"memberId\": 1}"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("likeType").description("추천/비추천 타입"),
                                fieldWithPath("targetType").description("게시판/댓글 타입"),
                                fieldWithPath("targetId").description("게시판/댓글 번호"),
                                fieldWithPath("memberId").description("회원 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.id").description("추천 번호"),
                                fieldWithPath("stock.likeType").description("추천/비추천 타입"),
                                fieldWithPath("stock.targetType").description("게시글/댓글 타입"),
                                fieldWithPath("stock.targetId").description("게시글/댓글 번호")
                        )
                ));
    }

    @Test
    void updateLike() throws Exception {
        when(likeService.updateLikeTypeById(1L, LikeType.LIKE)).thenReturn(like);
        mockMvc.perform(patch("/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"likeId\": 1,  \n" +
                                "\"likeType\": \"LIKE\"}"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("likeId").description("추천 번호"),
                                fieldWithPath("likeType").description("추천/비추천 타입")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.id").description("추천 번호"),
                                fieldWithPath("stock.likeType").description("추천/비추천 타입"),
                                fieldWithPath("stock.targetType").description("게시글/댓글 타입"),
                                fieldWithPath("stock.targetId").description("게시글/댓글 번호")
                        )
                ));
    }
}