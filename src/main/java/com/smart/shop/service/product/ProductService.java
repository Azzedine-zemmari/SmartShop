package com.smart.shop.service.product;

import com.smart.shop.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    String createProduct(ProductDto productDto);
    String updateProduct(int id , ProductDto productDto );
    String deleteProduct(int id);
    Page<ProductDto> findAllProduct(Pageable pageable);
}
