package org.example.services.impl;

import org.example.models.Book;
import org.example.models.Cart;
import org.example.models.User;
import org.example.repos.BookRepoInterface;
import org.example.repos.CartRepoInterface;
import org.example.repos.UserRepoInterface;
import org.example.services.CartServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CartService implements CartServiceInterface {
    private final CartRepoInterface cartRepo;


    public CartService(CartRepoInterface cartRepo) {
        this.cartRepo = cartRepo;

    }

    @Override
    public Cart addToCart(User user, Book book, int quantity) {
        Cart cartItem = Cart.builder().user(user).book(book).quantity(quantity).build();
        return cartRepo.save(cartItem);
    }

    @Override
    public void removeFromCart(String id) {
        cartRepo.deleteById(id);
    }

    @Override
    public List<Cart> findCart(String userId) {
        return cartRepo.findByUser_Id(userId);
    }

    @Override
    public void clearCart(String userId) {
    cartRepo.deleteByUserId(userId);
    }
}
