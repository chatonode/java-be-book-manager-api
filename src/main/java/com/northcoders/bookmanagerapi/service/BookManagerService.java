package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookManagerService {

    List<Book> getAllBooks();
    Book insertBook(Book book);

    Optional<Book> getBookById(Long id);
    Optional<Book> replaceBook(Long id, Book book);
}
