package com.siemens.SpringBootAPI.service;

import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.models.ProductDetails;

import com.siemens.SpringBootAPI.models.ProductRequest;
import com.siemens.SpringBootAPI.models.UpdateProductRequest;
import com.siemens.SpringBootAPI.models.myException;
import com.siemens.SpringBootAPI.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        p1.setId(1l);
        p1.setName("mangoes");
        p1.setQuantity(20);
        p1.setPrice(40.2);

        p2.setId(2l);
        p2.setName("sweet laddu");
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
        inputProductRequest.setName("Chocolate");
        inputProductRequest.setQuantity(10);
        inputProductRequest.setPrice(200.0);

        Product product = new Product();
        product.setId(12345l);
        product.setName("cake");
        product.setPrice(20.5);
        product.setQuantity(30);
        product.setStatus("available");

        when(productRepository.save(any())).thenReturn(product);

        //Act
        ProductDetails productDetails = productService.saveProduct(inputProductRequest);


        //Assert
        Assertions.assertThat(productDetails).isNotNull();
        assertEquals("cake", productDetails.getName());
        assertEquals(30, productDetails.getQuantity());
        assertEquals("available", productDetails.getStatus());
        assertEquals(20.5, productDetails.getPrice());

    }

    @Test
    public void deleteProductTest() { // doubt

        long productId = 1l;
        Product product = new Product();
//       willDoNothing().given(productRepository).deleteById(productId);
//       productService.deleteProduct(productId);
//       verify(productRepository, times(1)).deleteById(productId);
//       // assertThrows(IllegalStateException())
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));


    }


    //
    @Test
    public void updateProductTest()throws myException {

        long pId=1L;
        UpdateProductRequest updatedProduct=new UpdateProductRequest();
        updatedProduct.setId(2L);
        updatedProduct.setName("cake");
        updatedProduct.setQuantity(1);
        updatedProduct.setPrice(500.0);

        Product product = new Product();
        product.setName("cake");
        product.setQuantity(1);
        product.setPrice(500.0);
        product.setStatus("Available");

        when(productRepository.findById(pId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductDetails productDetails=productService.updateProduct(pId,updatedProduct);

         assertEquals("cake",productDetails.getName() );

    }

    @Test
    public void getProductById()throws myException{

        long pId=2;
        Product product = new Product();
        product.setId(12345L);
        product.setName("cake");
        product.setPrice(20.5);
        product.setQuantity(30);
        product.setStatus("available");

        when(productRepository.findById(pId)).thenReturn(Optional.of(product));

        ProductDetails productDetails=productService.getProductById(pId,product);

        Assertions.assertThat(productDetails).isNotNull();

    }

}