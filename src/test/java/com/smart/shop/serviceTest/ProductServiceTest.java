package com.smart.shop.serviceTest;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.exeception.ProductNotFoundException;
import com.smart.shop.mapper.ProductMapper;
import com.smart.shop.model.Product;
import com.smart.shop.repository.ProductRepository;
import com.smart.shop.service.product.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    public void createProduct(){
        ProductDto dto = new ProductDto();
        dto.setId(1);
        dto.setPrixUnitaire(122.01);
        dto.setStockDisponible(49);
        dto.setNom("jeans");

        Product product = new Product();
        product.setId(dto.getId());
        product.setPrix_unitaire(dto.getPrixUnitaire());
        product.setStock_disponible(dto.getStockDisponible());

        Mockito.when(productMapper.productDtoToProduct(dto)).thenReturn(product);

        Mockito.when(productRepository.save(product)).thenReturn(product);

        Mockito.when(productMapper.productToProductDto(product)).thenReturn(dto);


        ProductDto result = productService.createProduct(dto);

        assertEquals("jeans" ,result.getNom());

    }
    @Test
    public void updateProductThrowException(){
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()->productService.updateProduct(1,new ProductDto()));
    }
    @Test
    public void updateProductSuccessfully(){
        ProductDto dto = new ProductDto();
        dto.setNom("jean2");
        dto.setPrixUnitaire(22.02);

        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setNom("oldName");
        existingProduct.setPrix_unitaire(10.00);

        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));

        String result = productService.updateProduct(1,dto);

        assertEquals("product modifier avec success",result);

    }

    @Test
    public void deleteProduct(){
        Product prod = new Product();
        prod.setId(1);
        prod.setDeletedAt(null);

        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(prod));

        String result = productService.deleteProduct(1);

        assertEquals("product deleted",result);
        assertNotNull(prod.getDeletedAt());
    }
    @Test
    public void findAllProduct(){
        Product p1 = new Product();
        p1.setId(1);
        p1.setNom("Jean");

        Product p2 = new Product();
        p2.setId(2);
        p2.setNom("Tshirt");

        List<Product> products = List.of(p1,p2);
        Page<Product> productPage = new PageImpl<>(products);

        Pageable pageable = PageRequest.of(0,10);


        Mockito.when(productRepository.findByDeletedAtIsNull(pageable)).thenReturn(productPage);
        Mockito.when(productMapper.productToProductDto(p1)).thenReturn(new ProductDto(1,"Jean",null,null));
        Mockito.when(productMapper.productToProductDto(p2)).thenReturn(new ProductDto(2,"Tshirt",null,null));

        Page<ProductDto> result = productService.findAllProduct(pageable);

        assertEquals(2,result.getContent().size());
        assertEquals("Jean",result.getContent().get(0).getNom());

    }
}
