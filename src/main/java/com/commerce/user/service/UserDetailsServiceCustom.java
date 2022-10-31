package com.commerce.user.service;

import com.commerce.global.common.exception.BadRequestException;
import com.commerce.user.domain.Member;
import com.commerce.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("userDetailsServiceCustom")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceCustom implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .map(member -> createUser(member))
                .orElseThrow(() -> new BadRequestException("회원 정보를 찾을 수 없습니다."));
    }

    private User createUser(Member member) {
        if (!member.isActivated()) {
            throw new BadRequestException("탈퇴한 사용자 입니다.");
        }

        GrantedAuthority grantedAuthority = (GrantedAuthority) () -> "ROLE_USER";
        return new User(member.getUsername(), member.getPassword(), List.of(grantedAuthority));
    }
}
