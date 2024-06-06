package com.siemens.SpringBootAPI.controller;

import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.models.ProductDetails;
import com.siemens.SpringBootAPI.models.ProductRequest;
import com.siemens.SpringBootAPI.models.UpdateProductRequest;
import com.siemens.SpringBootAPI.models.myException;
import com.siemens.SpringBootAPI.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private myException myexception;

    @PostMapping("/products")
    public ResponseEntity<ProductDetails> saveProduct(@RequestBody ProductRequest productRequest) throws myException {
        ProductDetails newProduct = productService.saveProduct(productRequest);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/products")
    public List<ProductDetails> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id, Product p) throws myException {

        ProductDetails product = productService.getProductById(id, p);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest updateProductRequest) throws myException {

        ProductDetails updatedProduct = productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id)throws myException {

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}




