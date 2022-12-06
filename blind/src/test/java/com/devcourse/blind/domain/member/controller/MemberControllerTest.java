package com.devcourse.blind.domain.member.controller;

import com.devcourse.blind.config.RestDocsConfiguration;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.member.service.MemberService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

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

    private Member member = Member.builder()
            .id(1L)
            .userId("testUserId")
            .nickname("testNickname")
            .username("testUsername")
            .build();

    @Test
    void saveMemberTest() throws Exception {
        when(memberService.save(any())).thenReturn(member);
        mockMvc.perform(post("/members")
                        .content("{\"userId\": \"testUserId\",  \n" +
                                "\"nickname\": \"testNickname\",  \n" +
                                "\"username\": \"testUsername\",  \n" +
                                "\"corporationId\": 1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("userId").description("회원 아이디"),
                                fieldWithPath("nickname").description("회원 닉네임"),
                                fieldWithPath("username").description("회원 이름"),
                                fieldWithPath("corporationId").description("회원 기업 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.id").description("회원 번호"),
                                fieldWithPath("stock.userId").description("회원 아이디"),
                                fieldWithPath("stock.nickname").description("회원 닉네임"),
                                fieldWithPath("stock.username").description("회원 이름"),
                                fieldWithPath("stock.createdAt").description("생성 시간")

                        )));
    }
}