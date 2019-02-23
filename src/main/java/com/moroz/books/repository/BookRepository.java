package com.moroz.books.repository;

import com.moroz.books.model.Book;
import com.moroz.books.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByBookName(String name);
    Set<Book> findDistinctByCategoriesIn(Set<Category> categories);
}
