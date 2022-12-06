package com.devcourse.blind.domain.post.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import com.devcourse.blind.domain.category.domain.Category;
import com.devcourse.blind.domain.member.domain.Member;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseEntity {

    private final Long id;
    private String title;
    private String content;
    private String userId;
    private String username;
    private String corporationTitle;
    private int hitCount;
    private int likeCount;
    private Member member;
    private Category category;

    public static PostBuilder builderFrom(Post post) {
        return Post.builder()
                .title(post.title)
                .content(post.content)
                .hitCount(post.hitCount)
                .likeCount(post.likeCount)
                .userId(post.userId)
                .username(post.username)
                .corporationTitle(post.corporationTitle)
                .member(post.member)
                .category(post.category);
    }

    public boolean isExists() {
        return this.id != 0L;
    }
}
