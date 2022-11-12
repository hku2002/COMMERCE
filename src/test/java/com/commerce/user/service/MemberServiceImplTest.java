package com.commerce.user.service;

import com.commerce.global.common.exception.BadRequestException;
import com.commerce.user.domain.Member;
import com.commerce.user.dto.JoinMemberDto;
import com.commerce.user.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberServiceImpl;

    private static final String USER_SAME_ID = "testId";
    private static final String USER_ID = "testId2";

    @Test
    @DisplayName("회원가입 시 중복회원이 존재할 경우 예외를 던진다.")
    void joinDuplicateMemberExistThrow() {
        // given
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean()))
                .willReturn(Member.builder().id(1L).userId(USER_SAME_ID).build());

        // when
        JoinMemberDto joinMemberDto = new JoinMemberDto();
        joinMemberDto.setUserId(USER_SAME_ID);

        // then
        assertThatThrownBy(() -> memberServiceImpl.join(joinMemberDto)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("회원가입 시 save 함수를 한번 호출한다.")
    void joinCheckSaveMethodCall() {
        // given
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(null);
        given(memberRepository.save(any(Member.class))).willReturn(Member.builder().id(1L).userId(USER_ID).build());

        // when
        JoinMemberDto joinMemberDto = new JoinMemberDto();
        joinMemberDto.setUserId(USER_ID);
        joinMemberDto.encryptPassword("testPassword");
        memberServiceImpl.join(joinMemberDto);

        // then
        verify(memberRepository, times(1)).save(any());
    }

}