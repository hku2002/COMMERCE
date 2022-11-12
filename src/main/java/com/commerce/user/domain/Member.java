package com.commerce.user.domain;

import com.commerce.global.common.Address;
import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 11)
    private String phoneNumber;

    @Embedded
    private Address address;

    @Builder
    public Member (long id, String userId, String password, String username, String email, String phoneNumber, Address address, boolean activated, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.activated = activated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 회원이 존재하는지 체크
     * @param member 회원 객체
     */
    public static void checkMemberExist(Member member) {
        if (ObjectUtils.isEmpty(member)) {
            throw new BadRequestException("회원 정보를 찾을 수 없습니다.");
        }
    }

    /**
     * 회원이 중복되는지 확인
     * @param member 회원 객체
     */
    public static void checkMemberDuplicate(Member member) {
        if (!ObjectUtils.isEmpty(member)) {
            throw new BadRequestException("이미 가입된 회원이 존재합니다.");
        }
    }

}
