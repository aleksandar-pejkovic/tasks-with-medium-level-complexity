package com.tasks_with_medium_level_complexity.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.tasks_with_medium_level_complexity.bookstore.model.Book;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

    List<Book> findBooksByTitleContainingIgnoreCase(String title);

    List<Book> findBooksByAuthorsNameContainingIgnoreCase(String authorName);

    List<Book> findBooksByGenresNameContainingIgnoreCase(String genreName);
}
