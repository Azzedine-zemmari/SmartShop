package com.smart.shop.repository;

import com.smart.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Integer, User> {
}
