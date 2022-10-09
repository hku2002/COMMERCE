package com.shop.display.repository;

import com.shop.display.domain.Display;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long> {
}
