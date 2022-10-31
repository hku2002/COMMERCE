package com.commerce.user.repository;

import com.commerce.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserIdAndActivated(String userId, boolean activated);

    Optional<Member> findByUserId(String userId);
}
