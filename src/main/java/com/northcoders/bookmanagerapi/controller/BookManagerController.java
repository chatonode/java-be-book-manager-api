package com.northcoders.bookmanagerapi.controller;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.service.BookManagerService;
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
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookManagerService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/:bookId")
    public ResponseEntity<Book> getBookById() {
//        List<Book> books = bookManagerService.
        Optional<Book> bookFound = bookManagerService.getBookById(1L);
        if (bookFound.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        return new ResponseEntity<>(bookManagerService.getBookById(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookManagerService.insertBook(book);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());
        return new ResponseEntity<>(newBook, httpHeaders, HttpStatus.CREATED);
    }

}
