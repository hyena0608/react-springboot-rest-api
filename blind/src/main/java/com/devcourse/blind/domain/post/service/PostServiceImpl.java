package com.devcourse.blind.domain.post.service;

import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.member.repository.MemberRepository;
import com.devcourse.blind.domain.post.domain.Post;
import com.devcourse.blind.domain.post.message.PostExceptionMessage;
import com.devcourse.blind.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public Post save(Post post) {
        Long memberId = post.getMember().getId();
        Member findMember = memberRepository.findById(memberId);
        if (!findMember.isExists()) {
            throw new IllegalStateException(PostExceptionMessage.POST_INSERT_EXCEPTION.getMessage());
        }
        return postRepository.save(post);
    }

    @Override
    public Page<Post>findPostsByCategoryId(int page, int size, long categoryId) {
        return postRepository.findByCategoryId(PageRequest.of(page, size), categoryId);
    }

    @Override
    public List<Post> findPostsByTitle(String title) {
        return postRepository.findByPostTitle(title);
    }

    @Override
    public List<Post> findPostsCategories() {
        return postRepository.findPostsCategories();
    }

    @Override
    public boolean delete(long postId) {
        return postRepository.delete(postId);
    }
}
