package org.example.services.impl;

import org.example.models.Book;
import org.example.models.Cart;
import org.example.models.Order;
import org.example.models.User;
import org.example.repos.BookRepoInterface;
import org.example.repos.CartRepoInterface;
import org.example.repos.OrderRepoInterface;
import org.example.repos.UserRepoInterface;
import org.example.services.OrderServiceInterface;
import org.example.services.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService implements OrderServiceInterface {
    private final OrderRepoInterface orderRepo;
    private final CartRepoInterface cartRepo;
    private final BookRepoInterface bookRepo;


    public OrderService(OrderRepoInterface orderRepo, CartRepoInterface cartRepo, BookRepoInterface bookRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;

        this.bookRepo = bookRepo;
    }

    @Override
    @Transactional
    public Order createOrder(User user) {
        List<Cart> cartItems = cartRepo.findByUser_Id(user.getId());
        if(cartItems.isEmpty()){
            throw new RuntimeException("Koszyk jest pusty");
        }
        for(Cart cartItem : cartItems){
            Book book = cartItem.getBook();
            int remainingQuantity=book.getQuantity() - cartItem.getQuantity()-cartItem.getQuantity();
            if(remainingQuantity<0){
                throw new RuntimeException("Książka jest niedostępna");

            }
            book.setQuantity(remainingQuantity);
            bookRepo.save(book);
        }
        double totalPrice= cartItems.stream().mapToDouble(item->item.getBook().getPrice()* item.getQuantity()).sum();
        String itemsSum = cartItems.stream().map(item->item.getBook().getTitle()+" x"+item.getQuantity()).collect(Collectors.joining("; "));
        Order order = Order.builder().user(user).totalPrice(totalPrice).status("PENDING").itemJson(itemsSum).build();
        Order savedOrder = orderRepo.save(order);
        cartRepo.deleteByUserId(user.getId());
        return savedOrder;
    }

    @Override
    @Transactional
    public void updateOrder(String orderId, String status) {
        if (!"PAID".equals(status) && !"FINISHED".equals(status)) {
            throw new IllegalArgumentException("Niedozwolony status!");
        }
        Order order = orderRepo.findById(orderId).orElseThrow(()->new IllegalArgumentException("Nie znaleziono zamówienia"));
        order.setStatus(status);

        orderRepo.save(order);
    }

    @Override
    public List<Order> findUserOrder(String userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public Optional<Order> findOrder(String orderId) {
        return orderRepo.findById(orderId);
    }


}
