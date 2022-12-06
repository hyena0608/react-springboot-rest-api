package com.devcourse.blind.domain.post.repository;

import com.devcourse.blind.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findByPostId(long postId);
    Page<Post> findByCategoryId(Pageable page, long categoryId);
    List<Post> findByPostTitle(String postTitle);
    List<Post> findPostsCategories();
    boolean delete(long postId);
}
