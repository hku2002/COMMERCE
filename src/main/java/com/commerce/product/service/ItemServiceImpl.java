package com.commerce.product.service;

import com.commerce.product.domain.Item;
import com.commerce.product.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl {

    private final ItemRepository itemRepository;

    /**
     * 상품 옵션 목록
     * param displayId, mainProductId
     */
    public List<Item> optionList(Long displayId) {
        return null;
    }

}
