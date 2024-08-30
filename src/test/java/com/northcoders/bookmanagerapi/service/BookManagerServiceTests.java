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

    @Test
    public void testGetBooksByGenreReturnsListOfBooks() {
        // Arrange
        Genre genre = Genre.Education;
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Educational Book One", "This is the description for Educational Book One", "Author One", genre));
        books.add(new Book(2L, "Educational Book Two", "This is the description for Educational Book Two", "Author Two", genre));

        // Stub the repository's findByGenre method to return a list of books
        when(mockBookManagerRepository.findByGenre(genre)).thenReturn(books);

        // Act
        List<Book> actualResult = bookManagerServiceImpl.getBooksByGenre(genre);

        // Assert
        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(books);
        verify(mockBookManagerRepository, times(1)).findByGenre(genre);
    }

    @Test
    public void testGetBooksByGenreReturnsEmptyListWhenNoBooksFound() {
        // Arrange
        Genre genre = Genre.Fantasy;
        List<Book> emptyBooks = new ArrayList<>();

        // Stub the repository's findByGenre method to return an empty list
        when(mockBookManagerRepository.findByGenre(genre)).thenReturn(emptyBooks);

        // Act
        List<Book> actualResult = bookManagerServiceImpl.getBooksByGenre(genre);

        // Assert
        assertThat(actualResult).isEmpty();
        verify(mockBookManagerRepository, times(1)).findByGenre(genre);
    }

    @Test
    public void testGetBooksByGenreHandlesNullGenreGracefully() {
        // Arrange
        Genre genre = null;

        // Stub the repository's findByGenre method to return an empty list or handle null
        when(mockBookManagerRepository.findByGenre(genre)).thenReturn(new ArrayList<>());

        // Act
        List<Book> actualResult = bookManagerServiceImpl.getBooksByGenre(genre);

        // Assert
        assertThat(actualResult).isEmpty();
        verify(mockBookManagerRepository, times(1)).findByGenre(genre);
    }


    @Test
    public void testGetBookByIdReturnsBookWhenFound() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book(bookId, "Book One", "This is the description for Book One", "Author One", Genre.Education);

        // Stub the repository's findById method to return the book when the given ID is found
        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Optional<Book> actualResult = bookManagerServiceImpl.getBookById(bookId);

        // Assert
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(book);
        verify(mockBookManagerRepository, times(1)).findById(bookId);
    }

    @Test
    public void testGetBookByIdReturnsEmptyWhenNotFound() {
        // Arrange
        Long bookId = 2L;

        // Stub the repository's findById method to return an empty Optional when the book ID is not found
        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        Optional<Book> actualResult = bookManagerServiceImpl.getBookById(bookId);

        // Assert
        assertThat(actualResult).isNotPresent();
        verify(mockBookManagerRepository, times(1)).findById(bookId);
    }

    @Test
    public void testGetBookByIdHandlesNullIdGracefully() {
        // Arrange
        Long bookId = null;

        // Stub the repository's findById method to handle a null ID gracefully, returning an empty Optional
        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        Optional<Book> actualResult = bookManagerServiceImpl.getBookById(bookId);

        // Assert
        assertThat(actualResult).isNotPresent();
        verify(mockBookManagerRepository, times(1)).findById(bookId);
    }


}
