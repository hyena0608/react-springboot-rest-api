package com.devcourse.blind.domain.member.controller;

import com.devcourse.blind.base.controller.response.BaseResponseBody;
import com.devcourse.blind.domain.corporation.domain.Corporation;
import com.devcourse.blind.domain.member.controller.request.CreateMemberRequest;
import com.devcourse.blind.domain.member.controller.response.MemberResponse;
import com.devcourse.blind.domain.member.domain.Member;
import com.devcourse.blind.domain.member.message.MemberResponseMessage;
import com.devcourse.blind.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public BaseResponseBody<MemberResponse> saveMember(@RequestBody CreateMemberRequest request) {
        Member member = Member.builder()
                .userId(request.userId())
                .username(request.username())
                .nickname(request.nickname())
                .corporation(new Corporation(request.corporationId()))
                .build();
        Member savedMember = memberService.save(member);
        MemberResponse parsedMember = MemberResponse.from(savedMember);
        return BaseResponseBody.of(MemberResponseMessage.POST_SAVE.getTitle(), MemberResponseMessage.POST_SAVE.getContent(), parsedMember);
    }
}
