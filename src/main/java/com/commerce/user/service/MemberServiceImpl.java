package com.commerce.user.service;

import com.commerce.user.domain.Member;
import com.commerce.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


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
    public Long join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member).getId();
    }

    /**
     * 회원 조회
     * param userId
     */
    public Member findOne(String userId) {
        Member member = memberRepository.findByUserId(userId);
        if (ObjectUtils.isEmpty(member)) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        return member;
    }

    /**
     * 회원 중복 검사
     * param member
     */
    private void validateDuplicateMember(Member member) {
        if (!ObjectUtils.isEmpty(memberRepository.findByUserId(member.getUserId()))) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
