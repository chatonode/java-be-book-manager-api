package com.northcoders.bookmanagerapi.controller;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import com.northcoders.bookmanagerapi.service.BookManagerService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookManagerController {

    @Autowired
    BookManagerService bookManagerService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) Genre genre) {

        if (genre != null && !genre.toString().isBlank()) {
            return new ResponseEntity<>(bookManagerService.getBooksByGenre(genre), HttpStatus.OK);
        }

        return new ResponseEntity<>(bookManagerService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Optional<Book> bookFound = bookManagerService.getBookById(Long.parseLong(id));

        if (bookFound.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(bookFound.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookManagerService.insertBook(book);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());
        return new ResponseEntity<>(newBook, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        Optional<Book> updatedBook = bookManagerService.replaceBook(Long.parseLong(id), book);

        if (updatedBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("book", "/api/v1/book/" + updatedBook.get().getId().toString());
        return new ResponseEntity<>(updatedBook.get(), httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable String id) {
        Optional<Book> deletedBook = bookManagerService.deleteBookById(Long.parseLong(id));

        if (deletedBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.remove("book");
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

}
