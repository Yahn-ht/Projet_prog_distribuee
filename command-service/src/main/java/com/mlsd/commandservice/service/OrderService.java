package com.mlsd.commandservice.service;


import com.mlsd.commandservice.dto.OrderItemRequest;
import com.mlsd.commandservice.dto.OrderRequest;
import com.mlsd.commandservice.entity.Order;
import com.mlsd.commandservice.entity.OrderItem;
import com.mlsd.commandservice.entity.Product;
import com.mlsd.commandservice.repository.OrderRepository;
import com.mlsd.commandservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerEmail(request.getCustomerEmail());
        order.setOrderDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable : " + itemReq.getProductId()));

            // Vérification du stock disponible
            if (product.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException("Stock insuffisant pour : " + product.getName());
            }

            // Déduction du stock
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);

            // Création de la ligne de commande
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());

            order.getItems().add(item);

            // Calcul du total
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(lineTotal);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée : " + id));
    }
}