package com.smart.shop.repository;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByDeletedAtIsNull(Pageable pageable);
}
