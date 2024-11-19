package com.spring.crud.springcrud.service;

import com.spring.crud.springcrud.entity.Product;
import com.spring.crud.springcrud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product getByName(String name) {
        return productRepository.findByName(name);
    }

    public String deleteProduct(int id){
        productRepository.deleteById(id);
        return "Product deleted";
    }

    public Product updateProduct(Product product) {
        Product oldProduct = productRepository.findById(product.getId()).orElse(null);
        assert oldProduct != null;
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());
        return productRepository.save(oldProduct);
    }
}
