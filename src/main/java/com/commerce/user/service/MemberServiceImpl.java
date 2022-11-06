package com.commerce.user.service;

import com.commerce.global.common.exception.BadRequestException;
import com.commerce.user.domain.Member;
import com.commerce.user.dto.JoinMemberDto;
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
     * param joinMemberDto 회원 가입 정보
     */
    @Transactional
    public void join(JoinMemberDto joinMemberDto) {
        checkMemberDuplicate(memberRepository.findByUserIdAndActivated(joinMemberDto.getUserId(), true));
        joinMemberDto.encryptPassword(joinMemberDto.getPassword());
        memberRepository.save(joinMemberDto.toEntity()).getId();
    }

    /**
     * 회원 조회
     * param userId 회원 아이디
     */
    public Member findOne(String userId) {
        Member member = memberRepository.findByUserIdAndActivated(userId, true);
        member.checkMemberExist(member);
        return member;
    }

    /**
     * 회원 중복 체크
     * @param member 회원 객체
     */
    private void checkMemberDuplicate(Member member) {
        if (!ObjectUtils.isEmpty(member)) {
            throw new BadRequestException("이미 가입된 회원이 존재합니다.");
        }
    }

}
