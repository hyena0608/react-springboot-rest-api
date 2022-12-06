package com.devcourse.blind.domain.post.controller;

import com.devcourse.blind.base.controller.response.BaseResponseBody;
import com.devcourse.blind.domain.category.domain.Category;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.post.controller.request.CreatePostRequest;
import com.devcourse.blind.domain.post.controller.response.PostResponse;
import com.devcourse.blind.domain.post.controller.response.PostsCategoriesResponse;
import com.devcourse.blind.domain.post.controller.response.PostsResponse;
import com.devcourse.blind.domain.post.domain.Post;
import com.devcourse.blind.domain.post.service.PostService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devcourse.blind.domain.post.message.PostResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public BaseResponseBody<PostResponse> savePost(@RequestBody CreatePostRequest request) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .userId(request.userId())
                .username(request.username())
                .corporationTitle(request.corporationTitle())
                .category(new Category(request.categoryId()))
                .member(new Member(request.memberId()))
                .build();
        Post savedPost = postService.save(post);
        PostResponse parsedPost = PostResponse.from(savedPost);
        return BaseResponseBody.of(POST_SAVE.getTitle(), POST_SAVE.getContent(), parsedPost);
    }

    @DeleteMapping("/{postId}")
    public BaseResponseBody<Boolean> deletePost(@PathVariable Long postId) {
        boolean isDeleted = postService.delete(postId);
        return BaseResponseBody.of(POST_DELETE.getTitle(), POST_DELETE.getContent(), isDeleted);
    }

    @GetMapping("/category")
    public BaseResponseBody<PostsResponse> getPostsByCategoryId(@RequestParam @NotNull @Positive long categoryId,
                                                                @RequestParam @NotNull @PositiveOrZero int page,
                                                                @RequestParam @NotNull @Positive int size
    ) {
        Page<Post> postPage = postService.findPostsByCategoryId(page, size, categoryId);
        List<PostResponse> findPosts = postPage.getContent()
                .stream()
                .map(PostResponse::from)
                .toList();
        PostsResponse posts = PostsResponse.from(findPosts, postPage.getPageable(), categoryId);
        return BaseResponseBody.of(POSTS_BY_CATEGORY.getTitle(), POSTS_BY_CATEGORY.getContent(), posts);
    }

    @GetMapping
    public BaseResponseBody<List<PostsCategoriesResponse>> getPostsCategories() {
        List<PostsCategoriesResponse> posts = postService.findPostsCategories()
                .stream()
                .map(PostsCategoriesResponse::from)
                .toList();
        return BaseResponseBody.of(CATEGORIES_POSTS_FIND.getTitle(), CATEGORIES_POSTS_FIND.getContent(), posts);
    }
}
