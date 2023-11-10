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

import com.tasks_with_medium_level_complexity.bookstore.model.Genre;
import com.tasks_with_medium_level_complexity.bookstore.repository.GenreRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GenreController.class})
class GenreControllerTest {

    @Autowired
    private GenreController genreController;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    public void testGetAllGenres() {
        List<Genre> genreList = new ArrayList<>();
        when(genreRepository.findAll()).thenReturn(genreList);

        List<Genre> result = genreController.getAllGenres();

        assertEquals(genreList, result);
    }

    @Test
    public void testGetGenreById() {
        Long genreId = 1L;
        Genre genre = new Genre();
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        Optional<Genre> result = Optional.ofNullable(genreController.getGenreById(genreId));

        assertEquals(Optional.of(genre), result);
    }

    @Test
    public void testGetGenreByIdNotFound() {
        Long genreId = 1L;
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> genreController.getGenreById(genreId));
    }

    @Test
    public void testUpdateGenre() {
        Genre updatedGenre = new Genre();
        updatedGenre.setId(1L);
        when(genreRepository.findById(updatedGenre.getId())).thenReturn(Optional.of(updatedGenre));
        when(genreRepository.save(updatedGenre)).thenReturn(updatedGenre);

        Genre result = genreController.updateGenre(updatedGenre.getId(), updatedGenre);

        assertEquals(updatedGenre, result);
    }

    @Test
    public void testUpdateGenreNotFound() {
        Genre updatedGenre = new Genre();
        updatedGenre.setId(1L);
        when(genreRepository.existsById(updatedGenre.getId())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> genreController.updateGenre(updatedGenre.getId(), updatedGenre));

        verify(genreRepository, never()).save(updatedGenre);
    }

    @Test
    public void testDeleteGenre() {
        Long genreId = 1L;
        when(genreRepository.existsById(genreId)).thenReturn(true);

        assertDoesNotThrow(() -> genreController.deleteGenre(genreId));
    }
}
