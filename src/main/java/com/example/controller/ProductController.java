package com.example.controller;

import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import com.example.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-product/{id}")
    public ProductResponse getProduct(@PathVariable("id") String productId, @RequestHeader("User-Agent") String userAgent) {
        logger.info("User Agent: {}", userAgent);
        return productService.getProductById(productId);
    }

    @PostMapping("/update-product")
    public ProductRequest updateProduct(@RequestParam("id") String productId, ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete-product/{id}")
    public void updateProduct(@PathVariable("id") String productId) {
        productService.deleteProductById(productId);
    }

}
