package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.repository.BookManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    @Autowired
    BookManagerRepository bookManagerRepository;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookManagerRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book insertBook(Book book) {
        return bookManagerRepository.save(book);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookManagerRepository.findById(id);
    }

    @Override
    public Optional<Book> replaceBook(Long id, Book bookToPut) {
        Optional<Book> foundbook = bookManagerRepository.findById(id);

        if (foundbook.isPresent()) {
            foundbook.get().setTitle(bookToPut.getTitle());
            foundbook.get().setDescription(bookToPut.getDescription());
            foundbook.get().setAuthor(bookToPut.getAuthor());
            foundbook.get().setGenre(bookToPut.getGenre());
            bookManagerRepository.save(foundbook.get());
        }

        return foundbook;
    }

}
