package com.devcourse.blind.domain.like.service;

import com.devcourse.blind.domain.comment.domain.Comment;
import com.devcourse.blind.domain.comment.repository.CommentRepository;
import com.devcourse.blind.domain.like.domain.Like;
import com.devcourse.blind.domain.like.domain.LikeType;
import com.devcourse.blind.domain.like.domain.TargetType;
import com.devcourse.blind.domain.like.repository.LikeRepository;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.member.repository.MemberRepository;
import com.devcourse.blind.domain.post.domain.Post;
import com.devcourse.blind.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devcourse.blind.domain.like.message.LikeExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Override
    public Like save(Like like) {
        Long memberId = like.getMember().getId();
        Long targetId = like.getTargetId();
        TargetType targetType = like.getTargetType();
        validateTargetExists(targetId, targetType);
        Member findMember = memberRepository.findById(memberId);
        validateMemberExists(findMember);
        Like findLike = likeRepository.findBy(memberId, targetId, targetType);
        validateLikeDuplicated(findLike);

        return likeRepository.save(like);
    }

    private void validateTargetExists(Long targetId, TargetType targetType) {
        if (targetType.isPost()) {
            Post findPost = postRepository.findByPostId(targetId);
            if (!findPost.isExists()) {
                throw new IllegalStateException(LIKE_INSERT_TARGET_EXCEPTION.getMessage());
            }
        }
        if (targetType.isComment()) {
            Comment findComment = commentRepository.findById(targetId);
            if(!findComment.isExists()) {
                throw new IllegalStateException(LIKE_INSERT_TARGET_EXCEPTION.getMessage());
            }
        }
    }

    private static void validateLikeDuplicated(Like findLike) {
        if (findLike.isExists()) {
            throw new IllegalStateException(LIKE_INSERT_DUPLICATED.getMessage());
        }
    }

    private static void validateMemberExists(Member findMember) {
        if (!findMember.isExists()) {
            throw new IllegalStateException(LIKE_INSERT_MEMBER_EXCEPTION.getMessage());
        }
    }

    @Override
    public Like updateLikeTypeById(Long likeId, LikeType likeType) {
        return likeRepository.updateLikeTypeById(likeId, likeType);
    }
}
