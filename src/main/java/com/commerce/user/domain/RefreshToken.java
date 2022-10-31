package com.commerce.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "REFRESH_TOKEN")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expired_time")
    private LocalDateTime expiredTime;

    @Builder
    public RefreshToken(Long id, Long memberId, String refreshToken, LocalDateTime expiredTime) {
        this.id = id;
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiredTime = expiredTime;
    }

}
