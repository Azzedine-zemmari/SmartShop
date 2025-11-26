package com.smart.shop.controller;

import com.smart.shop.dto.ProductDto;
import com.smart.shop.model.Product;
import com.smart.shop.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertProduct(@RequestBody ProductDto productDto){
        String product = productService.createProduct(productDto);
        return ResponseEntity.ok(product);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") int id , @RequestBody ProductDto productDto){
        String product = productService.updateProduct(id,productDto);
        return ResponseEntity.ok(product);
    }
}
