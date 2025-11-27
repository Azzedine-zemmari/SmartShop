package com.smart.shop.repository;

import com.smart.shop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface OrderItemsRepository extends JpaRepository<OrderItem , Long> {
    List<OrderItem> findByCommandeId(Long commandeId);
}
