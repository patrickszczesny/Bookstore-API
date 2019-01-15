package com.example.BookStore.controller;

import com.example.BookStore.model.*;
import com.example.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET, value="/fake")
    public String getSomeText() {
        return "some text";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BookListing getListing(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author
    ) {
        SearchFilters searchFilters = new SearchFilters(title, author);
        return bookService.getFilteredListing(searchFilters);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllBooks() {
        bookService.deleteAll();
    }

    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BookDto getBookById(@PathVariable String bookId) {
        return BookDto.fromDomain(bookService.getById(bookId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody BookDto bookDto) {
        bookService.addBook(bookDto.toDomain());
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String bookId) {
        bookService.deleteById(bookId);
    }

    @GetMapping("/{bookId}/details")
    public BookWithDetailsDto getBookWithDetailsById(@PathVariable String bookId) {
        return BookWithDetailsDto.fromDomain(bookService.getBookWithDetails(bookId));
    }
}
