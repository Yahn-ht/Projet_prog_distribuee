package com.mlsd.commandservice.config;

import com.mlsd.commandservice.entity.Product;
import com.mlsd.commandservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Clavier Mécanique", "Clavier RGB switches rouges", new BigDecimal("89.99"), 15));
            repository.save(new Product("Souris Gamer", "Capteur optique 16000 DPI", new BigDecimal("49.99"), 25));
            repository.save(new Product("Écran 27 pouces", "144Hz 1ms IPS", new BigDecimal("249.99"), 5));
        };
    }
}