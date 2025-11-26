package com.smart.shop.service.product;

import com.smart.shop.dto.ProductDto;

public interface ProductService {
    String createProduct(ProductDto productDto);
    String updateProduct(int id , ProductDto productDto );
    String deleteProduct(int id);
}
