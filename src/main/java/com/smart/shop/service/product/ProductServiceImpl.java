package com.smart.shop.service.product;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.exeception.ProductNotFoundException;
import com.smart.shop.mapper.ProductMapper;
import com.smart.shop.model.Product;
import com.smart.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl  implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository , ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public String createProduct(ProductDto productDto) {
        try{
        Product product = new Product();
        product.setNom(productDto.getNom());
        product.setPrix_unitaire(productDto.getPrixUnitaire());
        product.setStock_disponible(productDto.getStockDisponible());

        Product productSaved = productRepository.save(product);

        return "product created successfully";
        }catch(IllegalArgumentException e){
            return "Exception : " + e.getMessage();
        }
    }
    @Override
    public String updateProduct(int id , ProductDto productDto){
        try{
            Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("produit not trouver"));
            if(productDto.getNom() != null){
                product.setNom(productDto.getNom());
            }
            if(productDto.getPrixUnitaire() != null){
                product.setPrix_unitaire(productDto.getPrixUnitaire());
            }
            if(productDto.getStockDisponible() != null){
                product.setStock_disponible(productDto.getStockDisponible());
            }
            productRepository.save(product);
            return "product modifier avec success";
        }catch(Exception e){
            return "exception : " + e.getMessage();
        }
    }
    @Override
    public String deleteProduct(int id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("produit not found"));
        product.setDeleted_at(LocalDateTime.now());
        productRepository.save(product);
        return "product deleted";
    }

}
