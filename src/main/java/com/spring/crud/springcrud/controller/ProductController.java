package com.spring.crud.springcrud.controller;

import com.spring.crud.springcrud.entity.Product;
import com.spring.crud.springcrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/products/add")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return productService.saveProducts(products);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("product/id/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @GetMapping("/product/name/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getByName(name);
    }

    @PutMapping("/product/update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }
}
