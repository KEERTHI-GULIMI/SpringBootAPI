package com.siemens.SpringBootAPI.service;

import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.models.ProductDetails;

import com.siemens.SpringBootAPI.models.ProductRequest;
import com.siemens.SpringBootAPI.models.UpdateProductRequest;
import com.siemens.SpringBootAPI.models.myException;
import com.siemens.SpringBootAPI.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private myException myexception;

    @Test
    public void getAllProductsTest() {

        List<Product> list = new ArrayList<Product>();
        Product p1 = new Product();
        Product p2 = new Product();

        p1.setId(1L);
        p1.setName("mangoes");
        p1.setQuantity(20);
        p1.setPrice(40.2);

        p2.setId(2L);
        p2.setName("sweetLaddu");
        p2.setQuantity(200);
        p2.setPrice(20.0);

        list.add(p1);
        list.add(p2);

        when(productRepository.findAll()).thenReturn(list);

        List<ProductDetails> productList = productService.getAllProducts();

        assertEquals(2, productList.size());

    }

    @Test
    public void saveProductTest() throws myException {

        //Arrange

        ProductRequest inputProductRequest = new ProductRequest();
        //ProductRequest inputProductRequest =  Mockito.mock(ProductRequest.class);;
        inputProductRequest.setName("Chocolate");
        inputProductRequest.setQuantity(30);
        inputProductRequest.setPrice(200.0);

        Product product = new Product();
        product.setId(12345L);
        product.setName(inputProductRequest.getName());
        product.setPrice(inputProductRequest.getPrice());
        product.setQuantity(inputProductRequest.getQuantity());
        product.setStatus("available");


        when(productRepository.save(any())).thenReturn(product);
        // when(inputProductRequest.getQuantity()).thenReturn(30);


        //Act
        ProductDetails productDetails = productService.saveProduct(inputProductRequest);


        //Assert
        Assertions.assertThat(productDetails).isNotNull();
        assertEquals("Chocolate", productDetails.getName());
        assertEquals(30, productDetails.getQuantity());
        assertEquals("available", productDetails.getStatus());
        assertEquals(200.0, productDetails.getPrice());

    }

    @Test
    public void saveProductTest1() throws myException {

        //Arrange
        ProductRequest inputProductRequest = new ProductRequest();
        inputProductRequest.setName("Chocolate");
        inputProductRequest.setQuantity(0);
        inputProductRequest.setPrice(200.0);

        Product product1 = new Product();
        product1.setId(12L);
        product1.setName("choco");
        product1.setPrice(20.5);
        product1.setQuantity(0);
        product1.setStatus(" not available");


        when(productRepository.save(any())).thenReturn(product1);


        //Act
        ProductDetails productDetails = productService.saveProduct(inputProductRequest);

        //Assert
        Assertions.assertThat(productDetails).isNotNull();

    }

  //  @Test
//    public void saveProductTest2() throws myException {
//
//        //Arrange
//        ProductRequest inputProductRequest = new ProductRequest();
//        inputProductRequest.setName("Chocolate");
//        inputProductRequest.setQuantity(-1);
//        inputProductRequest.setPrice(200.0);
//
//        Product product1 = new Product();
//        product1.setId(12L);
//        product1.setName("choco");
//        product1.setPrice(20.5);
//        product1.setQuantity(-1);
//        product1.setStatus(" not available");
//
//
//        when(productRepository.save(any())).thenReturn(product1);
//
//
//        //Act
//        ProductDetails productDetails = productService.saveProduct(inputProductRequest);
//
//
//        //Assert
//        Assertions.assertThat(productDetails).isNotNull();
//        assertThrows(myException.class, () -> productService.saveProduct(inputProductRequest));
//
//
//    }


    @Test
    public void deleteProductTest() throws myException { // doubt

        long productId = 1L;
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);

    }

    @Test
    public void deleteProductTest1() throws myException { // doubt

        long productId = 12L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(myException.class, () -> productService.deleteProduct(productId));

    }


    @Test
    public void updateProductTest() throws myException {

        long pId = 1L;
        UpdateProductRequest updatedProduct = new UpdateProductRequest();
        updatedProduct.setId(2L);
        updatedProduct.setName("cake");
        updatedProduct.setQuantity(1);
        updatedProduct.setPrice(500.0);

        Product product = new Product();
        product.setName(updatedProduct.getName());
        product.setQuantity(updatedProduct.getQuantity());
        product.setPrice(updatedProduct.getPrice());
        product.setStatus("Available");

        when(productRepository.findById(pId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductDetails productDetails = productService.updateProduct(pId, updatedProduct);

        assertEquals("cake", productDetails.getName());
        assertEquals(1, productDetails.getQuantity());
        assertEquals(500.0, productDetails.getPrice());
        assertEquals("Available", productDetails.getStatus());

    }

    @Test
    public void updateProductTest1() throws myException {

        long pId = 1L;
        UpdateProductRequest updatedProduct = new UpdateProductRequest();
        updatedProduct.setId(2L);
        updatedProduct.setName("cake");
        updatedProduct.setQuantity(1);
        updatedProduct.setPrice(500.0);

        Product product = new Product();
        product.setName(updatedProduct.getName());
        product.setQuantity(updatedProduct.getQuantity());
        product.setPrice(updatedProduct.getPrice());
        product.setStatus("Available");

        when(productRepository.findById(pId)).thenReturn(Optional.empty());
        when(productRepository.save(product)).thenReturn(product);

        assertThrows(myException.class, () -> productService.updateProduct(pId, updatedProduct));

    }


    @Test
    public void getProductById() throws myException {

        long pId = 2;
        Product product = new Product();
        product.setId(12345L);
        product.setName("cake");
        product.setPrice(20.5);
        product.setQuantity(30);
        product.setStatus("available");

        when(productRepository.findById(pId)).thenReturn(Optional.of(product));

        ProductDetails productDetails = productService.getProductById(pId, product);

        Assertions.assertThat(productDetails).isNotNull();

    }

    @Test
    public void getProductById1() throws myException {

        long pId = 2;
        Product product = new Product();
        product.setId(123L);
        product.setName("cake");
        product.setPrice(20.5);
        product.setQuantity(30);
        product.setStatus("available");

        when(productRepository.findById(pId)).thenReturn(Optional.empty());

        assertThrows(myException.class, () -> productService.getProductById(pId, product));

    }

}