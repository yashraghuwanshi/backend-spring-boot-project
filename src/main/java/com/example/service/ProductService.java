package com.example.service;

import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import org.springframework.data.projection.ProjectionFactory;

import java.util.List;

public interface ProductService {

    String saveProduct(ProductRequest productRequest);

    List<ProductResponse> getProducts();

    ProductResponse getProductById(String id);

    ProductRequest updateProduct(String id, ProductRequest productRequest);

    void deleteProductById(String id);
}
