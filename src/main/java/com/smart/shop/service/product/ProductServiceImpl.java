package com.smart.shop.service.product;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.exeception.ProductNotFoundException;
import com.smart.shop.mapper.ProductMapper;
import com.smart.shop.model.Product;
import com.smart.shop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl  implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository , ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);

        Product productSaved = productRepository.save(product);

        return productMapper.productToProductDto(productSaved);
    }
    @Override
    public String updateProduct(int id , ProductDto productDto){
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
    }
    @Override
    public String deleteProduct(int id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("produit not found"));
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
        return "product deleted";
    }
    @Override
    public Page<ProductDto> findAllProduct(Pageable pageable){
        return productRepository.findByDeletedAtIsNull(pageable)
                .map(productMapper::productToProductDto);
    }

    public List<ProductDto> test(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> result = products.stream()
                .filter(p->p.getStock_disponible() >= 50)
                .sorted(Comparator.comparing(Product::getPrix_unitaire).reversed())
                .map(m->{
                    m.setPrix_unitaire(m.getPrix_unitaire() * 1.1);
                    return productMapper.productToProductDto(m);
                })
                .toList();
        return result;

    }

}
