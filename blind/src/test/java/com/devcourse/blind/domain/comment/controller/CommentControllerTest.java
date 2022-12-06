package com.devcourse.blind.domain.comment.controller;

import com.devcourse.blind.config.RestDocsConfiguration;
import com.devcourse.blind.domain.comment.domain.Comment;
import com.devcourse.blind.domain.comment.service.CommentService;
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
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

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

    private Comment comment = Comment.builder()
            .id(1L)
            .content("testContent")
            .username("testUsername")
            .corporationTitle("testCorporationTitle")
            .likeCount(0)
            .build();

    @Test
    void saveCommentTest() throws Exception {
        when(commentService.save(any())).thenReturn(comment);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"testContent\", \n" +
                                "\"username\": \"testUsername\",  \n" +
                                "\"corporationTitle\": \"testCorporationTitle\",  \n" +
                                "\"postId\": 1,  \n" +
                                "\"memberId\": 1}"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("content").description("댓글 내용"),
                                fieldWithPath("username").description("댓글 작성자 이름"),
                                fieldWithPath("corporationTitle").description("댓글 작성자 기업명"),
                                fieldWithPath("postId").description("게시글 번호"),
                                fieldWithPath("memberId").description("댓글 작성자 회원 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.id").description("댓글 번호"),
                                fieldWithPath("stock.content").description("댓글 내용"),
                                fieldWithPath("stock.username").description("댓글 작성자 이름"),
                                fieldWithPath("stock.corporationTitle").description("댓글 작성자 기업명"),
                                fieldWithPath("stock.likeCount").description("댓글 추천수"),
                                fieldWithPath("stock.createdAt").description("생성 시간")
                        )
                ));
    }

    @Test
    void getCommentsByPostIdTest() throws Exception {
        when(commentService.findAllByPostId(anyInt(), anyInt(), anyLong()))
                .thenReturn(new PageImpl<>(List.of(comment), PageRequest.of(0, 2), 1));

        mockMvc.perform(get("/comments?postId=1&page=0&size=2"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        queryParameters(
                                parameterWithName("postId").description("게시글 번호"),
                                parameterWithName("page").description("댓글 페이지 번호"),
                                parameterWithName("size").description("댓글 페이징 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.postId").description("게시글 번호"),
                                fieldWithPath("stock.pageInfo").description("댓글 페이징 정보"),
                                fieldWithPath("stock.pageInfo.sort").description("댓글 페이징 정렬 정보"),
                                fieldWithPath("stock.pageInfo.sort.empty").description("댓글 페이징 정렬 데이터 상태"),
                                fieldWithPath("stock.pageInfo.sort.unsorted").description("댓글 페이징 정렬 상태 X"),
                                fieldWithPath("stock.pageInfo.sort.sorted").description("댓글 페이징 정렬 상태 O"),
                                fieldWithPath("stock.pageInfo.offset").description("댓글 오프셋"),
                                fieldWithPath("stock.pageInfo.pageNumber").description("댓글 페이지 번호"),
                                fieldWithPath("stock.pageInfo.pageSize").description("댓글 페이징 사이즈"),
                                fieldWithPath("stock.pageInfo.unpaged").description("댓글 페이징 상태 X"),
                                fieldWithPath("stock.pageInfo.paged").description("댓글 페이징 상태 O"),
                                fieldWithPath("stock.comments.[].id").description("댓글 번호"),
                                fieldWithPath("stock.comments.[].content").description("댓글 내용"),
                                fieldWithPath("stock.comments.[].username").description("댓글 작성자 이름"),
                                fieldWithPath("stock.comments.[].corporationTitle").description("댓글 작성자 기업명"),
                                fieldWithPath("stock.comments.[].likeCount").description("댓글 추천수"),
                                fieldWithPath("stock.comments.[].createdAt").description("생성 시간")
                        )
                ));
    }

    @Test
    void deleteByIdTest() throws Exception {
        when(commentService.deleteById(1)).thenReturn(Boolean.TRUE);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/comments/{commentId}", 1L))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("commentId").description("댓글 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock").description("데이터 삭제 여부")
                        )
                ));
    }
}