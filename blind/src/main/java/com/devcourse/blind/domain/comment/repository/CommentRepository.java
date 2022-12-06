package com.devcourse.blind.domain.comment.repository;

import com.devcourse.blind.domain.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(long commendId);
    Page<Comment> findAllByPostId(Pageable page, long postId);
    boolean deleteById(long commentId);
}
