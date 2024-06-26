package com.siemens.SpringBootAPI.controller;


import com.siemens.SpringBootAPI.models.*;
import com.siemens.SpringBootAPI.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDetails> saveProduct(@RequestBody ProductRequest productRequest) throws myCustomException {
        ProductDetails newProduct = productService.saveProduct(productRequest);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/products")
    public List<ProductDetails> getAllProducts(@RequestParam(required = false) String category, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortType, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
        return productService.getAllProducts(category, sortBy, sortType, minPrice, maxPrice);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable Integer id) throws myException {

        ProductDetails product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest updateProductRequest) throws myCustomException {

        ProductDetails updatedProduct = productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws myException {

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/report")
    public Report productReport() {
        return productService.getReport();

    }

}




