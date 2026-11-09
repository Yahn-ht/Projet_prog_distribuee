package com.mlsd.commandservice.repository;

import com.mlsd.commandservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}