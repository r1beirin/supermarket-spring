package com.springstudie.supermarket.model.infra.db.repository;

import com.springstudie.supermarket.model.usecases.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
