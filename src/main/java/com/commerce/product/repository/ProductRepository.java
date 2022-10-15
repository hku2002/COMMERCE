package com.commerce.product.repository;

import com.commerce.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.commerce.product.domain.Product.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByStatusIn(List<DisplayStatus> displayStatus, Pageable pageable);
}
