package com.devcourse.blind.domain.post.controller;

import com.devcourse.blind.config.RestDocsConfiguration;
import com.devcourse.blind.domain.category.domain.Category;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.post.domain.Post;
import com.devcourse.blind.domain.post.service.PostService;
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

import static com.devcourse.blind.domain.category.domain.CategoryTitle.BLABLA;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

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

    private Post post = Post.builder()
            .id(1L)
            .title("testTitle")
            .content("testContent")
            .userId("testUserId")
            .username("testUsername")
            .corporationTitle("testCorporationTitle")
            .hitCount(0)
            .likeCount(0)
            .member(new Member(1L))
            .category(Category.builder()
                    .id(1L)
                    .categoryTitle(BLABLA)
                    .build())
            .build();

    @Test
    void savePostTest() throws Exception {
        when(postService.save(any())).thenReturn(post);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"testTitle\", \n" +
                                "\"content\": \"testContent\",  \n" +
                                "\"userId\": \"testUserId\",  \n" +
                                "\"username\": \"testUsername\",  \n" +
                                "\"corporationTitle\": \"testCorporationTitle\",  \n" +
                                "\"categoryId\": 1,  \n" +
                                "\"memberId\": 1}"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("userId").description("게시글 작성자 아이디"),
                                fieldWithPath("username").description("게시글 작성자 이름"),
                                fieldWithPath("corporationTitle").description("게시글 작성자 기업명"),
                                fieldWithPath("categoryId").description("게시글 카테고리 번호"),
                                fieldWithPath("memberId").description("게시글 작성자 회원 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.id").description("게시글 아이디"),
                                fieldWithPath("stock.title").description("게시글 제목"),
                                fieldWithPath("stock.content").description("게시글 내용"),
                                fieldWithPath("stock.userId").description("게시글 작성자 아이디"),
                                fieldWithPath("stock.username").description("게시글 작성자 이름"),
                                fieldWithPath("stock.corporationTitle").description("게시글 작성자 기업명"),
                                fieldWithPath("stock.hitCount").description("게시글 조회수"),
                                fieldWithPath("stock.likeCount").description("게시글 추천수"),
                                fieldWithPath("stock.createdAt").description("생성시간")
                        )
                ));
    }

    @Test
    void getPostsByCategoryIdTest() throws Exception {
        when(postService.findPostsByCategoryId(anyInt(), anyInt(), anyLong()))
                .thenReturn(new PageImpl<>(List.of(post), PageRequest.of(0, 2), 1));

        mockMvc.perform(get("/posts/category?categoryId=1&page=0&size=2"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        queryParameters(
                                parameterWithName("categoryId").description("게시글 카테고리 번호"),
                                parameterWithName("page").description("게시글 페이지 번호"),
                                parameterWithName("size").description("게시글 페이징 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.categoryId").description("게시글 카테고리 번호"),
                                fieldWithPath("stock.pageInfo").description("게시글 페이징 정보"),
                                fieldWithPath("stock.pageInfo.sort").description("게시글 페이징 정렬 정보"),
                                fieldWithPath("stock.pageInfo.sort.empty").description("게시글 페이징 정렬 데이터 상태"),
                                fieldWithPath("stock.pageInfo.sort.unsorted").description("게시글 페이징 정렬 상태 X"),
                                fieldWithPath("stock.pageInfo.sort.sorted").description("게시글 페이징 정렬 상태 O"),
                                fieldWithPath("stock.pageInfo.offset").description("게시글 오프셋"),
                                fieldWithPath("stock.pageInfo.pageNumber").description("게시글 페이지 번호"),
                                fieldWithPath("stock.pageInfo.pageSize").description("게시글 페이징 사이즈"),
                                fieldWithPath("stock.pageInfo.unpaged").description("게시글 페이징 상태 X"),
                                fieldWithPath("stock.pageInfo.paged").description("게시글 페이징 상태 O"),
                                fieldWithPath("stock.posts.[].id").description("게시글 번호"),
                                fieldWithPath("stock.posts.[].title").description("게시글 제목"),
                                fieldWithPath("stock.posts.[].content").description("게시글 내용"),
                                fieldWithPath("stock.posts.[].userId").description("게시글 작성자 아이디"),
                                fieldWithPath("stock.posts.[].username").description("게시글 작성자 이름"),
                                fieldWithPath("stock.posts.[].corporationTitle").description("게시글 작성자 기업명"),
                                fieldWithPath("stock.posts.[].hitCount").description("게시글 조회수"),
                                fieldWithPath("stock.posts.[].likeCount").description("게시글 추천수"),
                                fieldWithPath("stock.posts.[].createdAt").description("게시글 생성 시간")
                        )
                ));
    }

    @Test
    void getPostsCategoriesTest() throws Exception {
        when(postService.findPostsCategories()).thenReturn(List.of(post));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.[].postId").description("게시글 번호"),
                                fieldWithPath("stock.[].postTitle").description("게시글 제목"),
                                fieldWithPath("stock.[].hitCount").description("게시글 조회수"),
                                fieldWithPath("stock.[].createdAt").description("게시글 생성 시간"),
                                fieldWithPath("stock.[].categoryId").description("게시글 카테고리 번호"),
                                fieldWithPath("stock.[].categoryTitle").description("게시글 카테고리 제목")
                        )
                ));
    }

    @Test
    void deletePostTest() throws Exception {
        when(postService.delete(anyInt())).thenReturn(Boolean.TRUE);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{postId}", 1L))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("postId").description("게시글 번호")
                        ),
                        responseFields(
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock").description("데이터 삭제 여부")
                        )
                ));
    }

}