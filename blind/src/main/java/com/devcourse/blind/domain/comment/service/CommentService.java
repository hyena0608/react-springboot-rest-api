package com.devcourse.blind.domain.comment.service;

import com.devcourse.blind.domain.comment.domain.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment save(Comment comment);
    Page<Comment> findAllByPostId(int page, int size, long postId);
    boolean deleteById(long commentId);
}
