package com.smart.shop.mapper;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    default ProductDto productToProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNom(product.getNom());
        productDto.setPrixUnitaire(product.getPrix_unitaire());
        productDto.setStockDisponible(product.getStock_disponible());
        return productDto;
    };
    default Product productDtoToProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setNom(productDto.getNom());
        product.setPrix_unitaire(productDto.getPrixUnitaire());
        product.setStock_disponible(productDto.getStockDisponible());
        return product;
    };
}
