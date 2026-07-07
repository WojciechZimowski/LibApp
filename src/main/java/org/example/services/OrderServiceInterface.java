package org.example.services;

import org.example.models.Order;
import org.example.models.User;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    Order createOrder(User user);
    Order updateOrder(String userId, String status);
    List<Order> findUserOrder(String userId);
    Optional<Order> findOrder(String orderId);
}
