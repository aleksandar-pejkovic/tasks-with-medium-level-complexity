package com.tasks_with_medium_level_complexity.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.tasks_with_medium_level_complexity.bookstore.model.Author;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {

    List<Author> findAuthorsByNameContainingIgnoreCase(String name);
}
