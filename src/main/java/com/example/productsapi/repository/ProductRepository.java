package com.example.productsapi.repository;

import com.example.productsapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);
    List<Product> findByTitle(String title);
}