package com.siemens.SpringBootAPI.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.models.ProductDetails;
import com.siemens.SpringBootAPI.models.ProductRequest;
import com.siemens.SpringBootAPI.models.UpdateProductRequest;
import com.siemens.SpringBootAPI.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {ProductController.class})
public class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void saveProductTest() throws Exception {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("carrot");
        productRequest.setPrice(22.2);
        productRequest.setQuantity(99);

        ProductDetails productDetails = new ProductDetails();
        productDetails.setId(2);
        productDetails.setName("carrot");
        productDetails.setPrice(22.2);
        productDetails.setQuantity(99);
        productDetails.setStatus("Available");

        when(productService.saveProduct(productRequest)).thenReturn(productDetails);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .content(new ObjectMapper().writeValueAsString(productRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.productService, Mockito.times(1)).saveProduct(productRequest);
    }

    @Test
    public void getAllProductTest() throws Exception {
        List<ProductDetails> list = new ArrayList<ProductDetails>();
        ProductDetails p1 = new ProductDetails();
        ProductDetails p2 = new ProductDetails();

        p1.setId(1);
        p1.setName("mangoes");
        p1.setQuantity(20);
        p1.setPrice(40.2);
        p1.setStatus("available");

        p2.setId(2);
        p2.setName("sweetLaddu");
        p2.setQuantity(200);
        p2.setPrice(20.0);
        p2.setStatus("available");

        list.add(p1);
        list.add(p2);

        when(productService.getAllProducts()).thenReturn(list);


        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                .content("this is my mock body")
                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                        .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getProductByIdTest() throws Exception {
        Integer pId = 2;

        Product product = new Product();
        product.setId(2);
        product.setName("cake");
        product.setPrice(20.5);
        product.setQuantity(30);
        product.setStatus("available");

        ProductDetails productDetails = new ProductDetails();
        productDetails.setId(2);
        productDetails.setName("carrot");
        productDetails.setPrice(22.2);
        productDetails.setQuantity(99);
        productDetails.setStatus("Available");

        when(productService.getProductById(pId)).thenReturn(productDetails);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/2")
                        .content(new ObjectMapper().writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    public void updateProductTest() throws Exception {

        Integer pId = 2;

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setId(125L);
        updateProductRequest.setName("cake");
        updateProductRequest.setPrice(20.5);
        updateProductRequest.setQuantity(30);

        ProductDetails productDetails = new ProductDetails();
        productDetails.setId(2);
        productDetails.setName("carrot");
        productDetails.setPrice(22.2);
        productDetails.setQuantity(99);
        productDetails.setStatus("Available");

        when(productService.updateProduct(pId, updateProductRequest)).thenReturn(productDetails);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/2")
                        .content(new ObjectMapper().writeValueAsString(updateProductRequest))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void  deleteProductTest() throws Exception {
        Integer pId=2;
        doNothing().when(productService).deleteProduct(pId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/2")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
