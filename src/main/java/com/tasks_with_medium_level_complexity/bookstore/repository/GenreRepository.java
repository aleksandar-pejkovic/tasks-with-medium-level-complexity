package com.tasks_with_medium_level_complexity.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.tasks_with_medium_level_complexity.bookstore.model.Genre;

@Repository
public interface GenreRepository extends ListCrudRepository<Genre, Long> {

    List<Genre> findGenresByNameContainingIgnoreCase(String name);
}
