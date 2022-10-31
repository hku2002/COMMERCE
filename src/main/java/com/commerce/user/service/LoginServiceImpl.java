package com.commerce.user.service;

import com.commerce.global.common.exception.BadRequestException;
import com.commerce.global.common.token.JwtTokenManager;
import com.commerce.user.domain.Member;
import com.commerce.user.domain.RefreshToken;
import com.commerce.user.dto.LoginDto;
import com.commerce.user.dto.LoginResponseDto;
import com.commerce.user.repository.MemberRepository;
import com.commerce.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static com.commerce.global.common.util.CryptoUtils.matches;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl {

    private final JwtTokenManager jwtTokenManager;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인 처리
     * @param loginDto
     */
    @Transactional
    public LoginResponseDto login(final LoginDto loginDto) {
        Member member = validationUser(loginDto);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getId(), member.getUserId());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        final String accessToken = jwtTokenManager.createAccessToken(authentication);
        final String refreshToken = jwtTokenManager.createRefreshToken(authentication);
        saveRefreshToken(member, refreshToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getId())
                .username(member.getUsername())
                .build();

    }

    /**
     * 사용자 정보 유효성 검사
     * @param loginDto
     */
    private Member validationUser(LoginDto loginDto) {
        final Member member = memberRepository.findByUserIdAndActivated(loginDto.getUserId(), true);
        if (ObjectUtils.isEmpty(member)) {
            throw new BadRequestException("존재하지 않는 아이디입니다.");
        }

        if (!matches(loginDto.getPassword(), member.getPassword())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    private void saveRefreshToken(Member member, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .memberId(member.getId())
                .refreshToken(refreshToken)
                .build());
    }
}
