package com.siemens.SpringBootAPI.service;

import com.siemens.SpringBootAPI.controller.ProductController;
import com.siemens.SpringBootAPI.models.ProductDetails;
import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.models.UpdateProductRequest;
import com.siemens.SpringBootAPI.models.myException;
import com.siemens.SpringBootAPI.repository.ProductRepository;
import com.siemens.SpringBootAPI.models.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private myException myexception;

    public ProductDetails saveProduct(ProductRequest productRequest) throws myException {

        Product product = new Product();

        if (productRequest.getQuantity() == 0) {
            product.setStatus("Not Available");
        } else if (productRequest.getQuantity() > 0) {
            product.setStatus("Available");
        } else if (productRequest.getQuantity() < 0) {
            throw new myException("Quantity  must be positive number only ");
        }

        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        Product savedProduct = productRepository.save(product);
        return convertProductToProductDetails(savedProduct);

    }

    public List<ProductDetails> getAllProducts() {

        List<Product> allProducts = productRepository.findAll();
        List<ProductDetails> productDetailsList = new ArrayList<>();

        for (Product allProduct : allProducts) {
            productDetailsList.add(convertProductToProductDetails(allProduct));
        }
        return productDetailsList;
    }

    public ProductDetails getProductById(Long id, Product product) throws myException {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return convertProductToProductDetails(optionalProduct.get());
        } else {
            logger.error("error message");
            throw new myException("invalid product id " + id);
        }
    }

    public ProductDetails updateProduct(Long id, UpdateProductRequest updatedProduct) throws myException {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            if (updatedProduct.getQuantity() == 0) {
                product.setStatus("Not Available");
            } else if (updatedProduct.getQuantity() > 0) {
                product.setStatus("Available");
            } else if (updatedProduct.getQuantity() < 0) {
                throw new myException("Quantity  must be positive number only ");
            }
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            Product uProduct = productRepository.save(product);
            return convertProductToProductDetails(uProduct);
        } else {
            throw new myException(" Quantity can't be negative ");
        }
    }

    public void deleteProduct(Long id) throws myException {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new myException(" provided" +id+ "is not present anyway");
        }
    }


    private ProductDetails convertProductToProductDetails(Product product) {

        ProductDetails productDetails = new ProductDetails();
        productDetails.setName(product.getName());
        productDetails.setId(product.getId());
        productDetails.setQuantity(product.getQuantity());
        productDetails.setPrice(product.getPrice());
        productDetails.setStatus(product.getStatus());

        return productDetails;
    }
}


