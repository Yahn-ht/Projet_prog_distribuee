package com.mlsd.commandservice.service;

import com.mlsd.commandservice.entity.Product;
import com.mlsd.commandservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}