package com.tasks_with_medium_level_complexity.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tasks_with_medium_level_complexity.bookstore.model.Book;
import com.tasks_with_medium_level_complexity.bookstore.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookController.class})
class BookControllerTest {

    @Autowired
    private BookController bookController;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testGetAllBooks() {
        List<Book> bookList = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookController.getAllBooks();

        assertEquals(bookList, result);
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> result = Optional.ofNullable(bookController.getBookById(bookId));

        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testGetBookByIdNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookController.getBookById(bookId));
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        when(bookRepository.findById(updatedBook.getId())).thenReturn(Optional.of(updatedBook));
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book result = bookController.updateBook(updatedBook.getId(), updatedBook);

        assertEquals(updatedBook, result);
    }

    @Test
    public void testUpdateBookNotFound() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        when(bookRepository.existsById(updatedBook.getId())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> bookController.updateBook(updatedBook.getId(), updatedBook));

        verify(bookRepository, never()).save(updatedBook);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        assertDoesNotThrow(() -> bookController.deleteBook(bookId));
    }
}
