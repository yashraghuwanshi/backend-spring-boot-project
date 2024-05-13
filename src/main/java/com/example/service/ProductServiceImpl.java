package com.example.service;

import com.example.annotation.LogExecutionTime;
import com.example.dto.ProductRequest;
import com.example.dto.ProductResponse;
import com.example.dto.SupplierRequest;
import com.example.exceptions.ProductNotFoundException;
import com.example.model.Product;
import com.example.model.Supplier;
import com.example.repository.ProductRepository;
import com.example.repository.SupplierRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, SupplierRepository supplierRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @LogExecutionTime
    @Override
    public String saveProduct(ProductRequest productRequest) {

        List<SupplierRequest> supplierRequests = productRequest.getSuppliers();
        List<Supplier> suppliers = supplierRequests.stream().map(this::mapToSupplier).toList();

        Product product = Product.builder()
                .name(productRequest.getName())
                .sku(productRequest.getSku())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .suppliers(suppliers)
                .build();

        productRepository.save(product);

        return "Product assigned with id: " + product.getId() + " and saved successfully";
    }

    @LogExecutionTime
    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapper.map(product, ProductResponse.class)).toList();
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: '" + id + "' does not exist in database."));
        return mapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductRequest updateProduct(String id, ProductRequest productRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: '" + id + "' does not exist in database."));


        List<Supplier> suppliers = productRequest.getSuppliers().stream().map(supplierRequest -> mapper.map(supplierRequest, Supplier.class)).toList();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setSku(productRequest.getSku());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setSuppliers(suppliers);

        productRepository.save(product);

        return mapper.map(product, ProductRequest.class);
    }

    @Override
    public void deleteProductById(String id) {
              productRepository.deleteById(id);
    }

    private Supplier mapToSupplier(SupplierRequest supplierRequest) {
        return Supplier.builder()
                .name(supplierRequest.getName())
                .phone(supplierRequest.getPhone())
                .email(supplierRequest.getEmail())
                .address(supplierRequest.getAddress())
                .build();
    }
}
