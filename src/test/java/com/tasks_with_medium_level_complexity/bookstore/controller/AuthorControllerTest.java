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

import com.tasks_with_medium_level_complexity.bookstore.model.Author;
import com.tasks_with_medium_level_complexity.bookstore.repository.AuthorRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthorController.class})
class AuthorControllerTest {

    @Autowired
    private AuthorController authorController;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    public void testCreateAuthor() {
        Author authorToCreate = new Author();
        when(authorRepository.save(authorToCreate)).thenReturn(authorToCreate);

        Author createdAuthor = authorController.createAuthor(authorToCreate);

        assertEquals(authorToCreate, createdAuthor);
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authorList = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authorList);

        List<Author> result = authorController.getAllAuthors();

        assertEquals(authorList, result);
    }

    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<Author> result = Optional.ofNullable(authorController.getAuthorById(authorId));

        assertEquals(Optional.of(author), result);
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authorController.getAuthorById(authorId));
    }

    @Test
    public void testUpdateAuthor() {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        when(authorRepository.findById(updatedAuthor.getId())).thenReturn(Optional.of(updatedAuthor));
        when(authorRepository.save(updatedAuthor)).thenReturn(updatedAuthor);

        Author result = authorController.updateAuthor(updatedAuthor.getId(), updatedAuthor);

        assertEquals(updatedAuthor, result);
    }

    @Test
    public void testUpdateAuthorNotFound() {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        when(authorRepository.existsById(updatedAuthor.getId())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> authorController.getAuthorById(updatedAuthor.getId()));

        verify(authorRepository, never()).save(updatedAuthor);
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        when(authorRepository.existsById(authorId)).thenReturn(true);

        assertDoesNotThrow(() -> authorController.deleteAuthor(authorId));
    }
}
