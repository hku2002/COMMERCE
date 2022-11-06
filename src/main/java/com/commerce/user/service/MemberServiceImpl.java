package com.commerce.user.service;

import com.commerce.user.domain.Member;
import com.commerce.user.dto.JoinMemberDto;
import com.commerce.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * param member
     */
    @Transactional
    public Long join(JoinMemberDto joinMemberDto) {
        Member member = memberRepository.findByUserIdAndActivated(joinMemberDto.getUserId(), true);
        member.checkMemberDuplicate(member);
        return memberRepository.save(joinMemberDto.toEntity()).getId();
    }

    /**
     * 회원 조회
     * param userId
     */
    public Member findOne(String userId) {
        Member member = memberRepository.findByUserIdAndActivated(userId, true);
        member.checkMemberExist(member);
        return member;
    }

}
