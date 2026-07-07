package org.example.services;

import org.example.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {
    Book addBook(Book book);
    void deleteBookById(String id);
    List<Book> findAllBooks();
    Optional<Book> findBookById(String id);
}
