package com.devcourse.blind.domain.comment.controller;

import com.devcourse.blind.base.controller.response.BaseResponseBody;
import com.devcourse.blind.domain.comment.controller.request.CreateCommentRequest;
import com.devcourse.blind.domain.comment.controller.response.CommentResponse;
import com.devcourse.blind.domain.comment.controller.response.CommentsResponse;
import com.devcourse.blind.domain.comment.domain.Comment;
import com.devcourse.blind.domain.comment.service.CommentService;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devcourse.blind.base.controller.response.BaseResponseBody.of;
import static com.devcourse.blind.domain.comment.message.CommentResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public BaseResponseBody<CommentResponse> saveComment(@RequestBody CreateCommentRequest request) {
        Comment comment = Comment.builder()
                .content(request.content())
                .username(request.username())
                .corporationTitle(request.corporationTitle())
                .post(new Post(request.postId()))
                .member(new Member(request.memberId()))
                .build();
        Comment savedComment = commentService.save(comment);
        CommentResponse parsedComment = CommentResponse.from(savedComment);
        return of(COMMENT_SAVE.getTitle(), COMMENT_SAVE.getContent(), parsedComment);
    }

    @GetMapping
    public BaseResponseBody<CommentsResponse> getCommentsByPostId(long postId,
                                                                  int page,
                                                                  int size
    ) {
        Page<Comment> commentPage = commentService.findAllByPostId(page, size, postId);
        List<CommentResponse> findComments = commentPage.getContent()
                .stream()
                .map(CommentResponse::from)
                .toList();
        CommentsResponse comments = CommentsResponse.from(findComments, commentPage.getPageable(), postId);
        return BaseResponseBody.of(COMMENTS_BY_POST_ID.getTitle(), COMMENTS_BY_POST_ID.getContent(), comments);
    }

    @DeleteMapping("/{commentId}")
    public BaseResponseBody<Boolean> deleteById(@PathVariable long commentId) {
        boolean isDeleted = commentService.deleteById(commentId);
        return BaseResponseBody.of(COMMENT_DELETE.getTitle(), COMMENT_DELETE.getContent(), isDeleted);
    }
}
