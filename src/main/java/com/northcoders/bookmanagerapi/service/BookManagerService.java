package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;

import java.util.List;

public interface BookManagerService {

    List<Book> getAllBooks();
    Book insertBook(Book book);
}
