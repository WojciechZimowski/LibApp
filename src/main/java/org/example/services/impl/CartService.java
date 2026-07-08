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
import java.util.Optional;

@Service
@Transactional
public class CartService implements CartServiceInterface {
    private final CartRepoInterface cartRepo;
    private final BookRepoInterface bookRepo;


    public CartService(CartRepoInterface cartRepo, BookRepoInterface bookRepo) {
        this.cartRepo = cartRepo;

        this.bookRepo = bookRepo;
    }

    @Override
    public Cart addToCart(User user, Book book, int quantity) {
        List<Cart> userCart = cartRepo.findByUser_Id(user.getId());

        Optional<Cart> existingItem = userCart.stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            Cart cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartRepo.save(cartItem); // Hibernate zrobi SQL UPDATE
        }

        Cart cartItem = Cart.builder()
                .id(java.util.UUID.randomUUID().toString())
                .user(user)
                .book(book)
                .quantity(quantity)
                .build();

        return cartRepo.save(cartItem);
    }

    @Override
    @Transactional
    public void removeFromCart(String itemId) {
        Cart cartItem = cartRepo.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono takiego elementu w koszyku."));

         Book book = cartItem.getBook();
        book.setQuantity(book.getQuantity() + cartItem.getQuantity());

        bookRepo.save(book);

        cartRepo.deleteById(itemId);
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
