package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.models.Order;
import org.example.models.User;
import org.example.services.OrderServiceInterface;
import org.example.services.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceInterface orderService;
    private final UserServiceInterface userService;

    public record StatusUpdateRequest(String status){}

    @PostMapping
    public ResponseEntity<Order> createOrder(Authentication authentication) {
        User user = userService.findUserByName(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Username not found"));
        return ResponseEntity.ok(orderService.createOrder(user));
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        User user = userService.findUserByName(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Username not found"));
        return ResponseEntity.ok(orderService.findUserOrder(user.getId()));
    }
    @PutMapping("/admin/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, request.status()));
    }
}
