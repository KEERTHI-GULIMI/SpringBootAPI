package com.siemens.SpringBootAPI.service;

import com.siemens.SpringBootAPI.controller.ProductController;
import com.siemens.SpringBootAPI.entity.Order;
import com.siemens.SpringBootAPI.entity.User;
import com.siemens.SpringBootAPI.models.*;
import com.siemens.SpringBootAPI.entity.Product;
import com.siemens.SpringBootAPI.repository.OrderRepository;
import com.siemens.SpringBootAPI.repository.ProductRepository;
import com.siemens.SpringBootAPI.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    public ProductDetails saveProduct(ProductRequest productRequest) throws myCustomException {

        Product product = new Product();

        if (productRequest.getQuantity() == 0) {
            product.setStatus("Not Available");
        } else if (productRequest.getQuantity() > 0) {
            product.setStatus("Available");
        } else if (productRequest.getQuantity() < 0) {
            throw new myCustomException("Quantity  must be positive number only ");
        }

        product.setName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        Product savedProduct = productRepository.save(product);
        return convertProductToProductDetails(savedProduct);

    }

    public Report getReport() {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Order> allOrders = orderRepository.findAll();
        List<Product> allProducts = productRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        Report report = new Report();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
                public void run() {

                    allRegisteredUsers(finalI, allUsers, report);

                    allUniquecategories(finalI, allProducts, report);

                    totalOrdersPlaced(finalI, allOrders, report);

                    AveragePriceOfOrders(finalI, allProducts, allOrders, report);

                    totalPriceOfOrders(finalI, allProducts, allOrders, report);
                }

            });
        }

        executor.shutdown();

        return report;
    }

    public void allUniquecategories(int finalI, List<Product> allProducts, Report report) {

        Set<String> productCategory = new HashSet<String>();
        List<String> productCategory1 = new ArrayList<>();

        if (finalI == 1) {
            allProducts.forEach(n1 -> {
                productCategory.add(n1.getCategory());
            });

            if (!productCategory1.contains(productCategory)) {
                productCategory1.add(productCategory.toString());
            }
            synchronized (report) {
                report.setUniqueCategories(productCategory1);
            }
        }
    }

    public void totalOrdersPlaced(int finalI, List<Order> allOrders, Report report) {

        if (finalI == 2) {
            synchronized (report) {
                report.setTotalNoOfOrders(allOrders.size());
            }
        }
    }

    public void AveragePriceOfOrders(int finalI, List<Product> allProducts, List<Order> allOrders, Report report) {
        AtomicReference<Double> averageSum = new AtomicReference<>(0.0);
        AtomicReference<Double> totalSum1 = new AtomicReference<>(0.0);

        if (finalI == 3) {
            Map<Integer, Double> productPriceMap = allProducts.stream().collect(Collectors.toMap(Product::getId, Product::getPrice));
            allOrders.forEach(order -> {
                Integer productId = order.getProductId();
                Double productPrice = productPriceMap.get(productId);
                if (productPrice != null) {
                    totalSum1.updateAndGet(v -> v + order.getOrderQuantity() * productPrice);
                }
            });
            int totalQuantity = allOrders.stream().mapToInt(Order::getOrderQuantity).sum();
            if (totalQuantity > 0) {
                averageSum.set(totalSum1.get() / totalQuantity);
            }

            report.setAveragePriceOfOrders(averageSum.get());
        }
    }

    public void allRegisteredUsers(int finalI, List<User> allUsers, Report report) {
        if (finalI == 0) {
            Map<Integer, String> userIdandNameMap = allUsers.stream().collect(Collectors.toMap(User::getUserId, User::getUserName));
            report.setAllRegistereduserMap(userIdandNameMap);
        }
    }


    public void totalPriceOfOrders(int finalI, List<Product> allProducts, List<Order> allOrders, Report report) {
        AtomicReference<Double> totalSum = new AtomicReference<>(0.0);

        if (finalI == 4) {
            Map<Integer, Double> productPriceMap = allProducts.stream().collect(Collectors.toMap(Product::getId, Product::getPrice));
            allOrders.forEach(order -> {
                Integer productId = order.getProductId();
                Double productPrice = productPriceMap.get(productId);
                if (productPrice != null) {
                    totalSum.updateAndGet(v -> v + order.getOrderQuantity() * productPrice);
                }
            });

            report.setTotalPriceOfOrders(totalSum.get());

        }
    }

    public List<ProductDetails> getAllProducts(String category, String sortBy, String sortType, Double minPrice, Double maxPrice) {
        List<Product> result = new ArrayList<>();
        Sort sort = null;
        if (sortType != null && !sortType.isEmpty() && sortBy != null && !sortBy.isEmpty()) {
            if (sortType.equals("asc")) {
                sort = Sort.by(Sort.Direction.ASC, sortBy);
            } else {
                sort = Sort.by(Sort.Direction.DESC, sortBy);
            }
        }

        List<Product> allProducts;

        if (sort != null) {
            allProducts = productRepository.findAll(sort);
            allProducts.forEach(n -> result.add(n));
        } else {
            allProducts = productRepository.findAll();
        }

        List<ProductDetails> productDetailsList = new ArrayList<>();

        if (category != null && !category.isEmpty()) {
            if (!result.isEmpty()) {

                Iterator<Product> iterator = result.iterator();
                while (iterator.hasNext()) {
                    Product item = iterator.next();
                    if (!item.getCategory().equals(category)) {
                        iterator.remove();
                    }
                }
            } else {
                allProducts.forEach(item -> {
                    if (item.getCategory().equals(category)) {
                        result.add(item);
                    }
                });
            }

        }

        if (minPrice != null && !minPrice.isNaN() && maxPrice != null && !maxPrice.isNaN()) {

            if (!result.isEmpty()) {

                Iterator<Product> iterator = result.iterator();
                while (iterator.hasNext()) {
                    Product item = iterator.next();
                    if (!(minPrice <= item.getPrice() && item.getPrice() <= maxPrice)) {
                        iterator.remove();
                    }
                }

            } else {
                allProducts.forEach(item -> {
                    if (minPrice <= item.getPrice() && item.getPrice() <= maxPrice) {
                        result.add(item);
                    }
                });
            }

        }
        if (category == null && sortBy == null && sortType == null && minPrice == null && maxPrice == null) {
            allProducts.forEach(n -> productDetailsList.add(convertProductToProductDetails(n)));

            return productDetailsList;
        }

        result.forEach(n -> productDetailsList.add(convertProductToProductDetails(n)));

        return productDetailsList;
    }
    
    public ProductDetails getProductById(Integer id) throws myException {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return convertProductToProductDetails(optionalProduct.get());
        } else {
            logger.error("error message");
            throw new myException("Provided Product Id " + id + " is not present to fetch");
        }
    }

    public ProductDetails updateProduct(Integer id, UpdateProductRequest updatedProduct) throws myCustomException {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            if (updatedProduct.getQuantity() == 0) {
                product.setStatus("Not Available");
            } else if (updatedProduct.getQuantity() > 0) {
                product.setStatus("Available");
            } else if (updatedProduct.getQuantity() < 0) {
                throw new myCustomException("Quantity  must be positive number only ");
            }
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setCategory(updatedProduct.getCategory());
            Product uProduct = productRepository.save(product);
            return convertProductToProductDetails(uProduct);
        } else {
            throw new myCustomException(" Product is not Present ");
        }
    }


    public void deleteProduct(Integer id) throws myException {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new myException(" provided Id:" + id + "is not present to delete");
        }
    }


    private ProductDetails convertProductToProductDetails(Product product) {

        ProductDetails productDetails = new ProductDetails();
        productDetails.setName(product.getName());
        productDetails.setId(product.getId());
        productDetails.setQuantity(product.getQuantity());
        productDetails.setPrice(product.getPrice());
        productDetails.setStatus(product.getStatus());
        productDetails.setCategory(product.getCategory());

        return productDetails;
    }

}

