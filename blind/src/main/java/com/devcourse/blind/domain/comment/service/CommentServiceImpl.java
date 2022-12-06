package com.devcourse.blind.domain.comment.service;

import com.devcourse.blind.domain.comment.domain.Comment;
import com.devcourse.blind.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> findAllByPostId(int page, int size, long postId) {
        return commentRepository.findAllByPostId(PageRequest.of(page, size), postId);
    }

    @Override
    public boolean deleteById(long commentId) {
        return commentRepository.deleteById(commentId);
    }
}
