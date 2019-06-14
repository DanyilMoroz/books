package com.moroz.books.controller;

import com.moroz.books.model.Book;
import com.moroz.books.model.FilterResult;
import com.moroz.books.repository.BookRepository;
import com.moroz.books.repository.CategoryRepository;
import com.moroz.books.service.FilterResultService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final FilterResultService filterResultService;

    @Value("${files.upload.uploadPath}")
    private String uploadPath;

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
        model.addAttribute("categoryName", "All books");
        return "books";
    }

    @GetMapping("add")
    public String addPage(Model model) {
        model.addAttribute("newBook", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String add(@ModelAttribute Book book,
                      @RequestParam("file") MultipartFile file, Model model) throws IOException {
        //обязательно добавить дефолтные картинки для списка книг (230х320) и страницы просмотра
        if(file != null ){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            book.setImageName(fileName);
        }
        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/books";
    }
    @PostMapping("filter")
    public String filter(@ModelAttribute FilterResult filterResult, Model model) {
        if(filterResult.getCategories().size() == 0){
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            return "books";
        }
        model.addAttribute("books", filterResultService.result(filterResult));
        model.addAttribute("categories", categoryRepository.findAll());
        return "books";
    }

    @GetMapping("category/{id}")
    public String findByCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("books", bookRepository.findBookByCategoriesId(id));
        model.addAttribute("categoryName", categoryRepository.findCategoryById(id).getName());
        return "books";
    }

    @GetMapping("edit/{id}")
    public String updateForm(@PathVariable("id") Book book, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "update";
    }

    @PostMapping("edit")
    public String updateBook(@ModelAttribute("book") Book book, Model model) {
        book.setImageName(bookRepository.findBookById(book.getId()).getImageName());
        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/books";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        bookRepository.deleteById(id);
        model.addAttribute("books", bookRepository.findAll());
        return "redirect:/books";
    }
}
