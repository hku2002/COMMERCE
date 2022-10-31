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
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "refresh_token", nullable = false, length = 228)
    private String refreshToken;

    @Column(name = "expired_time", updatable = false, nullable = false)
    private LocalDateTime expiredTime;

    @Builder
    public RefreshToken(Long id, String userId, String refreshToken, LocalDateTime expiredTime) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiredTime = expiredTime;
    }

}
