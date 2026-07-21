package com.spring.crud.springcrud.controller;

import com.spring.crud.springcrud.entity.Product;
import com.spring.crud.springcrud.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.XMLFormatter;

@RestController
public class ProductController {

    // Logger setup — one line per class
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @PostMapping("/product/add")
    public Product addProduct(@RequestBody Product product) {
        log.info("POST /product/add - adding product: {}", product.getName());
        Product saved = productService.saveProduct(product);
        log.info("Product saved successfully with id: {}", saved.getId());
        return saved;
    }

    @PostMapping("/products/add")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        log.info("POST /products/add - adding {} products", products.size());
        List<Product> saved = productService.saveProducts(products);
        log.info("Bulk save completed - {} products saved", saved.size());
        return saved;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        log.info("GET /products - fetching all products");
        List<Product> products = productService.getProducts();
        log.info("Returning {} products", products.size());
        return products;
    }

    @GetMapping("/product/id/{id}")
    public Product getProduct(@PathVariable int id) {
        log.info("GET /product/id/{} - fetching product by id", id);
        Product product = productService.getProduct(id);
        if (product == null) {
            log.warn("Product not found with id: {}", id);
        } else {
            log.info("Product found: {}", product.getName());
        }
        return product;
    }

    @GetMapping("/product/name/{name}")
    public Product getProductByName(@PathVariable String name) {
        log.info("GET /product/name/{} - fetching product by name", name);
        Product product = productService.getByName(name);
        if (product == null) {
            log.warn("Product not found with name: {}", name);
        } else {
            log.info("Product found with id: {}", product.getId());
        }
        return product;
    }

    @PutMapping("/product/update")
    public Product updateProduct(@RequestBody Product product) {
        log.info("PUT /product/update - updating product id: {}", product.getId());
        Product updated = productService.updateProduct(product);
        log.info("Product updated successfully: {}", updated.getId());
        return updated;
    }

    @DeleteMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        log.info("DELETE /product/delete/{} - deleting product", id);
        String result = productService.deleteProduct(id);
        log.info("Delete result for id {}: {}", id, result);
        return result;
    }
}