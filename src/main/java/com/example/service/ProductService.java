package com.example.service;

import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.projection.ProjectionFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {

    String saveProduct(ProductRequest productRequest);

    CompletableFuture<List<ProductResponse>> getProducts();

    CompletableFuture<ProductResponse> getProductById(String id);

    ProductResponse getProductByName(String name);

    ProductRequest updateProduct(String id, ProductRequest productRequest);

    ProductRequest patchProduct(String id, JsonPatch jsonPatch);

    void deleteProductById(String id);

    void softDelete(String id);
}
