package org.example.services.impl;

import org.example.models.Cart;
import org.example.models.Order;
import org.example.models.User;
import org.example.repos.CartRepoInterface;
import org.example.repos.UserRepoInterface;
import org.example.services.CartServiceInterface;
import org.example.services.OrderServiceInterface;
import org.example.services.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface {
    private final UserRepoInterface userRepo;
    private final OrderServiceInterface orderService;
   private final CartServiceInterface cartService;


    public UserService(UserRepoInterface userRepo, OrderServiceInterface orderService,  CartServiceInterface cartService) {
        this.userRepo = userRepo;
        this.orderService = orderService;
        this.cartService = cartService;


    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return userRepo.findByUsername(name);
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUser(String id) {

        List<Order> userOrders = orderService.findUserOrder(id);


        boolean hasUnfinishedOrders = userOrders.stream()
                .anyMatch(order -> !order.getStatus().equals("FINISHED"));

        if (hasUnfinishedOrders) {
            throw new IllegalStateException("Nie można usunąć użytkownika! Posiada on aktywne zamówienia.");
        }
        List<Cart> cartItems = cartService.findCart(id);

        for (Cart cartItem : cartItems) {
            cartService.removeFromCart(cartItem.getId());
        }


        userRepo.deleteById(id);
    }
}
