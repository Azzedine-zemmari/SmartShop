package com.smart.shop.repository;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByDeletedAtIsNull(Pageable pageable);
    @Modifying
    @Query("UPDATE Product p SET  p.stock_disponible = :stock where p.id = :id")
    Integer updateStockDisponible(@Param("stock") Integer stock , @Param("id") Integer id);
}
