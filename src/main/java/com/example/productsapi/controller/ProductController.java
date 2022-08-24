package com.example.productsapi.controller;

import com.example.productsapi.exception.ProductIdMismatchException;
import com.example.productsapi.exception.ProductNotFoundException;
import com.example.productsapi.model.Product;
import com.example.productsapi.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @Operation(summary = "get all products", tags = {"product"})
    public Iterable findAll() {
        return productRepository.findAll();
    }

    @GetMapping("/title/{productTitle}")
    @Operation(summary = "get product by title", tags = {"product"})
    public List findByTitle(@PathVariable String productTitle) {
        return productRepository.findByTitle(productTitle);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get product by id", tags = {"product"})
    public Product findOne(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @PostMapping
    @Operation(summary = "add new product", tags = {"product"})
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete product", tags = {"product"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update existing product", tags = {"product"})
    @ResponseStatus(HttpStatus.OK)
    public Product updateBook(@RequestBody Product product, @PathVariable Long id) {
        if (product.getId() != id) {
            throw new ProductIdMismatchException();
        }
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productRepository.save(product);
    }
}
