package com.spring.crud.springcrud.repository;

import com.spring.crud.springcrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> { // Product is the entity and Integer is the type of Primary key
    Product findByName(String name);
}
