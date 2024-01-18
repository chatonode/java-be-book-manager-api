package com.northcoders.bookmanagerapi.repository;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookManagerRepositoryTests {

    @Autowired
    private BookManagerRepository bookManagerRepository;

    @Test
    public void testFindAllBooksReturnsBooks() {

        // Arrange
        Book book = new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education);
        bookManagerRepository.save(book);

        // Act
        Iterable<Book> books = bookManagerRepository.findAll();

        // Assert
        assertThat(books).hasSize(1);

    }

}
