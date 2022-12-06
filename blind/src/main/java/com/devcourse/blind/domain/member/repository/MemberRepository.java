package com.devcourse.blind.domain.member.repository;

import com.devcourse.blind.domain.member.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Member findById(long memberId);
}
