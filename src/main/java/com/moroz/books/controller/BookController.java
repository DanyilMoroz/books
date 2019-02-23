package com.moroz.books.controller;

import com.moroz.books.model.Book;
import com.moroz.books.model.FilterResult;
import com.moroz.books.repository.BookRepository;
import com.moroz.books.repository.CategoryRepository;
import com.moroz.books.service.FilterResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final FilterResultService filterResultService;

    public BookController(BookRepository bookRepository,
                          CategoryRepository categoryRepository,
                          FilterResultService filterResultService) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.filterResultService = filterResultService;
    }

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("books")
    public String index(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("filterResult", new FilterResult());
        return "bookcards";
    }

    @GetMapping("add")
    public String addPage(Model model) {
        model.addAttribute("newBook", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute Book book, Model model) {
        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/books";
    }
    @PostMapping("filter")
    public String filter(@ModelAttribute FilterResult filterResult, Model model) {
        model.addAttribute("books", filterResultService.result(filterResult));
        model.addAttribute("categories", categoryRepository.findAll());
        return "bookcards";
    }

    @GetMapping("edit/{id}")
    public String updateForm(@PathVariable("id") Book book, Model model) {
        model.addAttribute("book", book);
        return "update";
    }

    @PostMapping("edit")
    public String updateBook(@ModelAttribute("book") Book book, Model model) {
        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        return "redirect:/books";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        bookRepository.deleteById(id);
        model.addAttribute("books", bookRepository.findAll());
        return "redirect:/books";
    }
}
