package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.exception.ResourceAlreadyExistsException;
import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
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
    public List<Book> getBooksByGenre(Genre genre) {
        return bookManagerRepository.findByGenre(genre);
    }

    @Override
    public Book insertBook(Book book) {
        Optional<Book> foundBook = bookManagerRepository.findById(book.getId());

        if (foundBook.isPresent()) {
            throw new ResourceAlreadyExistsException(Book.class.getSimpleName(), foundBook.get().getId());
        }

        return bookManagerRepository.save(book);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookManagerRepository.findById(id);
    }

    @Override
    public Optional<Book> replaceBook(Long id, Book bookToPut) {
        Optional<Book> foundBook = bookManagerRepository.findById(id);

        if (foundBook.isPresent()) {
            foundBook.get().setTitle(bookToPut.getTitle());
            foundBook.get().setDescription(bookToPut.getDescription());
            foundBook.get().setAuthor(bookToPut.getAuthor());
            foundBook.get().setGenre(bookToPut.getGenre());
            bookManagerRepository.save(foundBook.get());
        }

        return foundBook;
    }

    @Override
    public Optional<Book> deleteBookById(Long id) {
        Optional<Book> foundBook = bookManagerRepository.findById(id);

        if (foundBook.isPresent()) {
            bookManagerRepository.deleteById(foundBook.get().getId());
        }

        return foundBook;
    }

}
