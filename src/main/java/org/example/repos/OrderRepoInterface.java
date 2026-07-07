package org.example.repos;

import org.example.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepoInterface extends JpaRepository<Order,String> {
    List<Order> findByUserId(String userId);
}
