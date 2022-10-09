package com.commerce.user.service;

import com.commerce.global.common.Address;
import com.commerce.user.domain.Member;
import com.commerce.user.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired MemberServiceImpl memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Description("회원가입")
    public void joinTest() {
        // given
        Address address = Address.builder()
                .address("서울시 강남구 테헤란로 427")
                .addressDetail("아이파크몰 test 호")
                .zipCode("12345")
                .build();
        Member member = Member.builder()
                .userId("testId")
                .password("testPassword12")
                .username("홍길동")
                .email("gildong@commerce.com")
                .phoneNumber("01012341234")
                .address(address)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        memberService.join(member);

        // then
        assertEquals(member.getUserId(), memberRepository.findByUserId("testId").getUserId());
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
        Member member = Member.builder()
                .userId("testId")
                .password("testPassword12")
                .username("홍길동")
                .email("gildong@commerce.com")
                .phoneNumber("01012341234")
                .address(address)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        memberService.join(member);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member));
    }

    @Test
    @Description("회원 조회")
    public void findOneTest() {
        // given
        Address address = Address.builder()
                .address("서울시 강남구 테헤란로 427")
                .addressDetail("아이파크몰 test 호")
                .zipCode("12345")
                .build();
        Member member = Member.builder()
                .userId("userId")
                .password("testPassword12")
                .username("홍길동")
                .email("gildong@commerce.com")
                .phoneNumber("01012341234")
                .address(address)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        Long memberId = memberService.join(member);

        // then
        assertEquals(memberId, memberService.findOne(member.getUserId()).getId());
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
        Member member = Member.builder()
                .userId("testId")
                .password("testPassword12")
                .username("홍길동")
                .email("gildong@commerce.com")
                .phoneNumber("01012341234")
                .address(address)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        Long memberId = memberService.join(member);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.findOne("emptyId"));
    }
}