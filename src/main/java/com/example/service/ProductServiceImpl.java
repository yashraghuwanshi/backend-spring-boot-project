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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final SupplierRepository supplierRepository;

    private final ModelMapper mapper;

    private final SessionFactory sessionFactory;

    private final ObjectMapper objectMapper;

    @LogExecutionTime
    @Override
    public String saveProduct(ProductRequest productRequest) {
        log.info("{} is executing the saveProduct()", Thread.currentThread().getName());
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
    @Async
    @Override
    public CompletableFuture<List<ProductResponse>> getProducts() {
        List<Product> products = productRepository.findAll();
        log.info("{} is executing the getProducts()", Thread.currentThread().getName());
        List<ProductResponse> list = products.stream().map(product -> mapper.map(product, ProductResponse.class)).toList();
        return CompletableFuture.completedFuture(list);
    }

    @Override
    public CompletableFuture<ProductResponse> getProductById(String id) {
        log.info("{} is executing the getProductById()", Thread.currentThread().getName());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: '" + id + "' does not exist in database."));
        ProductResponse productResponse = mapper.map(product, ProductResponse.class);
        return CompletableFuture.completedFuture(productResponse);
    }

    @Override
    public ProductResponse getProductByName(String name){
        Product product = productRepository.findByName(name);
        return mapper.map(product, ProductResponse.class);
    }

    @Override
    public ProductRequest updateProduct(String id, ProductRequest productRequest) {

        System.out.println(productRequest);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: '" + id + "' does not exist in database."));


        List<Supplier> suppliers = productRequest.getSuppliers().stream().map(supplierRequest -> mapper.map(supplierRequest, Supplier.class)).toList();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setSku(productRequest.getSku());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());

        product.getSuppliers().addAll(suppliers);

        productRepository.save(product);

        System.out.println("Updated Product " + product);

        return mapper.map(product, ProductRequest.class);
    }

    @Override
    public ProductRequest patchProduct(String id, JsonPatch jsonPatch) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: '" + id + "' does not exist in database."));

        Product patchedProduct = null;

        try {
            patchedProduct = applyPatchToProduct(jsonPatch, product);
            productRepository.save(patchedProduct);
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return mapper.map(patchedProduct, ProductRequest.class);
    }

    private Product applyPatchToProduct(
            JsonPatch jsonPatch, Product product) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(product, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }

    @Override
    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public void softDelete(String id) {

        productRepository.softDeleteById(id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: '" + id + "' does not exist in database."));

        List<Supplier> suppliers = product.getSuppliers();

        suppliers.forEach(supplier -> {
            supplierRepository.softDeleteById(supplier.getId());
        });

     /*
     using below approach cause the Hibernate to generate update statements for all columns
     suppliers.forEach(supplier -> {
            supplier.setIsDeleted(true);
            supplierRepository.save(supplier); // Save each modified Supplier
        });
        */
    }


    private Supplier mapToSupplier(SupplierRequest supplierRequest) {
        return Supplier.builder()
                .name(supplierRequest.getName())
                .phone(supplierRequest.getPhone())
                .email(supplierRequest.getEmail())
                .address(supplierRequest.getAddress())
                .build();
    }

    public void getSession(){

        Session session = sessionFactory.getCurrentSession();

        Transaction transaction = session.beginTransaction();

        String hql = "SELECT * FROM Product WHERE id = ";

        Query<Product> query = session.createQuery(hql, Product.class);

        query.executeUpdate();


    }
}
