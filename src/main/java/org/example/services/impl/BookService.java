package org.example.services.impl;

import org.example.models.Book;
import org.example.repos.BookRepoInterface;
import org.example.services.BookServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookService implements BookServiceInterface {
    private final BookRepoInterface bookRepo;

    public BookService(BookRepoInterface bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public void deleteBookById(String id) {
        bookRepo.deleteById(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return  bookRepo.findAll();

    }

    @Override
    public Optional<Book> findBookById(String id) {
        return bookRepo.findById(id);
    }
}
