package com.springstudie.supermarket.repository;

import com.springstudie.supermarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
}
