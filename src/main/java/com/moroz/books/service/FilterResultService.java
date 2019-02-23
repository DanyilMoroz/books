package com.moroz.books.service;

import com.moroz.books.model.Book;
import com.moroz.books.model.FilterResult;
import com.moroz.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FilterResultService {

    private final BookRepository bookRepository;

    public FilterResultService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Set<Book> result(FilterResult filterResult){
        if(filterResult != null){
            return bookRepository.findDistinctByCategoriesIn(filterResult.getCategorySet());
        }
        return new HashSet<>();
    }
}
