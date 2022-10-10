package com.commerce.display.repository;

import com.commerce.display.domain.Display;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.commerce.display.domain.Display.*;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long> {

    public List<Display> findByDisplayCodeAndStatusAndActive(String displayCode, DisplayStatus status, boolean active);

}
