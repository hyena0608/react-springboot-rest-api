package com.devcourse.blind.domain.comment.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {

    private final Long id;
    private String content;
    private String username;
    private String corporationTitle;
    private int likeCount;

    private Post post;
    private Member member;

    public static CommentBuilder builderFrom(Comment comment) {
        return Comment.builder()
                .content(comment.content)
                .username(comment.username)
                .corporationTitle(comment.corporationTitle)
                .likeCount(comment.likeCount)
                .post(comment.post)
                .member(comment.member);
    }

    public boolean isExists() {
        return this.id == 0L;
    }
}
