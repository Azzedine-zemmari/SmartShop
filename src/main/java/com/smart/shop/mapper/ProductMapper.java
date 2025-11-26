package com.smart.shop.mapper;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToProductDto(Product product);
    Product productDtoToProduct(ProductDto productDto);
}
