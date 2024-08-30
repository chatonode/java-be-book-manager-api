package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookManagerService {

    List<Book> getAllBooks();
    List<Book> getBooksByGenre(Genre genre);
    Optional<Book> insertBook(Book book);

    Optional<Book> getBookById(Long id);
    Optional<Book> replaceBook(Long id, Book book);
    Optional<Book> deleteBookById(Long id);
}
