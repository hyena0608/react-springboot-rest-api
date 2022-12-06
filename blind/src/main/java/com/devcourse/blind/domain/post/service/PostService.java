package com.devcourse.blind.domain.post.service;

import com.devcourse.blind.domain.post.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    Post save(Post post);
    Page<Post> findPostsByCategoryId(int page, int size, long categoryId);
    List<Post> findPostsByTitle(String title);
    List<Post> findPostsCategories();
    boolean delete(long postId);
}
