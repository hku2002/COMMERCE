package com.commerce.user.service;

import com.commerce.global.common.Address;
import com.commerce.global.common.exception.BadRequestException;
import com.commerce.user.dto.JoinMemberDto;
import com.commerce.user.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired MemberServiceImpl memberService;
    @Autowired MemberRepository memberRepository;

    private static final String USER_ID = "testId";

    @Test
    @Description("회원가입")
    public void joinTest() {
        // given
        Address address = Address.builder()
                .address("서울시 강남구 테헤란로 427")
                .addressDetail("아이파크몰 test 호")
                .zipCode("12345")
                .build();
        JoinMemberDto joinMemberDto = new JoinMemberDto();
        joinMemberDto.setUserId(USER_ID);
        joinMemberDto.setPassword("1234");
        joinMemberDto.setUsername("홍길동");
        joinMemberDto.setEmail("test01@test.com");
        joinMemberDto.setPhoneNumber("01012345678");
        joinMemberDto.setAddress(address);

        // when
        memberService.join(joinMemberDto);

        // then
        assertThat(memberRepository.findByUserIdAndActivated(USER_ID, true).getUserId(), is(USER_ID));
    }

    @Test
    @Description("중복 회원 검사")
    public void validateDuplicateMemberTest() {
        // given
        Address address = Address.builder()
                .address("서울시 강남구 테헤란로 427")
                .addressDetail("아이파크몰 test 호")
                .zipCode("12345")
                .build();
        JoinMemberDto joinMemberDto = new JoinMemberDto();
        joinMemberDto.setUserId("testId");
        joinMemberDto.setPassword("1234");
        joinMemberDto.setUsername("홍길동");
        joinMemberDto.setEmail("test01@test.com");
        joinMemberDto.setPhoneNumber("01012345678");
        joinMemberDto.setAddress(address);

        // when
        memberRepository.save(joinMemberDto.toEntity());

        // then
        Assertions.assertThrows(BadRequestException.class, () -> {
            memberService.join(joinMemberDto);
        });
    }

    @Test
    @Description("회원이 없을 경우")
    public void userEmptyTest() {
        // given
        Address address = Address.builder()
                .address("서울시 강남구 테헤란로 427")
                .addressDetail("아이파크몰 test 호")
                .zipCode("12345")
                .build();
        JoinMemberDto joinMemberDto = new JoinMemberDto();
        joinMemberDto.setUserId("testId");
        joinMemberDto.setPassword("1234");
        joinMemberDto.setUsername("홍길동");
        joinMemberDto.setEmail("test01@test.com");
        joinMemberDto.setPhoneNumber("01012345678");
        joinMemberDto.setAddress(address);

        // when
        memberRepository.save(joinMemberDto.toEntity());

        // then
        assertThrows(BadRequestException.class, () -> memberService.findOne("emptyId"));
        Assertions.assertThrows(BadRequestException.class, () -> {
            memberService.findOne("emptyId");
        });
    }
}