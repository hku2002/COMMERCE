package com.commerce.user.service;

import com.commerce.user.domain.Member;
import com.commerce.user.dto.JoinMemberDto;
import com.commerce.user.domain.MemberRepository;
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
     * param joinMemberDto 회원 가입 정보
     */
    @Transactional
    public void join(JoinMemberDto joinMemberDto) {
        Member.checkMemberDuplicate(memberRepository.findByUserIdAndActivated(joinMemberDto.getUserId(), true));
        joinMemberDto.encryptPassword(joinMemberDto.getPassword());
        memberRepository.save(joinMemberDto.toEntity()).getId();
    }

}
