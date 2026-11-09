package com.mlsd.commandservice.repository;

import com.mlsd.commandservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}