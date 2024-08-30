package com.northcoders.bookmanagerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import com.northcoders.bookmanagerapi.service.BookManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
public class BookManagerControllerTests {

    @Mock
    private BookManagerServiceImpl mockBookManagerServiceImpl;

    @InjectMocks
    private BookManagerController bookManagerController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(bookManagerController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllBooks_ReturnsEmptyBooksInitially() throws Exception {

        List<Book> books = new ArrayList<>();
        when(mockBookManagerServiceImpl.getAllBooks()).thenReturn(books);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/book/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", hasSize(0)));
    }

    @Test
    public void testGetAllBooks_ReturnsBooks() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));
        books.add(new Book(2L, "Book Two", "This is the description for Book Two", "Person Two", Genre.Education));
        books.add(new Book(3L, "Book Three", "This is the description for Book Three", "Person Three", Genre.Education));

        when(mockBookManagerServiceImpl.getAllBooks()).thenReturn(books);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/book/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book One"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book Two"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Book Three"));
    }

    @Test
    public void testGetAllBooks_ReturnsEmptyBooksWithGivenGenre() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));
        books.add(new Book(2L, "Book Two", "This is the description for Book Two", "Person Two", Genre.Fiction));
        books.add(new Book(3L, "Book Three", "This is the description for Book Three", "Person Three", Genre.Romance));

        when(mockBookManagerServiceImpl.getBooksByGenre(Genre.Fantasy)).thenReturn(new ArrayList<>());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/book?genre=Fantasy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", hasSize(0)));
    }

    @Test
    public void testGetAllBooks_ReturnsBooksWithGivenGenre() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));
        books.add(new Book(2L, "Book Two", "This is the description for Book Two", "Person Two", Genre.Fantasy));
        books.add(new Book(3L, "Book Three", "This is the description for Book Three", "Person Three", Genre.Fantasy));

        when(mockBookManagerServiceImpl.getBooksByGenre(Genre.Fantasy)).thenReturn(new ArrayList<>() {{
            add(books.get(1));
            add(books.get(2));
        }});

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/book?genre=Fantasy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value(Genre.Fantasy.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value(Genre.Fantasy.toString()));
    }


    @Test
    public void testPostMappingAddABook_ReturnsInsertedBookWithUniqueId() throws Exception {

        Book book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);

        when(mockBookManagerServiceImpl.insertBook(book)).thenReturn(Optional.of(book));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/book/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockBookManagerServiceImpl, times(1)).insertBook(book);
    }

    @Test
    public void testPostMappingAddABook_Returns404WithExistingId() throws Exception {

    }

    @Test
    public void testGetBookById_ReturnsBook() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));

        when(mockBookManagerServiceImpl.getBookById(1L)).thenReturn(Optional.ofNullable(books.getFirst()));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/book/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book One"));

    }

    @Test
    public void testGetBookById_Returns404WhenBookNotFound() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));

        when(mockBookManagerServiceImpl.getBookById(2L)).thenReturn(Optional.empty());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/book/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteBookById_Returns204WhenBookDeleted() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book One", "This is the description for Book One", "Person One", Genre.Education));

        when(mockBookManagerServiceImpl.deleteBookById(1L)).thenReturn(Optional.of(books.get(0)));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.delete("/api/v1/book/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(mockBookManagerServiceImpl, times(1)).deleteBookById(1L);
    }

    @Test
    public void testDeleteBookById_Returns404WhenBookNotFound() throws Exception {

        when(mockBookManagerServiceImpl.deleteBookById(2L)).thenReturn(Optional.empty());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.delete("/api/v1/book/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(mockBookManagerServiceImpl, times(1)).deleteBookById(2L);
    }

    @Test
    public void testUpdateBookById_Returns200WhenBookUpdated() throws Exception {
        Book existingBook = new Book(1L, "Old Title", "Old Description", "Old Author", Genre.Education);
        Book updatedBook = new Book(1L, "New Title", "New Description", "New Author", Genre.Fiction);

        when(mockBookManagerServiceImpl.replaceBook(1L, updatedBook)).thenReturn(Optional.of(updatedBook));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.put("/api/v1/book/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updatedBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("New Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("New Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(Genre.Fiction.toString()));

        verify(mockBookManagerServiceImpl, times(1)).replaceBook(1L, updatedBook);
    }

    @Test
    public void testUpdateBookById_Returns404WhenBookNotFound() throws Exception {
        Book updatedBook = new Book(2L, "New Title", "New Description", "New Author", Genre.Fiction);

        when(mockBookManagerServiceImpl.replaceBook(2L, updatedBook)).thenReturn(Optional.empty());

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.put("/api/v1/book/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updatedBook)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(mockBookManagerServiceImpl, times(1)).replaceBook(2L, updatedBook);
    }


}
