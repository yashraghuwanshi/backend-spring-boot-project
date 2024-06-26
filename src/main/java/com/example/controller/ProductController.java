package com.example.controller;

import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import com.example.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/v1")
@RestController
@CrossOrigin(origins = {"http://localhost:4200/"})
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/save-product")
    public String saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/get-products")
    public CompletableFuture<List<ProductResponse>> getProducts() {
        return productService.getProducts();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-product/{id}")
    public CompletableFuture<ProductResponse> getProduct(@PathVariable("id") String productId, @RequestHeader("User-Agent") String userAgent) {
        logger.info("User Agent: {}", userAgent);
        return productService.getProductById(productId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update-product/{id}")
    public ProductRequest updateProduct(@PathVariable("id") String productId, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete-product/{id}")
    public void updateProduct(@PathVariable("id") String productId) {
        productService.deleteProductById(productId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/soft-delete-product/{id}")
    public void softDelete(@PathVariable("id") String productId) {
        productService.softDelete(productId);
    }

}
