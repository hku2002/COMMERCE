package com.commerce.display.service;

import com.commerce.display.domain.Display;
import com.commerce.display.domain.Display.DisplayStatus;
import com.commerce.display.repository.DisplayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisplayServiceImpl {

    private final DisplayRepository displayRepository;

    /**
     * 전시 목록 조회
     * param
     */
    public List<Display> findByDisplayCode(String displayCode) {
        return displayRepository.findByDisplayCodeAndStatusAndActive(displayCode , DisplayStatus.DISPLAY, true);
    }
}
