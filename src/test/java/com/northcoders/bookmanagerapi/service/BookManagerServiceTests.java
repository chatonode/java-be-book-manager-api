package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import com.northcoders.bookmanagerapi.repository.BookManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
public class BookManagerServiceTests {

    @Mock
    private BookManagerRepository mockBookManagerRepository;

    @InjectMocks
    private BookManagerServiceImpl bookManagerServiceImpl;

    @Test
    public void testGetAllBooksReturnsListOfBooks() {

        // Arrange
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));
        books.add(new Book(2L, "Book Two", "This is the description for Book Two", "Person Two", Genre.Education));
        books.add(new Book(3L, "Book Three", "This is the description for Book Three", "Person Three", Genre.Education));

        // We have a test double for the BookManagerRepository. This is a stub
        // Ensure that when .findAll() is invoked, it will always return the books above
        when(mockBookManagerRepository.findAll()).thenReturn(books);

        // Act
        List<Book> actualResult = bookManagerServiceImpl.getAllBooks();

        // Assert
        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(books);
    }

    @Test
    public void testAddABook() {

        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);

        when(mockBookManagerRepository.save(book)).thenReturn(book);

        Book actualResult = bookManagerServiceImpl.insertBook(book);

        assertThat(actualResult).isEqualTo(book);
    }

}
