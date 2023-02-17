/*
 *
 * @Author: github.com/r1beirin
 * @Year: 2023
 *
 */

/*
 *
 * @Author: github.com/r1beirin
 * @Year: 2023
 *
 */

package com.springstudie.supermarket.repository;

import com.springstudie.supermarket.model.usecases.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
